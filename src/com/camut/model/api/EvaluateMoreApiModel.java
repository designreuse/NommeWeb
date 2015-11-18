package com.camut.model.api;

import java.util.Date;

public class EvaluateMoreApiModel {
	
	private String content="";// 评价内容 
	private Date createtime=new Date();// 创建时间
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	
}
