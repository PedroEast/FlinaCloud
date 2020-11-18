package me.dongqinglin.pedro_coder.CServiceImplAlgorithm.fullStackGenerater;


import me.dongqinglin.pedro_coder.CServiceImplAlgorithm.fullStackGenerater.strategy.backendStrategy.BackendStrategy;
import me.dongqinglin.pedro_coder.CServiceImplAlgorithm.fullStackGenerater.strategy.backendStrategy.SpringBootBackendStrategy;
import me.dongqinglin.pedro_coder.DStringApi.bean.ColumnDefined;
import me.dongqinglin.pedro_coder.DFileApi.bean.FileDefined;
import me.dongqinglin.pedro_coder.CServiceImplAlgorithm.fullStackGenerater.strategy.daoStrategy.DaoStrategy;
import me.dongqinglin.pedro_coder.CServiceImplAlgorithm.fullStackGenerater.strategy.daoStrategy.HibernateDaoStrategy;
import me.dongqinglin.pedro_coder.CServiceImplAlgorithm.fullStackGenerater.strategy.frontendStrategy.AngularFrontendStrategy;
import me.dongqinglin.pedro_coder.CServiceImplAlgorithm.fullStackGenerater.strategy.frontendStrategy.FrontendStrategy;
import me.dongqinglin.pedro_coder.DStringApi.sqlStrStrategy.DefaultSqlStrStrategy;
import me.dongqinglin.pedro_coder.DStringApi.sqlStrStrategy.SqlStrStrategy;

import java.util.ArrayList;
import java.util.List;

public class MyStrSytemImpl implements MyStrSytem {

    private final String createTableStr;
    private final SqlStrStrategy strStrategy;
    private final BackendStrategy backendStrategy;
    private final FrontendStrategy frontendStrategy;
    private final DaoStrategy daoStrategy;

    public MyStrSytemImpl(String createTableStr) {
        this.createTableStr = createTableStr;
        this.strStrategy = new DefaultSqlStrStrategy();
        this.backendStrategy = new SpringBootBackendStrategy();
        this.frontendStrategy = new AngularFrontendStrategy();
        this.daoStrategy = new HibernateDaoStrategy();
    }

    public MyStrSytemImpl(String createTableStr, DaoStrategy daoStrategy, BackendStrategy backendStrategy, FrontendStrategy frontendStrategy) {
        this.createTableStr = createTableStr;
        this.strStrategy = new DefaultSqlStrStrategy();
        this.backendStrategy = backendStrategy;
        this.frontendStrategy = frontendStrategy;
        this.daoStrategy = daoStrategy;
    }

    @Override
    public List<FileDefined> generateFileNamesAndDatas() throws Exception {
        if (createTableStr == null || createTableStr.trim().equals("")) throw new Exception("建表语句不能为空");
        int firstLeftRoundBracketIndex = createTableStr.indexOf("(");
        int lastRightRoundBracketIndex = createTableStr.lastIndexOf(")");
        if (firstLeftRoundBracketIndex == -1 || lastRightRoundBracketIndex == -1 ) throw new Exception("建表语句不合法，没有左圆括号`(`或右圆括号`)`");
        List<FileDefined> results = new ArrayList<>();
        String underscroeTableName = strStrategy.getUnderscroeTableName(createTableStr);
        List<ColumnDefined> underscroeColumns = strStrategy.getUnderscroeColumns(createTableStr);
        List<FileDefined> daoFiles = daoStrategy.generateFiles(underscroeTableName, underscroeColumns);
        List<FileDefined> backendFiles = backendStrategy.generateFiles(underscroeTableName, underscroeColumns);
        List<FileDefined> frontendFiles = frontendStrategy.generateFiles(underscroeTableName, underscroeColumns);
        results.addAll(daoFiles);
        results.addAll(backendFiles);
        results.addAll(frontendFiles);
        // results = addListContentFromTo(daoFiles, results);
        return results;
    }

   /* private List<FileDefined> addListContentFromTo(List<FileDefined> files, List<FileDefined> results) {
        for (int i = 0; i < files.size(); i++) {
            results.add(files.get(i));
        }
        return results;
    }*/
}
