package com.sp.exception;

public class UnknownHeadByte extends BcpException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7933287620439484682L;

	public UnknownHeadByte(String message, Throwable cause) {
		super(message, cause);
	}
	
	public UnknownHeadByte() {
		super("unknown head exception", null);
	}
}
