package com.camut.utils;

import java.util.HashMap;
import java.util.Map;

import com.camut.framework.constant.GlobalConstant.DELETE_STATUS;

public class UDSqlCommand {

	private String queryString;
	private Map<String, Object> parameters;

	public UDSqlCommand() {
		queryString = null;
		parameters = new HashMap<String, Object>();
	}

	public UDSqlCommand SelectFrom(String tableName) {
		queryString = " FROM " + tableName;
		return this;
	}

	public UDSqlCommand CountFrom(String tableName) {
		queryString = "SELECT count(*) FROM " + tableName;
		return this;
	}

	public UDSqlCommand Where(String condition) {
		queryString += " WHERE " + condition;
		return this;
	}

	public UDSqlCommand WhereIn(String fieldName, Object from, Object to) {
		queryString += " WHERE " + fieldName + " in ( " + from + " , " + to + " )";
		return this;
	}

	public UDSqlCommand WhereIn(String fieldName, Object value) {
		queryString += " WHERE " + fieldName + " in ( " + value + " )";
		return this;
	}

	public UDSqlCommand And(String condition) {
		queryString += " AND " + condition;
		return this;
	}

	public UDSqlCommand GetNonDeletedRecordsOnly() {
		queryString += " AND delete_status=" + DELETE_STATUS.NOT_DELETED.getValue();
		return this;
	}

	public UDSqlCommand GetDeletedRecordsOnly() {
		queryString += " AND delete_status=" + DELETE_STATUS.DELETED.getValue();
		return this;
	}

	public UDSqlCommand OrderBy(String condition) {
		queryString += " order by " + condition;
		return this;
	}

	public UDSqlCommand WithAscOrder() {
		queryString += " ASC";
		return this;
	}

	public UDSqlCommand WithDescOrder() {
		queryString += " DESC";
		return this;
	}

	public UDSqlCommand AddParameters(String parameterName, Object value) {
		for (String key : parameters.keySet()) {
			if (key.equals(parameterName)) {
				Log4jUtil.info("at UDSqlCommand.AddParameters(String parameterName, Object value): Parameter name="
						+ parameterName + " is duplicated");
				return this;
			}
		}
		parameters.put(parameterName, value);
		return this;
	}

	public String GetQueryString() {
		return queryString;
	}

	public Map<String, Object> GetParameters() {
		return parameters;
	}
}
