package me.dongqinglin.pedro_coder.DStringApi.sqlStrStrategy;


import me.dongqinglin.pedro_coder.DStringApi.bean.ColumnDefined;

import java.util.List;

public interface SqlStrStrategy {
    String getUnderscroeTableName(String createTableStr) throws Exception;

    List<ColumnDefined> getUnderscroeColumns(String createTableStr) throws Exception;

}
