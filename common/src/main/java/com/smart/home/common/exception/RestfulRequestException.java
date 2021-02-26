package com.smart.home.common.exception;

public class RestfulRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code;

	public RestfulRequestException(int code, String message) {
		super(message);
		this.code = code;
	}

	public RestfulRequestException(String message) {
		super(message);
		this.code = 500;
	}

	public int getCode() {
		return code;
	}

	
}
