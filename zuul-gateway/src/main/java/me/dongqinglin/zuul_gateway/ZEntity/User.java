package me.dongqinglin.zuul_gateway.ZEntity;

import javax.persistence.*;
import java.util.Date;

@Entity(name="user")
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Column(name="username", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String username;
    @Column(name="password", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String password;

    @Column(name="last_password", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String lastPassword;
    @Column(name="last_password_modify_time", nullable=true, insertable=true, updatable=true, columnDefinition="date")
    private Date lastPasswordModifyTime;
    @Column(name="email", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String email;

    // 角色授权 插入时以ROLE_前缀开始，使用时controller不需要前缀
    // 权限授权，不需要前缀一致便好
    @Column(name="roles", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String roles;
    @Column(name="level", nullable=false, insertable=true, updatable=true)
    private int level;
    @Column(name="enable", nullable=false, insertable=true, updatable=true, columnDefinition="bit")
    private boolean enable;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastPassword() {
        return lastPassword;
    }

    public void setLastPassword(String lastPassword) {
        this.lastPassword = lastPassword;
    }

    public Date getLastPasswordModifyTime() {
        return lastPasswordModifyTime;
    }

    public void setLastPasswordModifyTime(Date lastPasswordModifyTime) {
        this.lastPasswordModifyTime = lastPasswordModifyTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}