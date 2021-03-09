package com.smart.home.common.exception;

/**
 * @author jason
 */
public class ServiceException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int code;

	public ServiceException(int code, String message) {
		super(message);
		this.code = code;
	}

	public ServiceException(String message) {
		super(message);
		this.code = 1;
	}

	public int getCode() {
		return code;
	}

	
}
