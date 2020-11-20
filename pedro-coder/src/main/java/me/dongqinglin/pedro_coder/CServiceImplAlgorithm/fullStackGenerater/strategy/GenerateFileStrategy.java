package me.dongqinglin.pedro_coder.CServiceImplAlgorithm.fullStackGenerater.strategy;



import me.dongqinglin.pedro_coder.DStringApi.bean.ColumnDefined;
import me.dongqinglin.pedro_coder.DFileApi.MyFileSystem;
import me.dongqinglin.pedro_coder.DFileApi.MyFileSystemImpl;
import me.dongqinglin.pedro_coder.DFileApi.bean.FileDefined;

import java.util.List;

public interface GenerateFileStrategy {
    String STATIC_ROOT = "D:/static/";
    MyFileSystem fileSystem = new MyFileSystemImpl();

    public List<FileDefined> generateFiles(String underscroeTableName, List<ColumnDefined> underscroeColumns) throws Exception;
}
