package com.sp.bcp;

import com.sp.domain.Cmd;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;

public class BcpHandler extends SimpleChannelInboundHandler<Packet> {
	@Override
	public void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
		if(msg.cmd == Cmd.ACK) {
			BcpManger.acknowledge(ctx.channel().id(), msg.seq);
		} 
		else if(msg.cmd == Cmd.LOGIN) { //登录认证，一般采用RSA非对称加密，这里就省了
			BcpManger.addSesion(new BcpSession(ctx.channel()));
		}
		else { //需要先登录认证
			//TODO 其他业务
			boolean ret = BcpManger.addPacket(ctx.channel().id(), msg);
			if(!ret) {
				//关闭连接，客户端重新登录认证
				ctx.channel().close();
			}
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		//TODO
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