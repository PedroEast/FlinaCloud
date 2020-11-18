package me.dongqinglin.pedro_coder.DStringApi.bean;

public class ColumnDefined {
    private String columnName;
    private String columnType;
    private boolean nullable;
    private String indexType;

    private String extra;

    public ColumnDefined() {
    }

    public String getColumnName() {
        return columnName;
    }

    public ColumnDefined setColumnName(String columnName) {
        this.columnName = columnName;
        return this;

    }

    public String getColumnType() {
        return columnType;
    }

    public ColumnDefined setColumnType(String columnType) {
        this.columnType = columnType;
        return this;

    }

    public boolean isNullable() {
        return nullable;
    }

    public ColumnDefined setNullable(boolean nullable) {
        this.nullable = nullable;
        return this;

    }

    public String getIndexType() {
        return indexType;
    }

    public ColumnDefined setIndexType(String indexType) {
        this.indexType = indexType;
        return this;

    }

    public String getExtra() {
        return extra;
    }

    public ColumnDefined setExtra(String extra) {
        this.extra = extra;
        return this;
    }

    @Override
    public String toString() {
        return "ColumnDefined{" +
                "columnName='" + columnName + '\'' +
                ", columnType='" + columnType + '\'' +
                ", nullable=" + nullable +
                ", indexType='" + indexType + '\'' +
                ", extra='" + extra + '\'' +
                '}';
    }
}
