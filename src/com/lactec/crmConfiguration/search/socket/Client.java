package com.lactec.crmConfiguration.search.socket;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.Properties;

public class Client {
	public static String gpuHostName = "10.0.118.38";
	public static int gpuSearchport = 5510;
	public static int sockectPoolNumber = 30;
	public static int timeOut = 30 * 1000;
	public static String ftpHost = "10.0.118.38";
	public static int ftpPort = 21;
	static {
		Properties p = new Properties();
		try {
			InputStream is = Client.class.getResourceAsStream("/gpu.properties");
			p.load(is);
			gpuHostName = p.getProperty("hostName");
			gpuSearchport = Integer.parseInt(p.getProperty("port"));
			sockectPoolNumber = Integer.parseInt(p.getProperty("poolNumber"));
			timeOut = Integer.parseInt(p.getProperty("timeOut"));
			ftpHost = p.getProperty("ftpHost");
			ftpPort = Integer.parseInt(p.getProperty("ftpPort"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("error:"+e);
		}
	}

	private String error;
	private long disposeTime;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public long getDisposeTime() {
		return disposeTime;
	}

	public void setDisposeTime(long disposeTime) {
		this.disposeTime = disposeTime;
	}

	public String getSearchInfo(String sql, String taskId, long startTime) {
		String response = null;
		byte[] sqlArr = formatSql(taskId, sql);
		byte[] data = formatSendInfo(sqlArr);
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		// response=sendAndRecevie(hostName,Integer.parseInt(port),data);
		bout.write(data, 0, sqlArr.length + 9);
		long b1 = System.currentTimeMillis();
		SocketIO socket = null;
		try {
			// 长连接, 从连接池中获取连接
			ConnectPool connPool = ConnectPool.getInstance(gpuHostName, gpuSearchport, sockectPoolNumber);
			socket = connPool.getConnect();
			if (socket.IsConnected() == false) {
				if (!socket.connectHSM(gpuHostName, gpuSearchport, timeOut)) {
					connPool.putConnect(socket);
				}
			}
			long time = System.currentTimeMillis() - b1;
			System.out.print("Socket连接耗时：" + time + "ms...");
			if (time > 20) {
				System.out.println("网络状况差，请重新测试...");
			} else {
				System.out.println("");
			}
			response = socket.ExchangeData(new String(bout.toByteArray(), "ISO-8859-1"), timeOut, taskId, startTime);
			connPool.putConnect(socket);
			if (null == response || "".equals(response)) {
				System.out.println("socket 连接断开 重新连接...");
				socket.allClose();
				if (!socket.connectHSM(gpuHostName, gpuSearchport, timeOut)) {
					connPool.putConnect(socket);
				}
				response = socket.ExchangeData(new String(bout.toByteArray(), "ISO-8859-1"), timeOut, taskId,
						startTime);
				return response;
			}
			return response;
		} catch (SocketException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public byte[] formatSql(String taskId, String sql) {
		System.out.println("查询请求唯一标识：" + taskId + "  SQL:" + sql);
		sql = taskId + "#" + sql;
		byte[] formatSql;
		try {
			formatSql = sql.getBytes("utf8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return formatSql;
	}

	public byte[] formatSendInfo(byte[] sqlData) {
		byte[] data = new byte[10240];
		// byte[]head=new byte[]{0xAB,(byte) 0xEF};
		data[0] = (byte) 0xAB;
		data[1] = (byte) 0xEF;
		data[2] = 1;
		data[3] = 1;
		data[4] = 1;

		try {
			if (sqlData.length < 256) {
				data[5] = (byte) sqlData.length;
				data[6] = 0;
			}
			if (sqlData.length >= 256) {
				data[5] = (byte) (sqlData.length % 256);
				data[6] = (byte) (sqlData.length / 256);
			}

			data[7] = 0;
			data[8] = 0;
			System.arraycopy(sqlData, 0, data, 9, sqlData.length);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
		}
		return data;
	}

	public static String getTaskId() {
		long time = System.currentTimeMillis();
		int data = (int) (Math.random() * 10000);
		int data2 = (int) (Math.random() * 10000);
		int data3 = (int) (Math.random() * 10000);
		String taskId = "id" + data + time + data2 + data3;
		return taskId;
	}

}
