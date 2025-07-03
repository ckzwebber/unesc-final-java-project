package service;

import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.HexFormat;
import java.util.List;

import database.dao.FileHashDAO;
import database.model.FileHash;

public class FileHashService {

	private static FileHashDAO fileHashDAO;

	static {
		try {
			fileHashDAO = new FileHashDAO();
		} catch (SQLException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public List<FileHash> list() {
		try {
			return fileHashDAO.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public FileHash getByFileHash(String fileHash) {
		if (fileHash == null || fileHash.isEmpty()) {
			throw new IllegalArgumentException("File hash cannot be null or empty");
		}

		try {
			FileHash fileHashDb = fileHashDAO.selectByHash(fileHash);
			if (fileHashDb == null) {
				throw new IllegalArgumentException("File hash not found");
			}
			return fileHashDb;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void delete(String fileHash) {
		if (fileHash == null || fileHash.isEmpty()) {
			throw new IllegalArgumentException("File hash cannot be null or empty");
		}

		try {
			FileHash fileHashDb = fileHashDAO.selectByHash(fileHash);
			if (fileHashDb == null) {
				throw new IllegalArgumentException("File hash not found");
			}
			fileHashDAO.delete(fileHash);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public FileHash create(MessageDigest messageDigest, int importType) {
		if (messageDigest == null) {
			throw new IllegalArgumentException("Message digest cannot be null or empty");
		}

		if (importType < 0) {
			throw new IllegalArgumentException("Import type must be a non-negative integer");
		}

		String fileHash = "";

		byte[] digestBytes = messageDigest.digest();
		fileHash = HexFormat.of().formatHex(digestBytes);

		FileHash existingFileHash = fileHashOnDatabase(fileHash);

		if (existingFileHash != null) {
			throw new IllegalArgumentException("File hash already exists in database");
		}

		FileHash newFileHash = new FileHash(fileHash, importType);
		try {
			fileHashDAO.insert(newFileHash);
			return newFileHash;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private FileHash fileHashOnDatabase(String fileHash) {
		if (fileHash == null || fileHash.isEmpty()) {
			throw new IllegalArgumentException("File hash cannot be null or empty");
		}

		try {
			return fileHashDAO.selectByHash(fileHash);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
