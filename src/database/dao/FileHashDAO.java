package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.ConnectionFactory;
import database.model.FileHash;

public class FileHashDAO {

	private static final String SELECT_ALL_QUERY = "SELECT * FROM file_hash";
	private static final String SELECT_BY_HASH_QUERY = "SELECT * FROM file_hash WHERE file_hash = ?";
	private static final String INSERT_QUERY = "INSERT INTO file_hash (file_hash, import_type) VALUES (?, ?)";
	private static final String DELETE_QUERY = "DELETE FROM file_hash WHERE file_hash = ?";

	private final PreparedStatement selectAllStatement;
	private final PreparedStatement selectByHashStatement;
	private final PreparedStatement insertStatement;
	private final PreparedStatement deleteStatement;

	public FileHashDAO() throws SQLException {
		Connection connection = ConnectionFactory.getConnection();
		selectAllStatement = connection.prepareStatement(SELECT_ALL_QUERY);
		selectByHashStatement = connection.prepareStatement(SELECT_BY_HASH_QUERY);
		insertStatement = connection.prepareStatement(INSERT_QUERY);
		deleteStatement = connection.prepareStatement(DELETE_QUERY);
	}

	public void insert(FileHash fileHash) throws SQLException {
		insertStatement.setString(1, fileHash.getFileHash());
		insertStatement.setInt(2, fileHash.getImportType());
		insertStatement.executeUpdate();
	}

	public void delete(String fileHash) throws SQLException {
		deleteStatement.setString(1, fileHash);
		deleteStatement.executeUpdate();
	}

	public ArrayList<FileHash> selectAll() throws SQLException {
		ArrayList<FileHash> list = new ArrayList<>();
		try (ResultSet resultSet = selectAllStatement.executeQuery()) {
			while (resultSet.next()) {
				list.add(buildFileHashFromResultSet(resultSet));
			}
		}
		return list;
	}

	public FileHash selectByHash(String fileHash) throws SQLException {
		selectByHashStatement.setString(1, fileHash);
		try (ResultSet resultSet = selectByHashStatement.executeQuery()) {
			if (resultSet.next()) {
				return buildFileHashFromResultSet(resultSet);
			}
		}
		return null;
	}

	private FileHash buildFileHashFromResultSet(ResultSet resultSet) throws SQLException {
		FileHash fileHash = new FileHash();
		fileHash.setFileHash(resultSet.getString("file_hash"));
		fileHash.setImportType(resultSet.getInt("import_type"));
		fileHash.setImportCount(resultSet.getInt("import_count"));
		return fileHash;
	}
}
