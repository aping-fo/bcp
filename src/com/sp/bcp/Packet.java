package com.sp.bcp;

public class Packet {
	// 协议头
	public final short cmd; // 请求指令
	public final short lenght; // 包体长度
	public int seq; // 包序列ID
	public final byte[] data; // 包体

	public long time; //收到数据包时间,不参与网络传输
	public int count; //重传次数，一般重传3次
	
	private Packet(short cmd, int seq, byte[] data) {
		this.cmd = cmd;
		this.seq = seq;
		this.lenght = (short) data.length;
		this.data = data;
		time = System.currentTimeMillis();
	}

	public static Packet valueOf(short cmd, int seq, byte[] data) {
		Packet packet = new Packet(cmd, seq, data);
		return packet;
	}
}
