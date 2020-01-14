package com.lactec.crmConfiguration.search.service;

import java.io.InputStream;

import com.lactec.crmConfiguration.search.bean.PageBean2;
import com.lactec.crmConfiguration.search.socket.Client;

public class GpuSearchAPI {

	private String error;// 错误信息

	public PageBean2 search(String sql, int startNum, int pageSize) {
		PageBean2 pb2 = null;
		long startTime = System.currentTimeMillis();
		try {
			if (pageSize > 0) {
				sql = sql + " #" + startNum + "#" + pageSize;
			}
			String taskId = Client.getTaskId();
			Client service = new Client();
			String ftpFilePath = service.getSearchInfo(sql, taskId, startTime);
			long searchTime = System.currentTimeMillis();
			long disposeTime = searchTime - startTime;
			System.out.println("收到响应消息----:" + ftpFilePath + "---time--" + disposeTime + "ms");
			if (ftpFilePath.contains("ErrorCode_")) {
				this.error = "GPU查询出错，错误代码：" + ftpFilePath + "---time---" + disposeTime;
				System.out.println(error);
				return null;
			} else if (ftpFilePath.contains("success")) {
				System.out.println("语句执行完成。");
				return null;
			} else {
				if (Utils.isNull(ftpFilePath)) {
					throw new Exception("gpu返回消息内容为空");
				} // ftp://172.168.1.1/id.bin
				if (!ftpFilePath.toLowerCase().trim().startsWith("ftp://")) {
					throw new Exception("gpu返回消息格式不是ftp://开头、【" + ftpFilePath + "】");
				}
				int spaceIndex = ftpFilePath.lastIndexOf(' ');
				if (spaceIndex > 0) {
					ftpFilePath = ftpFilePath.substring(0, spaceIndex).trim();
				}
				ftpFilePath = ftpFilePath.substring(6);
				int index = ftpFilePath.indexOf("/");
				if (index < 0) {
					throw new Exception("gpu返回消息格式不正确，没有根目录/、【" + ftpFilePath + "】");
				}
				String ftpIp = ftpFilePath.substring(0, index);
				if (!"0".equals(Client.ftpHost)) {
					ftpIp = Client.ftpHost;
				}
				String filePath = ftpFilePath.substring(index + 1);
				index = filePath.lastIndexOf("/");
				String fileName = null;
				if (index <= 0) {
					fileName = filePath;
					filePath = "/";
				} else {
					fileName = filePath.substring(index + 1);
					filePath = filePath.substring(0, index);
				}
				long start1 = System.currentTimeMillis();
				ftpUtil util = new ftpUtil(Client.ftpPort);
				InputStream is = util.download(ftpIp, filePath + fileName);
				// InputStream is=new FileInputStream(new File(ftpFilePath));
				long end1 = System.currentTimeMillis();

				System.out.println("ftp download time:" + (end1 - start1) + " ms   查询结果集文件名称:" + fileName);

				// ==4.解析数据
				GpuAnalyFileService analyFileService = new GpuAnalyFileService();
				pb2 = analyFileService.getPbReadTxtFileTxt(is);
				pb2.setTaskId(taskId);
				pb2.setDisposeTime("查询响应时间:" + disposeTime + "毫秒");
				long endTime = System.currentTimeMillis();
				System.out.println("time；" + (endTime - startTime));
				return pb2;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			this.error = ex.getMessage();
			return null;
		}
	}

}
