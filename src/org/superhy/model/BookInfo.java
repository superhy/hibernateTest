package org.superhy.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * BookInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "book_info", catalog = "frameworktest_db")
public class BookInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private UserInfo userInfo;
	private String name;
	private String publish;
	private Timestamp publishtime;

	// Constructors

	/** default constructor */
	public BookInfo() {
	}

	/** minimal constructor */
	public BookInfo(UserInfo userInfo, String name) {
		this.userInfo = userInfo;
		this.name = name;
	}

	/** full constructor */
	public BookInfo(UserInfo userInfo, String name, String publish,
			Timestamp publishtime) {
		this.userInfo = userInfo;
		this.name = name;
		this.publish = publish;
		this.publishtime = publishtime;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id")
	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "publish", length = 20)
	public String getPublish() {
		return this.publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	@Column(name = "publishtime", length = 19)
	public Timestamp getPublishtime() {
		return this.publishtime;
	}

	public void setPublishtime(Timestamp publishtime) {
		this.publishtime = publishtime;
	}

}