package com.camut.pageModel;

/**
 * @ClassName PageOpenTime.java
 * @author wangpin
 * @createtime Jun 24, 2015
 * @author
 * @updateTime
 * @memo
 */
public class PageOpenTime implements Comparable<PageOpenTime> {

	private long id;//主键
	private Integer week;// 星期 //this one actually is day: Monday, Tuesday...Sunday
	private String starttime;// 开始时间
	private String endtime;// 结束时间
	private Integer type;//自取 外送 就餐
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Override
	public int compareTo(PageOpenTime o) {
		// TODO Auto-generated method stub
		return (int) (this.week-o.week);
	}
	
}
