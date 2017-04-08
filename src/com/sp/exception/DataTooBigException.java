package com.sp.exception;

public class DataTooBigException extends BcpException{
	private static final long serialVersionUID = 2500224368930495854L;

	public DataTooBigException() {
		super("The data received was too big!", null);
	}

	public DataTooBigException(String message, Throwable cause) {
		super(message, cause);
	}
}
