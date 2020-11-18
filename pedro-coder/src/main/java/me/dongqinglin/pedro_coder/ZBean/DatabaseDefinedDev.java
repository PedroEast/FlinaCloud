package me.dongqinglin.pedro_coder.ZBean;

public class DatabaseDefinedDev {
    private String Tables_in_auto_code;

    public DatabaseDefinedDev() {
    }

    public DatabaseDefinedDev(String tables_in_auto_code) {
        Tables_in_auto_code = tables_in_auto_code;
    }

    public String getTables_in_auto_code() {
        return Tables_in_auto_code;
    }

    @Override
    public String toString() {
        return "DatabaseDefinedDev{" +
                "Tables_in_auto_code='" + Tables_in_auto_code + '\'' +
                '}';
    }
}
