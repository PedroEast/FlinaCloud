package me.dongqinglin.pedro_coder.ZBean;

public class MessageDefined {


    private int code;
    private String metadata;
    private String data;

    public MessageDefined() {
    }

    public MessageDefined(int code, String metadata, String data) {
        this.code = code;
        this.metadata = metadata;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMetadata() {
        return metadata;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "MessageDefined{" +
                "code=" + code +
                ", metadata='" + metadata + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
