package me.dongqinglin.pedro_coder.ZBean;

public class DatabaseDefindProd {

    private String Tables_in_flina;

    public DatabaseDefindProd() {
    }

    public DatabaseDefindProd(String tables_in_flina) {
        Tables_in_flina = tables_in_flina;
    }

    public String getTables_in_flina() {
        return Tables_in_flina;
    }

    @Override
    public String toString() {
        return "DatabaseDefindProd{" +
                "Tables_in_flina='" + Tables_in_flina + '\'' +
                '}';
    }
}
