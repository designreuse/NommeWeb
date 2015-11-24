package com.camut.model;

// default package

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @entity Complain . 
 * @author 王频
 * @createTime 2015-05-25
 * @author 
 * @updateTime 
 * @memo 
 */
@Entity
@Table(name = "tbl_complain")
public class Complain extends IdEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1939971342659813336L;
	private String title;// 投诉标题
	private String content;// 投诉内容
	private Date createdate;// 投诉时间
	private Consumers consumers;// 与会员一对一，单向关联

	// Property accessors

	@Column(name = "title", nullable = false, length = 200)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content", nullable = false, length = 1000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "createdate", nullable = false, length = 10)
	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="consumer_id")
	public Consumers getConsumers() {
		return consumers;
	}

	public void setConsumers(Consumers consumers) {
		this.consumers = consumers;
	}

}