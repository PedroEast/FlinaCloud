package me.dongqinglin.pedro_coder.ZBean;

public class ColumnDefined {
    private String Field;
    private String Null;
    private String Type;
    private String Key;
    private String Default;
    private String Extra;

    public ColumnDefined() {
    }

    public ColumnDefined(String field, String aNull, String type, String key, String aDefault, String extra) {
        Field = field;
        Null = aNull;
        Type = type;
        Key = key;
        Default = aDefault;
        Extra = extra;
    }

    public String getField() {
        return Field;
    }

    public String getNull() {
        return Null;
    }

    public String getType() {
        return Type;
    }

    public String getKey() {
        return Key;
    }

    public String getDefault() {
        return Default;
    }

    public String getExtra() {
        return Extra;
    }

    @Override
    public String toString() {
        return "ColumnDefined{" +
                "Field='" + Field + '\'' +
                ", Null='" + Null + '\'' +
                ", Type='" + Type + '\'' +
                ", Key='" + Key + '\'' +
                ", Default='" + Default + '\'' +
                ", Extra='" + Extra + '\'' +
                '}';
    }
}
