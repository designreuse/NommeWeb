package com.camut.model.api;

import java.io.Serializable;
import java.util.Date;

/**
 * @entity ComplainReplyApiModel . 
 * @author zyf	
 * @createTime 2015-05-30
 * @author 
 * @updateTime 
 * @memo 
 */
public class ComplainReplyApiModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2715800748713570169L;
	private long id=0;//评论id
	private Integer replyId=1;// 回复人
	private String replyContent="";// 回复内容
	private Date replyDate=new Date();// 回复时间
	private Integer handleStatus=1;// 处理结果
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Integer getReplyId() {
		return replyId;
	}
	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public Date getReplyDate() {
		return replyDate;
	}
	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}
	public Integer getHandleStatus() {
		return handleStatus;
	}
	public void setHandleStatus(Integer handleStatus) {
		this.handleStatus = handleStatus;
	}
	
}
