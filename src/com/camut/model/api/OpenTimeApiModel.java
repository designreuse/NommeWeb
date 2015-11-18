package com.camut.model.api;

import java.io.Serializable;

/**
 * @entity OpenTimeApiModel .
 * @author zyf
 * @createTime 2015-05-30
 * @author
 * @updateTime
 * @memo
 */
public class OpenTimeApiModel implements Serializable,Comparable<OpenTimeApiModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9136370800562992061L;
	private long openTimeId; // 营业时间id
	private Integer week;// 星期
	private String opentime = "";
	private Integer type;// 类型：自取或者外送或者预定
	private String weekName;

	public long getOpenTimeId() {
		return openTimeId;
	}

	public void setOpenTimeId(long openTimeId) {
		this.openTimeId = openTimeId;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public String getOpentime() {
		return opentime;
	}

	public void setOpentime(String opentime) {
		this.opentime = opentime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getWeekName() {
		String str="";
		switch (week) {
		case 1:
			str="Monday";
			break;
		case 2:
			str="Tuesday";
			break;
		case 3:
			str="Wednesday";
			break;
		case 4:
			str="Thursday";
			break;
		case 5:
			str="Friday";
			break;
		case 6:
			str="Saturday";
			break;
		default:
			str="Sunday";
			break;
		}
		return str;
	}

	public void setWeekName(String weekName) {
		this.weekName = weekName;
	}

	@Override
    public int compareTo(OpenTimeApiModel arg0) {
        return this.week.compareTo(arg0.week);
    }
}
