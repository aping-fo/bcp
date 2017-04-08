package com.sp.exception;

import java.io.IOException;

public class BcpException extends IOException{
	private static final long serialVersionUID = -6747773721144663670L;
	
	public BcpException(String message, Throwable cause) {
		super(message, cause);
	}
}
