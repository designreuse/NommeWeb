package com.camut.pageModel;

import java.util.Date;

public class PageEvaluate {
	
	private String content;// 评价内容 review details
	private double score;// 评分 rating
	private Date createtime;// 创建时间
	private String consumer;//顾客名称
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getConsumer() {
		return consumer;
	}
	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}
	
	
	
	
}
