package com.camut.pageModel;

import java.io.Serializable;
import java.util.List;

public class PageMessage implements Serializable {
	
private static final long serialVersionUID = 4549689561573640893L;
	
	/**
	 * 返回给前台页面的信息
	 */
	private String errorMsg;//错误信息; error message
	
	private boolean success=true;  //是否成; success or not
	
	private List<?> list;//对象的List
	
	private int flag=0;//状态码标记; status flag
	
	
	
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	
	

}
