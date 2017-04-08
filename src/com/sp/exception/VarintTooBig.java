package com.sp.exception;

public class VarintTooBig extends BcpException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4085152651109101874L;

	public VarintTooBig(String message, Throwable cause) {
		super(message, cause);
	}

	public VarintTooBig() {
		super("The varint is too big to read!", null);
	}
}
