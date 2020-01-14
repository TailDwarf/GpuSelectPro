package com.lactec.crmConfiguration.search.socket;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConnectPool {
	private String HsmHost = "";
	private int HsmPort = 0;
	private static ConnectPool instance = null;
	private PriorityBlockingQueue pbQueue = null;
	private ClientPCollator clientCollator = null;
	private static int usingCount = 0;
	private static int count = 0;
	private static Object lock = new Object();
	private static Object countLock = new Object();

	public static ConnectPool getInstance() {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new ConnectPool();
				}
			}
		}
		return instance;
	}

	public static synchronized ConnectPool getInstance(String HsmHost, int HsmPort, int poolNumber) {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new ConnectPool(HsmHost, HsmPort, poolNumber);
				}
			}
		}
		return instance;
	}

	private ConnectPool() {
		initialize(25);
	}

	private ConnectPool(String HsmHost, int HsmPort, int poolNumber) {
		this.HsmHost = HsmHost;
		this.HsmPort = HsmPort;
		initialize(poolNumber);
	}

	public void initialize(int poolNumber) {
		try {
			count = poolNumber;
			this.clientCollator = new ClientPCollator();
			this.pbQueue = new PriorityBlockingQueue(count + 1, this.clientCollator);
			for (int i = 0; i < count; i++)
				this.pbQueue.put(new SocketIO(i));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeConnect(SocketIO socket) {
		socket.allClose();
		this.pbQueue.remove(socket);
	}

	public synchronized SocketIO getConnect() {
		SocketIO socket = null;
		if (this.pbQueue.size() + usingCount < count) {
			enCount();
			socket = new SocketIO();
			return socket;
		}
		try {
			socket = (SocketIO) this.pbQueue.poll(1L, TimeUnit.SECONDS);
			if (socket == null)
				socket = new SocketIO();
			enCount();
		} catch (Exception e) {
			e.printStackTrace();
			return new SocketIO();
		}
		return socket;
	}

	public void putConnect(SocketIO socket) {
		deCount();
		if (usingCount + this.pbQueue.size() < count) {
			this.pbQueue.put(socket);
		} else {
			socket.allClose();
			socket = null;
		}
	}

	private void deCount() {
		synchronized (countLock) {
			usingCount -= 1;
		}
	}

	private void enCount() {
		synchronized (countLock) {
			usingCount += 1;
		}
	}

	public static void setCount(int newCount) {
		count = newCount;
	}

	public static int getUsingCount() {
		return usingCount;
	}

	public static void setUsingCount(int usingCount) {
		ConnectPool.usingCount = usingCount;
	}

	public static int getCount() {
		return count;
	}

	public PriorityBlockingQueue getPbQueue() {
		return pbQueue;
	}

	public void setPbQueue(PriorityBlockingQueue pbQueue) {
		this.pbQueue = pbQueue;
	}
}