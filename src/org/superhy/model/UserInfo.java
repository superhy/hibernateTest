package org.superhy.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * UserInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_info", catalog = "frameworktest_db")
public class UserInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String pass;
	private Double weight;
	private Timestamp birth;
	private Set<BookInfo> bookInfos = new HashSet<BookInfo>(0);

	// Constructors

	/** default constructor */
	public UserInfo() {
	}

	/** minimal constructor */
	public UserInfo(String name, String pass, Double weight, Timestamp birth) {
		this.name = name;
		this.pass = pass;
		this.weight = weight;
		this.birth = birth;
	}

	/** full constructor */
	public UserInfo(String name, String pass, Double weight, Timestamp birth,
			Set<BookInfo> bookInfos) {
		this.name = name;
		this.pass = pass;
		this.weight = weight;
		this.birth = birth;
		this.bookInfos = bookInfos;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "pass", length = 20)
	public String getPass() {
		return this.pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Column(name = "weight", precision = 22, scale = 0)
	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Column(name = "birth", length = 19)
	public Timestamp getBirth() {
		return this.birth;
	}

	public void setBirth(Timestamp birth) {
		this.birth = birth;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userInfo")
	public Set<BookInfo> getBookInfos() {
		return this.bookInfos;
	}

	public void setBookInfos(Set<BookInfo> bookInfos) {
		this.bookInfos = bookInfos;
	}

}