package me.dongqinglin.pedro_coder.DFileApi;


import me.dongqinglin.pedro_coder.DFileApi.bean.DirectoryDefined;
import me.dongqinglin.pedro_coder.DFileApi.bean.FileDefined;

import java.io.*;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class MyFileSystemImpl implements MyFileSystem {


    @Override
    public DirectoryDefined dir(String folderPath) throws Exception {
        if (folderPath == null || folderPath.trim().equals("")) throw new Exception("dir()参数非法");
        File pathFile = new File(folderPath);
        if (!pathFile.exists()) throw new Exception(folderPath+ "dir()文件不存在" );
        if (pathFile.isFile()) throw new Exception("dir()期待是文件夹，而不是文件");
        DirectoryDefined result = new DirectoryDefined();
        result.setDiretoryName(pathFile.getName());
        try {
            File files[] = pathFile.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    DirectoryDefined chirdResult = dir(file.getAbsolutePath());
                    result.addtDirectory(chirdResult);
                } else {
                    result.addFile(file.getName());
                }
            }
        } catch (Exception e) {
            System.out.println("I/O异常");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean newFolder(String folderPath) throws Exception {
        if (folderPath == null || folderPath.trim().equals("")) throw new Exception("newFolder()参数非法");
        File pathFile=new File(folderPath);
        if (pathFile.exists()) {
            System.out.println("文件夹已存在，不需要创建");
            return true;
        }
        try {
            pathFile.mkdirs();
        } catch (Exception e) {
            System.out.println("I/O异常");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean newFile(String filePath, String fileData) throws Exception {
        if (filePath == null || filePath.trim().equals("")) throw new Exception("newFile()参数非法");
        if (fileData == null || fileData.trim().equals("")) throw new Exception("newFile()参数非法");
        File file=new File(filePath);
        String fileFolderStr = null;
        try {
            fileFolderStr = filePath.substring(0, filePath.lastIndexOf('\\'));
        } catch (Exception e) {
            try {
                fileFolderStr = filePath.substring(0, filePath.lastIndexOf('/'));
            } catch (Exception e1) {
                fileFolderStr = "";
            }
        }
        if(!newFolder(fileFolderStr)) return false;
        // 不允许新建已经存在的文件，默认为修改文件名? Windows的实现方式
        if(file.exists()) throw new Exception("新建文件失败，因为文件已经存在");
        try {
            file.createNewFile();
            FileOutputStream out=new FileOutputStream(file,false);
            StringBuffer sb=new StringBuffer(fileData);
            out.write(sb.toString().getBytes("utf-8"));
            out.close();
        } catch (IOException e) {
            System.out.println("I/O异常");
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public boolean newFile(FileDefined file) throws Exception {
        if (file == null ) throw new Exception("newFile()参数非法");
        return newFile(file.getFileName(), file.getFileData());
    }

    @Override
    public boolean newFiles(List<FileDefined> files) throws Exception {
        if (files == null || files.size() == 0 ) throw new Exception("newFile()参数非法");
        boolean result = true;
        for (int i = 0; i < files.size(); i++) {
            FileDefined file = files.get(i);
            System.out.println(file.getFileName());
            result = result && newFile(file);
        }
        return result;
    }

    @Override
    public boolean editFile(String filePath, String fileData) throws Exception {
        if (filePath == null || filePath.trim().equals("")) throw new Exception("editFile()参数非法");
        if (fileData == null || fileData.trim().equals("")) throw new Exception("editFile()参数非法");
        File file=new File(filePath);
        String fileFolderStr = null;
        try {
            fileFolderStr = filePath.substring(0, filePath.lastIndexOf('\\'));
        } catch (Exception e) {
            try {
                fileFolderStr = filePath.substring(0, filePath.lastIndexOf('/'));
            } catch (Exception e1) {
                fileFolderStr = "";
            }
        }
        if(!newFolder(fileFolderStr)) return false;
        // 不允许修改不存在的文件
        if(!file.exists()) throw new Exception("要修改的文件存在，或许你应该先新建文件");
        try {
            file.createNewFile();
            FileOutputStream out=new FileOutputStream(file,false);
            StringBuffer sb=new StringBuffer(fileData);
            out.write(sb.toString().getBytes("utf-8"));
            out.close();
        } catch (IOException e) {
            System.out.println("I/O异常");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean editFile(FileDefined file) throws Exception {
        if (file == null ) throw new Exception("editFile()参数非法");
        return editFile(file.getFileName(), file.getFileData());
    }

    @Override
    public boolean editFiles(List<FileDefined> files) throws Exception {
        if (files == null || files.size() == 0 ) throw new Exception("editFile()参数非法");
        boolean result = true;
        for (int i = 0; i < files.size(); i++) {
            FileDefined file = files.get(i);
            result = result && editFile(file);
        }
        return result;
    }

    @Override
    public String view(String filePath) throws Exception {
        if (filePath == null || filePath.trim().equals("")) throw new Exception("view()参数非法");
        File file = new File(filePath);

        if (!file.exists()) throw new Exception("要预览的文件不存在，或许你应该先新建文件"+ filePath);

        StringBuilder sb = new StringBuilder();
        BufferedReader in = new BufferedReader(new FileReader(file));
        String str;
        while ((str = in.readLine()) != null) {
            sb.append(str + "\n");
        }
        return sb.toString();
    }

    @Override
    public boolean rename(String originSourceName, String newSourceName) throws Exception {
        if (originSourceName == null || originSourceName.trim().equals("")) throw new Exception("rename()参数非法");
        if (newSourceName == null || newSourceName.trim().equals("")) throw new Exception("rename()参数非法");
        File file = new File(originSourceName);
        File newFile = new File(newSourceName);
        if (!file.exists()) throw new Exception("要重命名的文件不存在，或许你应该先新建文件");
        if (newFile.exists()) throw new Exception("重命名后的文件已存在，或许你应该更换名称");
        if(file.renameTo(newFile)) return true;
        System.out.println("重命名失败，可能是I/O流被占用,或者不能重命名路径内父路径");
        return false;
    }

    @Override
    public boolean delete(String sourcePath) throws Exception {
        if (sourcePath == null || sourcePath.trim().equals("")) throw new Exception("delete()参数非法");
        File file = new File(sourcePath);
        boolean flag = false;
        if(!file.exists()) {
            System.out.println("虽然要删除的文件或文件夹不存在，但删除方法已经实质完成了工作");
            return true;
        }
        if(file.isFile()) { flag = deleteFile(sourcePath);}
        else if(file.isDirectory()) {flag =  deleteDirectory(sourcePath);}
        return flag;
    }

    @Override
    public boolean copy(String sourcePath, String targetPath) throws Exception {
        if (sourcePath == null || sourcePath.trim().equals("")) throw new Exception("copy()参数非法");
        if (targetPath == null || targetPath.trim().equals("")) throw new Exception("copy()参数非法");
        // System.out.println(sourcePath +"|" +targetPath);
        File file = new File(sourcePath);
        File newfile = new File(targetPath);
        // System.out.println(file.isDirectory() + "|" + file.isFile());
        // System.out.println(newfile.isDirectory() + "|" + newfile.isFile());
        if(!file.exists()) throw new Exception("要复制的源文件的文件不存在，或许你应该先新建文件");
        if(file.isDirectory()){
            // System.out.println( "目录目录");
            if (!targetPath.endsWith(File.separator)) {
                targetPath = targetPath + File.separator + file.getName();
                newfile = new File(targetPath);
                newfile.mkdirs();
            }
            if (!copyFileSync(file, newfile)) return false;
        }else if(file.isFile()){
            // System.out.println( "文件文件");
            String fileType = file.getName().substring(file.getName().indexOf("."));
            // System.out.println(fileType);
            if(!targetPath.endsWith(fileType)){
                if (!targetPath.endsWith(File.separator)) targetPath = targetPath + File.separator;
                newfile = new File(targetPath + file.getName());
            }
            // System.out.println(file.getAbsoluteFile() + "|" + newfile.getAbsolutePath());
            if (!copyFileSync(file, newfile)) return false;
        }

        return true;
    }

    @Override
    public boolean cut(String sourcePath, String targetPath) throws Exception {
        if (sourcePath == null || sourcePath.trim().equals("")) throw new Exception("cut()参数非法");
        if (targetPath == null || targetPath.trim().equals("")) throw new Exception("cut()参数非法");
        boolean copyFlag = copy(sourcePath, targetPath);
        if (!copyFlag) {
            return copyFlag;
        }else {
            boolean deleteFlag = delete(sourcePath);
            if(!deleteFlag) {
                return deleteFlag;
            }
        }
        return true;
    }


    @Override
    public boolean zip(String sourcePath, String targetPath) throws Exception {
        if (sourcePath == null || sourcePath.trim().equals("")) throw new Exception("zip()参数非法");
        if (targetPath == null || targetPath.trim().equals("")) throw new Exception("zip()参数非法");
        if (!zipFiles(sourcePath, targetPath)) {
            System.out.println("解压缩失败, 可能是I/O流占用或打印的语句");
            return false;
        }
        return true;
    }

        @Override
    public boolean unzip(String sourcePath, String targetPath) throws Exception {
        if (sourcePath == null || sourcePath.trim().equals("")) throw new Exception("unzip()参数非法");
        if (targetPath == null || targetPath.trim().equals("")) throw new Exception("unzip()参数非法");
        if (!unzipFiles(sourcePath, targetPath)) {
            System.out.println("解压缩失败, 可能是I/O流占用或打印的语句");
            return false;
        }
        return true;
    }

    private boolean copyFileSync(File file, File newfile) {
        try {
            if(file.isFile()){
                Files.copy(file.toPath(), newfile.toPath());
            } else if (file.isDirectory()) {
                newfile.mkdirs();
                File[] files = file.listFiles();
                for (File tempFile: files) {
                    File tempTargetFile = new File(newfile.getAbsolutePath() + File.separator + tempFile.getName());
                    System.out.println(tempFile.getAbsoluteFile() + "|" + tempTargetFile.getAbsolutePath());
                    copyFileSync(tempFile, tempTargetFile);
                }
            }

        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private boolean deleteFile(String fileName) {
        File file = new File(fileName);
        boolean result = false;
        if(!file.exists()) {System.out.println("不存在此文件"); return result;}
        if(!file.isFile()) {System.out.println("期待是文件，而不是文件夹"); return result;}
        file.delete();
        result = true;
        return result;
    }

    private boolean deleteDirectory(String dir) {
        // If dir does not end with a file separator, the file separator is automatically added
        if (!dir.endsWith(File.separator)) dir = dir + File.separator;
        File dirFile = new File(dir);
        boolean result = false;
        if(!dirFile.exists()) {System.out.println("不存在此文件夹"); return result;}
        if(!dirFile.isDirectory()) {System.out.println("期待是文件夹，而不是文件"); return result;}
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if(file.isFile()) file.delete();
            if(file.isDirectory()) deleteDirectory(file.getAbsolutePath());
        }
        dirFile.delete();
        result = true;
        return  result;
    }

    private boolean unzipFiles(String sourcePath, String targetPath) {
        if (sourcePath == null || sourcePath.trim().equals("")) {
            System.out.println("参数sourcePath非法");return false;  }
        if (targetPath == null || targetPath.trim().equals("")) {
            System.out.println("参数targetPath非法");return false;  }
        if (!sourcePath.endsWith(".zip")) {
            sourcePath = sourcePath + ".zip";}
        File sourceFile = new File(sourcePath);
        File targetPathFile = new File(targetPath);
        if(!sourceFile.exists()){
            System.out.println("文件不存在:"+ sourcePath);return false; }
        if(!targetPathFile.isDirectory()){
            System.out.println("解压目标路径不是目录:" + targetPath);return false; }
        boolean result = false;
        try {
            ZipFile zipFile = new ZipFile(sourcePath);
            Enumeration emu = zipFile.entries();
            int i=0;
            while(emu.hasMoreElements()){
                int BUFFER = 1024;
                ZipEntry entry = (ZipEntry)emu.nextElement();
                //会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
                if (entry.isDirectory()) {
                    new File(targetPath + entry.getName()).mkdirs();
                    continue;
                }
                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                File file = new File(targetPath + entry.getName());
                //加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
                //而这个文件所在的目录还没有出现过，所以要建出目录来。
                File parent = file.getParentFile();
                if(parent != null && (!parent.exists())){
                    parent.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos,BUFFER);
                int count;
                byte data[] = new byte[BUFFER];
                while ((count = bis.read(data, 0, BUFFER)) != -1) {
                    bos.write(data, 0, count);
                }
                bos.flush();
                bos.close();
                bis.close();
            }
            zipFile.close();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean zipFiles(String sourceFilePath,String targetFilePath) {
        if(sourceFilePath == null || targetFilePath == null) return false;

        boolean result = false;
        ZipOutputStream out = null;
        File sourceFile = new File(sourceFilePath);
        if(!sourceFile.exists()) {
            System.out.println("该路径不存在"+ sourceFilePath); return false;
        }
        File targetFile = new File(targetFilePath); // .. zip
        if (!targetFilePath.endsWith(".zip")) {
            if (!targetFilePath.endsWith(File.separator)) {
                targetFilePath = targetFilePath + sourceFile.getName() + ".zip";
                targetFile = new File(targetFilePath);
            }
        }
        try {
            out = new ZipOutputStream(new FileOutputStream(targetFile));
            if(sourceFile.isFile()){
                result = zipFile(sourceFile, out, "");
            } else if(sourceFile.isDirectory()){
                File[] list = sourceFile.listFiles();
                result = true;
                for (int i = 0; i < list.length; i++) {
                    result = result && compress(list[i], out, "");
                }

            }else{
                System.out.println("该路径既不是文件，也不是文件夹，可能不存在");
                return result;
            }

            System.out.println("Zip files complete.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private boolean zipFile(File file, ZipOutputStream out, String folderStr) {
        if (!file.exists()) return false;
        boolean result = false;
        byte[] buf = new byte[1024];
        FileInputStream in = null;
        try {
            int len;
            in = new FileInputStream(file);
            out.putNextEntry(new ZipEntry(folderStr + file.getName()));
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.closeEntry();
                if (in != null) in.close();
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private boolean compress(File file, ZipOutputStream out, String folderStr) {
        if (!file.exists()) return false;
        boolean result = false;
        // Determine whether it is a directory or a file\
        if (file.isDirectory()) {
            folderStr = file.getName();
            File[] files = file.listFiles();
            result = true;
            for (int i = 0; i < files.length; i++) {
                result = result && compress(files[i], out, folderStr + "/");
            }
        } else {
            result = zipFile(file, out, folderStr);
        }
        return result;
    }
}
