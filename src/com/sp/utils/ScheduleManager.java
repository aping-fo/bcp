package com.sp.utils;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduleManager {

	private static ScheduledThreadPoolExecutor scheduleService = new ScheduledThreadPoolExecutor(4);
	
	public static void schedule(Runnable task,int initialDelay,int period,TimeUnit unit) {
		scheduleService.scheduleAtFixedRate(task, initialDelay, period, unit);
	}
}
