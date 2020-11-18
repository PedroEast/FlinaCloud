package me.dongqinglin.zuul_gateway.ZBean;

public class MessageDefined {
    private int code;
    private String metadata;
    private String data;

    public MessageDefined(int code, String metadata, String data) {
        this.code = code;
        this.metadata = metadata;
        this.data = data;
    }

    public MessageDefined() {
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
