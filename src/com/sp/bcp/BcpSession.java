package com.sp.bcp;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.Channel;

public class BcpSession {
	private Channel channel; //提供多个连接?
	private Map<Integer, Packet> packetMap = new HashMap<>();
	
	public BcpSession(Channel channel) {
		this.channel = channel;
	}

	public void send(Packet packet) {
		channel.writeAndFlush(packet);
	}
	
	public void addPacket(Packet packet) {
		packetMap.put(packet.seq, packet);
	}
}
