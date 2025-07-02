package controller;

import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.List;

import database.model.FileHash;
import service.FileHashService;

public class FileHashController {

	private static FileHashService fileHashService;

	static {
		fileHashService = new FileHashService();
	}

	public static List<FileHash> list() throws SQLException {
		List<FileHash> fileHashes = fileHashService.list();
		return fileHashes;
	}

	public static FileHash getByFileHash(String fileHash) throws SQLException {
		FileHash fileHashObject = fileHashService.getByFileHash(fileHash);
		return fileHashObject;
	}

	public static FileHash insert(MessageDigest messageDigest, int importType) throws SQLException {
		FileHash fileHash = fileHashService.create(messageDigest, importType);
		return fileHash;
	}

	public static void delete(String fileHash) throws SQLException {
		fileHashService.delete(fileHash);
	}

}
