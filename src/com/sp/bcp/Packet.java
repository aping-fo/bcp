package com.sp.bcp;

public class Packet {
	// 协议头
	public final short cmd; // 请求指令
	public final short lenght; // 包体长度
	public final int seq; // 包序列ID
	public final byte[] data; // 包体

	private Packet(short cmd, int seq, byte[] data) {
		this.cmd = cmd;
		this.seq = seq;
		this.lenght = (short) data.length;
		this.data = data;
	}

	public static Packet valueOf(short cmd, int seq, byte[] data) {
		Packet packet = new Packet(cmd, seq, data);
		return packet;
	}
}
