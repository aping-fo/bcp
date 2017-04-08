package com.sp.job;

import com.sp.bcp.BcpManger;

/**
 * 
 * @author aping.foo
 *	50ms检测一次，根据需求调整检测时间，这里的重传时间可能会不精确，检测时间越短，精确度越高
 */
public class AcknowledgeJob implements Runnable{
 
	@Override
	public void run() {
		BcpManger.scheduleRto();
	}
}
