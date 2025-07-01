package database.model;

public class FileHash {

    private String fileHash;

    public FileHash() {
    }

    public FileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }
}
