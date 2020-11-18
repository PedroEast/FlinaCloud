package me.dongqinglin.pedro_coder.CServiceImplAlgorithm;

import me.dongqinglin.pedro_coder.DFileApi.bean.FileDefined;

import java.util.List;

public interface FullStackGeneraterFacade {

    List<FileDefined> generateFile() throws Exception;
}
