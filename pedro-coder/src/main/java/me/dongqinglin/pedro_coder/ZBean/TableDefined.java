package me.dongqinglin.pedro_coder.ZBean;

import java.util.List;

public class TableDefined {
    private String tableName;
    private List<ColumnDefined> columns;
    private List createTable;

    public TableDefined() {
    }

    public TableDefined(String tableName, List<ColumnDefined> columns, List createTable) {
        this.tableName = tableName;
        this.columns = columns;
        this.createTable = createTable;
    }

    public String getTableName() {
        return tableName;
    }

    public List<ColumnDefined> getColumns() {
        return columns;
    }

    public List getCreateTable() {
        return createTable;
    }
}
