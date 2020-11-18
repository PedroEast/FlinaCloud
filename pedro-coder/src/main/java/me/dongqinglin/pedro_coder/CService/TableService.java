package me.dongqinglin.pedro_coder.CService;

import java.util.List;

public interface TableService {
    public List getTables();
    public List getColumns(String tableName);
    public List getCreateTableStr(String tableName);

    public List findAll(String tableName);
    public void drop(String tableName);
}
