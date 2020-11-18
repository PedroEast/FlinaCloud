package me.dongqinglin.pedro_coder.CServiceImpl;

import me.dongqinglin.pedro_coder.CService.ExtraFileService;
import me.dongqinglin.pedro_coder.DFileApi.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ExtraFileServiceImpl implements ExtraFileService {

    @Value("${static-root}")
    private String UPLOAD_FOLDER_PATH;

    @Override
    public void uploadExtraFiles(MultipartFile[] files, String tableName, int id) {
        if(files.length == 0 ) System.out.println("None file.");

        String IDFolder = UPLOAD_FOLDER_PATH + tableName +"/" + id + "/";
        String ZipFolder = UPLOAD_FOLDER_PATH + tableName  + "/zip/";
        FileUtil.makeDir(IDFolder);
        FileUtil.makeDir(ZipFolder);

        for (int i = 0; i < files.length; i++) {
            try {
                MultipartFile file = files[i];
                String fileName = file.getOriginalFilename();
                // System.out.println(fileName);
                file.transferTo(new File(IDFolder + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String zipFileName = ZipFolder + id + ".zip";
        FileUtil.zipFiles(IDFolder,zipFileName);
    }

}
