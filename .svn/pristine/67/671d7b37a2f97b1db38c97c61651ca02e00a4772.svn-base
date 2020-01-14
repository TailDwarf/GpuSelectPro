package com.lactec.crmConfiguration.search.util;

import org.bouncycastle.util.encoders.Hex;

import com.lactec.crmConfiguration.search.socket.Client;
import com.lactec.crmConfiguration.search.socket.ConnectPool;
import com.lactec.crmConfiguration.search.socket.SocketIO;

public class SocketUtil {
	public static void init() {
		// TODO Auto-generated method stub
		new Hex();
		SocketIO socket = null;
		try {
			ConnectPool connPool = ConnectPool.getInstance(Client.gpuHostName, Client.gpuSearchport,
					Client.sockectPoolNumber);
			socket = connPool.getConnect();
			if (socket.IsConnected() == false) {
				if (!socket.connectHSM(Client.gpuHostName, Client.gpuSearchport, Client.timeOut)) {
					connPool.putConnect(socket);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
