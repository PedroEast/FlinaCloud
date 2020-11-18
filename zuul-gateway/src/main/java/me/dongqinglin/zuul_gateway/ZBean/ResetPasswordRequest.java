package me.dongqinglin.zuul_gateway.ZBean;

public class ResetPasswordRequest {
    private String username;
    private String password;
    private String email;
    private String code;

    public ResetPasswordRequest() {
    }

    public ResetPasswordRequest(String username, String password, String email, String code) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "ResetPasswordRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
