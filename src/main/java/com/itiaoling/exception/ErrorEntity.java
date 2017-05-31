package com.itiaoling.exception;

public class ErrorEntity {

	private String resource;
	private String field;
	private String msg;
	
	public ErrorEntity(String resource, String field, String msg) {
		this.resource = resource;
		this.field = field;
		this.msg = msg;
	}
	
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
