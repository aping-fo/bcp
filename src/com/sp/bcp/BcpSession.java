package com.sp.bcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BcpSession {
	/** 重传时间间隔，单位毫秒，下一次重传为RTO*2，以2的指数增加重传时间 */
	private static final int RTO = 200;
	/**
	 * 最多缓存多少个离线包，实际可以用配置，在这里直接写死
	 */
	private static final int MaxOfflinePack = 500;
	
	private Channel channel; // 提供多个连接?
	private Map<Integer, Packet> packetMap = new ConcurrentHashMap<>();

	public BcpSession(Channel channel) {
		this.channel = channel;
	}

	public ChannelId id() {
		return channel.id();
	}
	
	public boolean send(Packet packet) {
		boolean ret = true;
		try {
			channel.writeAndFlush(packet);
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}

	public void addPacket(Packet packet) {
		if(packetMap.size() < MaxOfflinePack) {
			packetMap.put(packet.seq, packet);
		}
	}

	public void removePacket(int id) {
		packetMap.remove(id);
	}

	public void close() {
		if (channel != null) {
			channel.close();
		}
		packetMap.clear();
		channel = null;
		packetMap = null;
	}

	public boolean isOpen() {
		return channel.isOpen();
	}

	public void rto() { //检测可能不是那么精准
		if (!isOpen())
			return;
		for (Packet packet : packetMap.values()) {
			if (System.currentTimeMillis() > packet.time) {
				send(packet);
				packet.count += 1;
				packet.time = System.currentTimeMillis() + RTO * (1 << packet.count);
			}
		}
	}
}
