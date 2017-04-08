package com.sp.bcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class BcpDecoder extends ByteToMessageDecoder {

	private static final int HEAD_SIZE = 8;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,List<Object> out) {
		if (in.readableBytes() < HEAD_SIZE) {
			return;
		}

		in.markReaderIndex();
		short cmd = in.readShort();
		short dataLength = in.readShort();
		int seq = in.readInt();
		if (in.readableBytes() < dataLength) {
			in.resetReaderIndex();
			return;
		}
		byte[] data = new byte[dataLength];
		in.readBytes(data);
		out.add(Packet.valueOf(cmd, seq, data));
	}
}
