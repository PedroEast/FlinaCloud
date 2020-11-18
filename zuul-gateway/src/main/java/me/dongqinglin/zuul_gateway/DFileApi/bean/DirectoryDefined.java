package me.dongqinglin.zuul_gateway.DFileApi.bean;

import java.util.ArrayList;
import java.util.List;


/**
 * This class is a nested data structure, because the file structure is also a nested data structure.
 * There are two types of data in the computer system: path and file. Paths and files can be nested,
 * but files cannot be nested. So starting from the path, the data structure of this file structure
 * can be abstracted. The private member variables of the path are the name of the path itself `diretoryName`,
 * the name collection of files under the path `files` and the object collection of paths under the path `directories`.
 * The member methods are adding the element `addFile()` to the name set of the file under the path and
 * adding the element `addtDirectory()` to the path pair under the path.
 *
 * 本类是嵌套的数据结构，因为文件结构也是嵌套数据结构。计算机系统中共计存在路径和文件两类数据，
 * 而路径可以嵌套路径和文件，文件不能嵌套。所以从路径出发，可以抽象出这个文件结构的数据结构。
 * 路径的私有成员变量为路径本身的名称`diretoryName`和路径下文件的名称集合`files`和路径下路径的对象集合`directories`。
 * 成员方法为向路径下文件的名称集合中添加元素`addFile()`和向路径下的路径对下添加元素`addtDirectory()`。
 */
public class DirectoryDefined {
    List<String> files = new ArrayList<String>();
    String diretoryName = "";
    List<DirectoryDefined> directories = new ArrayList<DirectoryDefined>();

    public DirectoryDefined() {
    }

    public DirectoryDefined(List<String> files, String diretoryName, List<DirectoryDefined> directories) {
        this.files = files;
        this.diretoryName = diretoryName;
        this.directories = directories;
    }

    public List<String> getFiles() {
        return files;
    }

    public List<DirectoryDefined> getDirectories() {
        return directories;
    }

    public String getDiretoryName() {
        return diretoryName;
    }

    public void setDiretoryName(String diretoryName) {
        this.diretoryName = diretoryName;
    }

    public DirectoryDefined addFile(String file){
        if (file == null || file.trim().equals("")) {   return this;    }
        files.add(file);
        return this;
    }

    public DirectoryDefined deleteFile(String file){
        if (file == null || file.trim().equals("")) {   return this;    }
        files.remove(file);
        return this;
    }

    public DirectoryDefined addtDirectory(DirectoryDefined directory){
        if (directory == null) {   return this;   }
        directories.add(directory);
        return this;
    }

    public DirectoryDefined deleteDirectory(DirectoryDefined directory){
        if (directory == null) {   return this;   }
        directories.remove(directory);
        return this;
    }

    @Override
    public String toString() {
        return "\nDirectory{" +
                "files=" + files +
                "\n, diretoryName='" + diretoryName + '\'' +
                "\n, directories=" + directories +
                '}';
    }
}
