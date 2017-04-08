package com.sp.bcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


public class BcpEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) {
        // Write a message.
    	out.writeShort(msg.cmd);
    	out.writeShort(msg.lenght);
    	out.writeInt(msg.seq);
    	out.writeBytes(msg.data);
    }
}
