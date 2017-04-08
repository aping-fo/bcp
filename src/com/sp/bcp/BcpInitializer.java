package com.sp.bcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class BcpInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new BcpDecoder());
		pipeline.addLast(new BcpEncoder());
		pipeline.addLast(new BcpHandler());
		pipeline.addLast(new IdleStateHandler(45,30,60));
	}
}
