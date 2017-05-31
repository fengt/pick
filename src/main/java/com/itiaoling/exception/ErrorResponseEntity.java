package com.itiaoling.exception;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponseEntity {

    private int status;
    private String message;
    private List<ErrorEntity> errors = new ArrayList<ErrorEntity>();
    
    
    public ErrorResponseEntity() {
		super();
	}


	public ErrorResponseEntity(int status, String message, List<ErrorEntity> errors) {
		super();
		this.status = status;
		this.message = message;
		this.errors = errors;
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public List<ErrorEntity> getErrors() {
		return errors;
	}



	public void setErrors(List<ErrorEntity> errors) {
		this.errors = errors;
	}


}
