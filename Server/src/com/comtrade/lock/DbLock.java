package com.comtrade.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DbLock {

	private static final DbLock instance = new DbLock();
	private Lock lock;
	
	private DbLock() {
		lock = new ReentrantLock();
	}
	
	public static DbLock getInstance() {
		return instance;
	}

	public boolean lock() {
		return lock.tryLock();
	}

	public void unlock() {
		lock.unlock();
	}
	
}
