package com.sp.bcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BcpSession {
	private AtomicInteger seq = new AtomicInteger(1); // 服务端维护回应seq，1开始递增
	/** 重传时间间隔，单位毫秒，下一次重传为RTO*2，以2的指数增加重传时间 */
	private static final int RTO = 200;
	/**
	 * 最多缓存多少个离线包，实际可以用配置，在这里直接写死
	 */
	private static final int MaxOfflinePack = 500;
	
	private Channel channel; // 提供多个连接?
	
	//回应的消息缓存，请求消息由客户端维护
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
			packet.seq = seq.getAndIncrement();
			channel.writeAndFlush(packet);
			addPacket(packet);
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
				channel.writeAndFlush(packet);
				packet.count += 1;
				packet.time = System.currentTimeMillis() + RTO * (1 << packet.count);
			}
		}
	}
}
