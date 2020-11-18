package me.dongqinglin.pedro_coder.CServiceImplAlgorithm;




import me.dongqinglin.pedro_coder.CServiceImplAlgorithm.fullStackGenerater.MyStrSytem;
import me.dongqinglin.pedro_coder.CServiceImplAlgorithm.fullStackGenerater.MyStrSytemImpl;
import me.dongqinglin.pedro_coder.CServiceImplAlgorithm.fullStackGenerater.strategy.backendStrategy.BackendStrategy;
import me.dongqinglin.pedro_coder.DFileApi.bean.FileDefined;
import me.dongqinglin.pedro_coder.CServiceImplAlgorithm.fullStackGenerater.strategy.daoStrategy.DaoStrategy;
import me.dongqinglin.pedro_coder.CServiceImplAlgorithm.fullStackGenerater.strategy.frontendStrategy.FrontendStrategy;

import java.util.List;

public class FullStackGeneraterFacadeImpl implements FullStackGeneraterFacade {

    private final MyStrSytem strSystem;

    public FullStackGeneraterFacadeImpl(String createTableStr) {
        this.strSystem = new MyStrSytemImpl(createTableStr);
    }

    public FullStackGeneraterFacadeImpl(String createTableStr, String daoStrategy, String backendStrategy, String frontendStrategy) {
        MyStrSytem strSystem1;
        String packageName = "me.dongqinglin.pedro_coder.CServiceImplAlgorithm.fullStackGenerater.strategy";
        daoStrategy = packageName + ".daoStrategy." + daoStrategy + "DaoStrategy";
        backendStrategy = packageName + ".backendStrategy." + daoStrategy + "BackendStrategy";
        frontendStrategy = packageName + ".frontendStrategy." + daoStrategy + "FrontendStrategy";
        try {
            Class<?> daoClass = Class.forName(daoStrategy);
            Class<?> backendClass = Class.forName(backendStrategy);
            Class<?> frontendClass = Class.forName(frontendStrategy);
            DaoStrategy daoStrategy1 = (DaoStrategy) daoClass.getConstructor().newInstance();
            BackendStrategy backendStrategy1 = (BackendStrategy) backendClass.getConstructor().newInstance();
            FrontendStrategy frontendStrategy1 = (FrontendStrategy) frontendClass.getConstructor().newInstance();
            strSystem1 = new MyStrSytemImpl(createTableStr, daoStrategy1, backendStrategy1, frontendStrategy1);
        } catch (Exception e) {
            strSystem1 = new MyStrSytemImpl(createTableStr);
        }
        this.strSystem = strSystem1;
    }

    @Override
    public List<FileDefined> generateFile() throws Exception {
        List<FileDefined> files= strSystem.generateFileNamesAndDatas();
        return files;
    }


}
