package database.model;

public class FileHash {

	private String fileHash;
	private int importType;
	private int importCount;

	public FileHash() {
	}

	public FileHash(String fileHash, int importType) {
		this.fileHash = fileHash;
		this.importType = importType;
	}

	public String getFileHash() {
		return fileHash;
	}

	public void setFileHash(String fileHash) {
		this.fileHash = fileHash;
	}

	public int getImportType() {
		return importType;
	}

	public void setImportType(int importType) {
		this.importType = importType;
	}

	public int getImportCount() {
		return importCount;
	}

	public void setImportCount(int importCount) {
		this.importCount = importCount;
	}

}
