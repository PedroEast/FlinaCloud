package me.dongqinglin.pedro_coder.CServiceImplAlgorithm.fullStackGenerater.strategy.daoStrategy;


import me.dongqinglin.pedro_coder.DStringApi.bean.ColumnDefined;
import me.dongqinglin.pedro_coder.DStringApi.MyStrUtil;
import me.dongqinglin.pedro_coder.DFileApi.bean.FileDefined;

import java.util.ArrayList;
import java.util.List;


public class HibernateDaoStrategy implements DaoStrategy {

    @Override
    public List<FileDefined> generateFiles(String underscroeTableName, List<ColumnDefined> underscroeColumns) throws Exception {
        List<FileDefined> results = new ArrayList<>();
        FileDefined daoFile = generateDao(underscroeTableName);
        results.add(daoFile);
        return results;
    }

    private FileDefined generateDao(String underscroeTableName) throws Exception {
        FileDefined result = null;
        String littleHumpTableName = MyStrUtil.underscroeToLittleHump(underscroeTableName);
        String bigHumpTableName = MyStrUtil.littleHumpToBig(littleHumpTableName);
        String fileName = bigHumpTableName + "Dao.java";

        String fileData = "";
        fileData = fileSystem.view(STATIC_ROOT + "template/dao_hibernate_dao.template");
        fileData = fileData.replaceAll("`tableNameBigHump`", bigHumpTableName);
        result = new FileDefined(fileName, fileData);
        return result;
    }
}
