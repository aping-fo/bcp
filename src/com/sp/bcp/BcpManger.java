package com.sp.bcp;

import io.netty.channel.ChannelId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class BcpManger {

	private final static Map<ChannelId, BcpSession> sessionMap = new ConcurrentHashMap<>();
	
	public static void addSesion(BcpSession session) {
		sessionMap.put(session.id(), session);
	}
	
	public static void removeSesion(ChannelId id) {
		BcpSession session = sessionMap.remove(id);
		if(session != null) {
			session.close();
		}
	}
	
	public static void scheduleRto() {
		for(BcpSession session : sessionMap.values()) {
			session.rto();
		}
	}
	 
	public static void acknowledge(ChannelId sessionId,int seq) {
		BcpSession session = sessionMap.get(sessionId);
		if(session != null) {
			session.removePacket(seq);
		}
	}
	
	public static boolean addPacket(ChannelId sessionId,Packet packet) {
		boolean ret = true;
		BcpSession session = sessionMap.get(sessionId);
		if(session != null) {
			session.addPacket(packet);
		}
		else {
			//TODO 记录日志，为啥没有?没有登录?
			//关闭连接?
			ret = false;
		}
		return ret;
	}
}
