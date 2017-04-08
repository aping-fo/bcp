package com.sp.bcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;

import java.math.BigInteger;

public class BcpHandler extends SimpleChannelInboundHandler<BigInteger> {

	private BigInteger lastMultiplier = new BigInteger("1");
	private BigInteger factorial = new BigInteger("1");

	@Override
	public void channelRead0(ChannelHandlerContext ctx, BigInteger msg)
			throws Exception {
		// Calculate the cumulative factorial and send it to the client.
		lastMultiplier = msg;
		factorial = factorial.multiply(msg);
		ctx.writeAndFlush(factorial);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.err.printf("Factorial of %,d is: %,d%n", lastMultiplier,
				factorial);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
		cause.printStackTrace();
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		IdleState state = (IdleState) evt;
		switch (state) {
		case READER_IDLE:
			break;
		case WRITER_IDLE:
			break;
		case ALL_IDLE:
			break;
		default:
			break;
		}
	}

}
