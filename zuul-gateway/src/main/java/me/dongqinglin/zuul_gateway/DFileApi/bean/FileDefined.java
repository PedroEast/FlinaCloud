package me.dongqinglin.zuul_gateway.DFileApi.bean;

public class FileDefined {
    private String fileName;
    private String fileData;

    public FileDefined(String fileName, String fileData) {
        this.fileName = fileName;
        this.fileData = fileData;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileData() {
        return fileData;
    }

    @Override
    public String toString() {
        return "FileDefined{" +
                "fileName='" + fileName + '\'' +
                ", fileData='" + fileData + '\'' +
                '}';
    }
}
