package com.sp.job;

import com.sp.bcp.BcpManger;

/**
 * 
 * @author aping.foo
 *	每
 */
public class AcknowledgeJob implements Runnable{
 
	@Override
	public void run() {
		BcpManger.scheduleRto();
	}
}
