package com.sp.bcp;

import io.netty.channel.ChannelId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class BcpManger {

	private final static Map<Long, BcpSession> sessionMap = new ConcurrentHashMap<>();
	
	public static void addSesion(Long id,BcpSession session) {
		if(!sessionMap.containsKey(id)) { 
			sessionMap.put(id, session); 
		}
		else {//重连过来的
			BcpSession session1 = sessionMap.remove(id);
			session1.setChannel(session.getChannel());
		}
	}
	
	public static void removeSesion(Long id) {
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
