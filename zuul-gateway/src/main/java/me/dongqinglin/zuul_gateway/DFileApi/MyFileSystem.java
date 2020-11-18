package me.dongqinglin.zuul_gateway.DFileApi;


import me.dongqinglin.zuul_gateway.DFileApi.bean.DirectoryDefined;
import me.dongqinglin.zuul_gateway.DFileApi.bean.FileDefined;

import java.util.List;

public interface MyFileSystem {

    DirectoryDefined dir(String folderPath) throws Exception;

    boolean newFolder(String folderPath) throws Exception;

    boolean newFile(String filePath, String fileData) throws Exception;
    boolean newFile(FileDefined file) throws Exception;
    boolean newFiles(List<FileDefined> files) throws Exception;


    boolean editFile(String filePath, String fileData) throws Exception;
    boolean editFile(FileDefined file) throws Exception;
    boolean editFiles(List<FileDefined> files) throws Exception;


    String view(String filePath) throws Exception;

    boolean rename(String originSourceName, String newSourceName) throws Exception;
    boolean delete(String sourcePath) throws Exception;

    boolean copy(String sourcePath, String targetPath) throws Exception;
    boolean cut(String sourcePath, String targetPath) throws Exception;

    boolean zip(String sourcePath, String targetPath) throws Exception;
    boolean unzip(String sourcePath, String targetPath) throws Exception;

    // boolean upload(MultipartFile[] files, String targetPath);
}
