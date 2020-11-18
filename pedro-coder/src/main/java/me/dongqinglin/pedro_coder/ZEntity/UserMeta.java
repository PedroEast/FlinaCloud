package me.dongqinglin.pedro_coder.ZEntity;

import javax.persistence.*;

@Entity(name="user_meta")
@Table(name="user_meta")
public class UserMeta {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	// GenerationType有四种可能值分别是AUTO,INDENTITY,SEQUENCE 和 TABLE。
	private Integer id;
	@Column(name="enable", nullable=false, insertable=true, updatable=true, columnDefinition="bit(1)")
	private Boolean enable;
	@Column(name="college", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
	private String college;
	@Column(name="concat", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
	private String concat;
	@Column(name="name", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
	private String name;
	@Column(name="student_id", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
	private String studentId;
	@ManyToOne
	@JoinColumn(name = "author_id")
	private User authorId;

	public UserMeta setId(Integer id){this.id = id;return this;}
	public Integer getId(){return this.id;}
	public UserMeta setEnable(Boolean enable){this.enable = enable;return this;}
	public Boolean getEnable(){return this.enable;}
	public UserMeta setCollege(String college){this.college = college;return this;}
	public String getCollege(){return this.college;}
	public UserMeta setConcat(String concat){this.concat = concat;return this;}
	public String getConcat(){return this.concat;}
	public UserMeta setName(String name){this.name = name;return this;}
	public String getName(){return this.name;}
	public UserMeta setStudentId(String studentId){this.studentId = studentId;return this;}
	public String getStudentId(){return this.studentId;}
	public UserMeta setAuthorId(User authorId){this.authorId = authorId;return this;}
	public User getAuthorId(){return this.authorId;}


}
