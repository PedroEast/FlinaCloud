package me.dongqinglin.zuul_gateway.ZBean;

public class StateDefined {
    private int code;

    public StateDefined() {
    }

    public StateDefined(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "StateDefined{" +
                "code=" + code +
                '}';
    }
}
