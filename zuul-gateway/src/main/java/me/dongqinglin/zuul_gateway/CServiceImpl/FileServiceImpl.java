package me.dongqinglin.zuul_gateway.CServiceImpl;


import me.dongqinglin.zuul_gateway.CService.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {
    @Value("${static-root}")
    private String STATIC_ROOT;

    @Override
    public String getStaticRootStr() {
        return STATIC_ROOT;
    }


    @Override
    public boolean handleUpload(MultipartFile[] files, String targetPath) throws Exception {
        if (files == null || files.length == 0) throw new Exception("参数非法");
        if (targetPath == null || targetPath.trim().equals("")) throw new Exception("参数非法");
        // System.out.println(targetPath);

        if (targetPath.indexOf(".")!= -1){ targetPath.replaceAll(".", "");}
        if (!targetPath.endsWith(File.separator)) targetPath = targetPath + File.separator;
        // System.out.println(targetPath);

        new File(targetPath).mkdirs();
        for (int i = 0; i < files.length; i++) {
            try {
                MultipartFile file = files[i];
                String fileName = file.getOriginalFilename();
                // System.out.println(fileName);
                // System.out.println(targetPath + fileName);
                file.transferTo(new File(targetPath + fileName));
            } catch (IOException e) {
                throw new Exception("上传失败, 可能是路径错误, 路径为" + targetPath);
            }
        }
        return true;
    }
}
