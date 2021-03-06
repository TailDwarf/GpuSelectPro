package com.lactec.crmConfiguration.search.thread;

import java.util.List;

import com.lactec.crmConfiguration.search.service.GpuSearchAPI;

public class QueryThread implements Runnable {
	private GpuSearchAPI api;
	private List<String> sqlList;

	public QueryThread() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QueryThread(GpuSearchAPI api, List<String> sqlList ) {
		super();
		this.api = api;
		this.sqlList = sqlList;
	}
//
//	@Override
	//定时10分钟结束
//	public void run() {
//		// TODO Auto-generated method stub
//		boolean flag = true;
//		Date data1 = new Date();
//		while(flag){
//			if (api != null && sqlList.size() != 0) {
//				for (String sql : sqlList) {
//					api.search(sql, 0, 0);
//					System.out.println("--------------------------- Thread " + num);
//				}
//			}
//			Date data2 = new Date();
//			long time_pl = data2.getTime()-data1.getTime();
//			System.out.println("时间差===" + time_pl);
//			if(data2.getTime()-data1.getTime() >= 6000000){
//				flag = false;
//			}
//		}
//	}
	
	public void run() {
		// TODO Auto-generated method stub
			if (api != null && sqlList.size() != 0) {
				for (String sql : sqlList) {
					api.search(sql, 0, 0);
				}
			}
		}
		
	}
