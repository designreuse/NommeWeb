/**
 * 
 */
package com.camut.pageModel;

/**
 * @ClassName PageTable.java
 * @author wangpin
 * @createtime Jun 24, 2015
 * @author
 * @updateTime
 * @memo
 */
public class PageTable {

	private long id;//主键
	private Integer acceptanceNum;//桌位容纳人数
    private Integer tableNum;//桌位数量
	public Integer getAcceptanceNum() {
		return acceptanceNum;
	}
	public void setAcceptanceNum(Integer acceptanceNum) {
		this.acceptanceNum = acceptanceNum;
	}
	public Integer getTableNum() {
		return tableNum;
	}
	public void setTableNum(Integer tableNum) {
		this.tableNum = tableNum;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
    
    
}
