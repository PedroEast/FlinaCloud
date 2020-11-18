package me.dongqinglin.pedro_coder.CServiceImplAlgorithm;

import me.dongqinglin.pedro_coder.DFileApi.MyFileSystem;
import me.dongqinglin.pedro_coder.DFileApi.MyFileSystemImpl;
import me.dongqinglin.pedro_coder.DFileApi.bean.FileDefined;
import me.dongqinglin.pedro_coder.ZBean.MessageDefined;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FullStackGeneraterTest {
    public static void main(String[] args) throws Exception {
        String createTableStr = "CREATE TABLE `user_meta` (\n" +
                "`id` int(11) NOT NULL,\n" +
                "  `enable` bit(1) NOT NULL,\n" +
                "  `college` text DEFAULT NULL,\n" +
                "  `concat` varchar(255) DEFAULT NULL,\n" +
                "  `name` varchar(255) DEFAULT NULL,\n" +
                "  `student_id` varchar(255) DEFAULT NULL,\n" +
                "  `author_id` int(11) NOT NULL,\n" +
                "  PRIMARY KEY (`id`)\n," +
                "  FOREIGN KEY (author_id) REFERENCES user(id),\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8\n";
        String daoApi = "Hibernate";
        String backendApi = "SpringBoot";
        String frontendApi = "Angular";

        FullStackGeneraterFacade cli = new FullStackGeneraterFacadeImpl(createTableStr, daoApi, backendApi, frontendApi);
        List<FileDefined> fileDefineds = cli.generateFile();
        MessageDefined message = generateFiles(fileDefineds);
        System.out.println(message);
    }

    private static MessageDefined generateFiles(List<FileDefined> files) throws Exception {
        if(files.size() == 0 ) return null;
        MessageDefined result;
        MyFileSystem fileSystem = new MyFileSystemImpl();
        String folder = "D:/static/" + "tempFile/";

        for (int i = 0; i < files.size(); i++) {
            FileDefined file = files.get(i);
            String filePath =  folder + file.getFileName();
            fileSystem.newFile(filePath, file.getFileData());
        }
        String zipPath = "D:/static/" + "flina.zip";
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

        result = new MessageDefined(200, "success","flina.zip");
        return result;
    }
}
