package me.dongqinglin.pedro_coder.BController;

import me.dongqinglin.pedro_coder.ZBean.FullStackAutoRequest;
import me.dongqinglin.pedro_coder.ZBean.MessageDefined;
import me.dongqinglin.pedro_coder.CServiceImplAlgorithm.FullStackGeneraterFacade;
import me.dongqinglin.pedro_coder.CServiceImplAlgorithm.FullStackGeneraterFacadeImpl;
import me.dongqinglin.pedro_coder.DFileApi.MyFileSystem;
import me.dongqinglin.pedro_coder.DFileApi.MyFileSystemImpl;
import me.dongqinglin.pedro_coder.DFileApi.bean.FileDefined;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@RestController
@RequestMapping("fullstack")
public class FullstackController {

    @Value("${static-root}")
    private String STATIC_ROOT;

    @PostMapping("onPostToPreview")
    public ResponseEntity<?> onPostToPreview(@RequestBody FullStackAutoRequest autoRequest){
        List<FileDefined> result = null;
        System.out.println(autoRequest);
        FullStackGeneraterFacade cli = new FullStackGeneraterFacadeImpl(autoRequest.getCreateTableStr(), autoRequest.getDaoApi(), autoRequest.getBackendApi(), autoRequest.getFrontendApi());
        try {
            result = cli.generateFile();
        } catch (Exception e) {
            System.out.println(e);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("onPostToGenerate")
    public ResponseEntity<?> onPostToGenerate(@RequestBody FullStackAutoRequest autoRequest){
        MessageDefined result = null;
        // System.out.println(autoRequest);
        FullStackGeneraterFacade cli = new FullStackGeneraterFacadeImpl(autoRequest.getCreateTableStr(), autoRequest.getDaoApi(), autoRequest.getBackendApi(), autoRequest.getFrontendApi());
        try {
            List<FileDefined> files = cli.generateFile();
            result = generateFiles(files);
        } catch (Exception e) {
            result = new MessageDefined(502, "error", e.toString());
        }finally {
            return ResponseEntity.ok(result);
        }
    }

    private MessageDefined generateFiles(List<FileDefined> files) throws Exception {
        if(files.size() == 0 ) return null;
        MessageDefined result;
        MyFileSystem fileSystem = new MyFileSystemImpl();
        String folder = STATIC_ROOT + "tempFile/";

        for (int i = 0; i < files.size(); i++) {
            FileDefined file = files.get(i);
            String filePath =  folder + file.getFileName();
            fileSystem.newFile(filePath, file.getFileData());
        }
        String zipPath = STATIC_ROOT + "flina.zip";
        fileSystem.zip(folder, zipPath);

        final Timer timer = new Timer();
        //设定定时任务
        timer.schedule(new TimerTask() {
            //定时任务执行方法
            @Override
            public void run() {
                try {
                    fileSystem.delete(folder);
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println("无法删除");
                }
                System.out.println("We delete temp files success.");
            }
        }, 1000*60*5);

        result = new MessageDefined(200, "success", "flina.zip");
        return result;
    }


}
