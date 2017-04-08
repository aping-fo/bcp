package com.sp.exception;

public class AlreadyReceivedFinish extends BcpException{
	private static final long serialVersionUID = 1090944492452040531L;

	public AlreadyReceivedFinish() {
		super("Already received the Finish Packet", null);
	}

	public AlreadyReceivedFinish(String message, Throwable cause) {
		super(message, cause);
	}
}
