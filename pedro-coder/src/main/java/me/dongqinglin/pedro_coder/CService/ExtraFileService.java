package me.dongqinglin.pedro_coder.CService;

import org.springframework.web.multipart.MultipartFile;

public interface ExtraFileService {
    public void uploadExtraFiles(MultipartFile[] files, String tableName, int id);
}
