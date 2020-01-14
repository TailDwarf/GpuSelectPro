package com.lactec.crmConfiguration.search.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import org.bouncycastle.util.encoders.Hex;

public class SocketIO {
	private Socket h;
	private BufferedReader is;
	private PrintWriter os;
	public boolean ok = false;
	public String conerrmsg = null;
	private int fashTime = 0;

	public SocketIO(int iserial) {
		this.h = null;
		this.is = null;
		this.os = null;
		this.ok = false;
		this.conerrmsg = null;
	}

	public SocketIO() {
		this.h = null;
		this.is = null;
		this.os = null;
		this.ok = false;
		this.conerrmsg = null;
	}

	public boolean IsConnected() {
		return this.ok;
	}

	public boolean connectHSM(String ip, int port) throws Exception {
		try {
			this.h = new Socket();
			this.h.connect(new InetSocketAddress(ip, port), 60 * 000);
			// this.h.setSoLinger(true, 0);
			this.h.setSoTimeout(60 * 000);
			is = new BufferedReader(new InputStreamReader(h.getInputStream(), "ISO-8859-1"));
			os = new PrintWriter(new OutputStreamWriter(h.getOutputStream(), "ISO-8859-1"));
			this.ok = true;
		} catch (SocketException e) {
			this.ok = false;
			this.conerrmsg = ("Possible Reason:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (!this.ok) {
				allClose();
				return false;
			}
		}
		return true;
	}

	public boolean connectHSM(String ip, int port, int timeOut) throws Exception {
		try {
			this.h = new Socket();
			this.h.connect(new InetSocketAddress(ip, port), timeOut);
			// this.h.setSoLinger(true, 0);
			this.h.setSoTimeout(timeOut);

			is = new BufferedReader(new InputStreamReader(h.getInputStream(), "ISO-8859-1"));
			os = new PrintWriter(new OutputStreamWriter(h.getOutputStream(), "ISO-8859-1"));
			this.ok = true;
		} catch (SocketException e) {
			this.ok = false;
			this.conerrmsg = ("Possible Reason：" + e.getMessage());
			e.printStackTrace();
		}
		return this.ok;
	}

	public String ExchangeData(String in, int timeOut, String taskId, long startTime) {
		String outstr = null;
		try {
			h.setSoTimeout(timeOut);
			SendToHSM(in);
			outstr = RecvFromSvr(taskId, startTime);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return outstr;
	}

	private void SendToHSM(String str) {
		this.os.write(str);
		this.os.flush();
	}

	public void allClose() {
		try {
			this.is.close();
			this.is = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			this.os.close();
			this.os = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			this.h.close();
			this.h = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.ok = false;
		}
	}

	public String RecvFromSvr(String taskId, long startTime) throws Exception {
		String out = "";
		String tmpstr = "";
		int i = 0;
		String response = null;
		try {
			char[] out1 = new char[2048];
			i = is.read(out1);
			for (int ii = 0; ii < i; ii++) {
				tmpstr = Integer.toHexString(out1[ii]);
				out += tmpstr;
			}
			response = new String(Hex.decode(out.getBytes()), "ISO-8859-1");
			if ("".equals(response) || null == response) {
				System.out.println("socket 接收消息异常:接收消息为空");
				return null;
			} else if (response.contains(taskId)) {
				return response;
			} else if (response.contains("success")) {
				return response;
			} else {
				if (fashTime < 5) {
					System.out.println("socket 接收消息格式或者消息taskId匹配异常 taskId:" + taskId + "    响应消息:" + response
							+ "    time" + (System.currentTimeMillis() - startTime));
					RecvFromSvr(taskId, startTime);
					fashTime++;
				} else {
					fashTime = 0;
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return response;
	}

	public String RecvFromSvrUntilLen() throws Exception {
		String out = "";
		String str = "";

		try {
			while ((str = is.readLine()) != null) {
				out = out + str;
				if (out.startsWith("ftp://")) {
					System.out.println("socket 收到消息：" + str);
					return out;
				}
				if (null == str || "".equals(str)) {
					System.out.println("socket 收到消息：" + str);
					return out;
				}
				out = out + "\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return out;
	}
}
