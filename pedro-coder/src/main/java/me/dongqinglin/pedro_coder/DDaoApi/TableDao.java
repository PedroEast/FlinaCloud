package me.dongqinglin.pedro_coder.DDaoApi;

import me.dongqinglin.pedro_coder.ZBean.ColumnDefined;

import java.util.List;

public interface TableDao {
    public List getTables();
    public List<ColumnDefined> getColumns(String tableName);
    public List getCreateTableStr(String tableName);
    public List findAll(String tableName);

    public void drop(String tableName);
}
