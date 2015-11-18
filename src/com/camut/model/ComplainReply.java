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
 * @entity ComplainReply . 
 * @author 王频
 * @createTime 2015-05-25
 * @author 
 * @updateTime 
 * @memo 
 */
@Entity
@Table(name = "tbl_complain_reply", catalog = "nomme")
public class ComplainReply extends IdEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1034773360833033496L;
	private Complain complain;// 与投诉一对一，单向关联
	private Integer replyId;// 回复人
	private String replyContent;// 回复内容
	private Date replyDate;// 回复时间
	private Integer handleStatus;// 处理结果

	// Property accessors

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="reply_id")
	public Complain getComplain() {
		return complain;
	}

	public void setComplain(Complain complain) {
		this.complain = complain;
	}

	@Column(name = "reply_id", nullable = false,insertable=false,updatable=false)
	public Integer getReplyId() {
		return this.replyId;
	}

	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}

	@Column(name = "reply_content", nullable = false, length = 1000)
	public String getReplyContent() {
		return this.replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "reply_date", nullable = false, length = 10)
	public Date getReplyDate() {
		return this.replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

	@Column(name = "handle_status", nullable = false)
	public Integer getHandleStatus() {
		return this.handleStatus;
	}

	public void setHandleStatus(Integer handleStatus) {
		this.handleStatus = handleStatus;
	}

}