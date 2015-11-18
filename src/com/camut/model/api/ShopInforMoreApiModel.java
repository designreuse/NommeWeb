package com.camut.model.api;

import java.util.List;
import java.util.Map;

public class ShopInforMoreApiModel {
	
	private String features;// 特征,介绍
	private List<Map<String, Object>> opentime;
	private List<EvaluateApiModel> evaluate;
	public String getFeatures() {
		return features;
	}
	public void setFeatures(String features) {
		this.features = features;
	}
	public List<Map<String, Object>> getOpentime() {
		return opentime;
	}
	public void setOpentime(List<Map<String, Object>> opentime) {
		this.opentime = opentime;
	}
	public List<EvaluateApiModel> getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(List<EvaluateApiModel> evaluate) {
		this.evaluate = evaluate;
	}
	
	
	
	
}
