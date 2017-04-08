package com.sp.bcp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class BcpManger {

	private final Map<Long, BcpSession> map = new ConcurrentHashMap<>();
}
