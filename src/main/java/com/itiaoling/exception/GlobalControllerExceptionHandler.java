package com.itiaoling.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

	@ExceptionHandler(value = { IllegalArgumentException.class })
	public void handleBadRequests(HttpServletResponse response, NullPointerException ex) throws IOException{
		response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	}
	
}
