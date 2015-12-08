package com.camut.model;

public class ApiResponse {
	private int statusEnum;
	private String responseString;
	private String message;
	
	public ApiResponse() {
		this.statusEnum = 0;
		this.responseString = null;
		this.message = null;
	}
	
	public ApiResponse(int statusEnum, String responseString, String message) {
		this.statusEnum = statusEnum;
		this.responseString = responseString;
		this.message = message;
	}
	
	public int getStatusEnum() {
		return statusEnum;
	}
	public void setStatusEnum(int statusEnum) {
		this.statusEnum = statusEnum;
	}
	
	public String getResponseString() {
		return responseString;
	}
	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
