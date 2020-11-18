package me.dongqinglin.zuul_gateway.ZBean;


import me.dongqinglin.zuul_gateway.ZEntity.User;

public class SignInResponse {
    private String jwt;
    private User user;

    public SignInResponse(String jwt, User user) {
        this.jwt = jwt;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getJwt() {
        return jwt;
    }
}
