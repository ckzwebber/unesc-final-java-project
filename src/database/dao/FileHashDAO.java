package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.ConnectionFactory;
import database.model.FileHash;

public class FileHashDAO {

    private static final String SELECT_ALL_QUERY = "SELECT file_hash FROM tb_file_hash";
    private static final String SELECT_BY_HASH_QUERY = "SELECT file_hash FROM tb_file_hash WHERE file_hash = ?";
    private static final String INSERT_QUERY = "INSERT INTO tb_file_hash(file_hash) VALUES (?)";
    private static final String DELETE_QUERY = "DELETE FROM tb_file_hash WHERE file_hash = ?";

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
        insertStatement.executeUpdate();
    }

    public void delete(String fileHash) throws SQLException {
        deleteStatement.setString(1, fileHash);
        deleteStatement.executeUpdate();
    }

    public ArrayList<FileHash> selectAll() throws SQLException {
        ArrayList<FileHash> list = new ArrayList<>();
        try (ResultSet reseultSet = selectAllStatement.executeQuery()) {
            while (reseultSet.next()) {
                list.add(buildFileHashFromResultSet(reseultSet));
            }
        }
        return list;
    }

    public FileHash selectByHash(String fileHash) throws SQLException {
        selectByHashStatement.setString(1, fileHash);
        try (ResultSet reseultSet = selectByHashStatement.executeQuery()) {
            if (reseultSet.next()) {
                return buildFileHashFromResultSet(reseultSet);
            }
        }
        return null;
    }

    private FileHash buildFileHashFromResultSet(ResultSet reseultSet) throws SQLException {
        FileHash fileHash = new FileHash();
        fileHash.setFileHash(reseultSet.getString("file_hash"));
        return fileHash;
    }
}
