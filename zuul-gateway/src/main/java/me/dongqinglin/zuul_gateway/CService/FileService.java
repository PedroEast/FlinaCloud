package me.dongqinglin.zuul_gateway.CService;

import org.springframework.web.multipart.MultipartFile;

/**
 * This class is the interface of file service. File service refers to file operations such as creating new folders, moving and copying.
 * This class uses member methods to define different behaviors, and strictly defines the parameters of each method.
 * Some parameters have been optimized, so that these illegal parameters can be corrected and the fault tolerance rate is increased.
 * This type of file operation includes, folder listing, new folder, new file, edit file, preview file, rename file or folder,
 * delete file or folder, copy file or folder, move file or folder, compress File or folder, unzip the file or folder, handleUpload the file to the specified folder.
 * It should be noted that the compression format currently only supports .zip files,
 * corresponding to `dir`, `newFolder`, `newFile`, `editFile`, `view`, `rename`, `copy`, `cut`, `zip` , `unzip`, `handleUpload`.
 *
 * 本类是文件服务的接口，文件服务指新建文件夹、移动和复制等文件操作。本类使用了成员方法来定义不同的行为，并严格定义了各个方法的参数，
 * 部分参数做了优化处理，使得这部分非法的参数得以校正，增加容错率。
 * 本类的文件操作包括，文件夹列举，新建文件夹，新建文件，编辑文件，预览文件，重命名文件或文件夹，删除文件或文件夹，复制文件或文件夹，移动文件或文件夹，
 * 压缩文件或文件夹，解压缩文件或文件夹，上传文件到指定文件夹。
 * 需要注意的是压缩格式目前只支持.zip文件。
 * 分别对应`dir`，`newFolder`，`newFile`，`editFile`，`view`，`rename`，`copy`，`cut`，`zip`，`unzip`，`handleUpload`。
 */
public interface FileService {
    String getStaticRootStr();

    boolean handleUpload(MultipartFile[] files, String targetPath) throws Exception;

}
