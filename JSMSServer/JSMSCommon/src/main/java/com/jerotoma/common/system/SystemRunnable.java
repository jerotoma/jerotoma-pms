package com.jerotoma.common.system;

import com.jerotoma.common.system.SystemThreadLocal.ThreadLocalContext;

public class SystemRunnable implements Runnable {
	
	protected ThreadLocalContext localContext;
	
	protected volatile boolean stopped = false;

	public SystemRunnable() {
		localContext = SystemThreadLocal.getLocalContext();
	}
	
	@Override
	public void run() {
		SystemThreadLocal.add(localContext);
		this.process();
	}
	
	public void process() {
	}
	
	public void stopRunnable() {
		stopped = true;
	}
}