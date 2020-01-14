package com.lactec.crmConfiguration.search.socket;

import java.util.Comparator;

class ClientPCollator implements Comparator<Object> {
	public int compare(Object o1, Object o2) {
		if (((o1 instanceof SocketIO)) && ((o2 instanceof SocketIO))) {
			SocketIO client1 = (SocketIO) o1;
			SocketIO client2 = (SocketIO) o2;
			int prior1 = client1.IsConnected() ? 2 : 1;
			int prior2 = client2.IsConnected() ? 2 : 1;

			if (prior1 > prior2)
				return 1;
			if (prior1 == prior2) {
				return 0;
			}
			return -1;
		}

		ClassCastException cce = new ClassCastException("比较时应输入UnionSocket类");
		throw cce;
	}
}
