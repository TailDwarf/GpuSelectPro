package com.lactec.crmConfiguration.search.test;

import java.util.ArrayList;
import java.util.List;

import com.lactec.crmConfiguration.search.service.GpuSearchAPI;
import com.lactec.crmConfiguration.search.thread.QueryThread;
import com.lactec.crmConfiguration.search.util.FileUtil;
import com.lactec.crmConfiguration.search.util.SocketUtil;

public class ThreadTest {
	public static void main(String[] args) throws Exception {
		// SQL脚本数量(文件数量)
		int fileNum = 1;
		List<List<String>> fileList = new ArrayList<>();
		for (int i = 3; i <=fileNum+2; i++) {
			List<String> sqlList = FileUtil.getSqlList("test_" + i + ".txt");
			fileList.add(sqlList);
		}
		SocketUtil.init();
		for (int i = 0; i < fileNum; i++) {
			GpuSearchAPI api = new GpuSearchAPI();
			QueryThread query = new QueryThread(api, fileList.get(i));
			Thread thread = new Thread(query);
			thread.start();   
		}
	}
}
