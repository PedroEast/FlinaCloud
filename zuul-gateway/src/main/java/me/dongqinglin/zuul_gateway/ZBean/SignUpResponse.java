package me.dongqinglin.zuul_gateway.ZBean;

public class SignUpResponse {
    private boolean state;
    private String msg;

    public SignUpResponse(boolean state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }
}
