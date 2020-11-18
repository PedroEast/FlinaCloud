package me.dongqinglin.pedro_coder.ZEntity;

import javax.persistence.*;
import java.sql.Date;

@Entity(name="user")
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	// GenerationType有四种可能值分别是AUTO,INDENTITY,SEQUENCE 和 TABLE。
	private Integer id;
	@Column(name="email", nullable=true, insertable=true, updatable=true)
	private String email;
	@Column(name="enable", nullable=false, insertable=true, updatable=true)
	private Boolean enable;
	@Column(name="last_password", nullable=true, insertable=true, updatable=true)
	private String lastPassword;
	@Column(name="last_password_modify_time", nullable=true, insertable=true, updatable=true)
	private Date lastPasswordModifyTime;
	@Column(name="level", nullable=false, insertable=true, updatable=true)
	private Integer level;
	@Column(name="password", nullable=true, insertable=true, updatable=true)
	private String password;
	@Column(name="roles", nullable=true, insertable=true, updatable=true)
	private String roles;
	@Column(name="username", nullable=true, insertable=true, updatable=true)
	private String username;


	public User setId(Integer id){this.id = id;return this;}
	public Integer getId(){return this.id;}
	public User setEmail(String email){this.email = email;return this;}
	public String getEmail(){return this.email;}
	public User setEnable(Boolean enable){this.enable = enable;return this;}
	public Boolean getEnable(){return this.enable;}
	public User setLastPassword(String lastPassword){this.lastPassword = lastPassword;return this;}
	public String getLastPassword(){return this.lastPassword;}
	public User setLastPasswordModifyTime(Date lastPasswordModifyTime){this.lastPasswordModifyTime = lastPasswordModifyTime;return this;}
	public Date getLastPasswordModifyTime(){return this.lastPasswordModifyTime;}
	public User setLevel(Integer level){this.level = level;return this;}
	public Integer getLevel(){return this.level;}
	public User setPassword(String password){this.password = password;return this;}
	public String getPassword(){return this.password;}
	public User setRoles(String roles){this.roles = roles;return this;}
	public String getRoles(){return this.roles;}
	public User setUsername(String username){this.username = username;return this;}
	public String getUsername(){return this.username;}


}
