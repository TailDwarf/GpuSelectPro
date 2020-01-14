package com.lactec.crmConfiguration.search.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.lactec.crmConfiguration.search.bean.PageBean2;

public class GpuAnalyFileService {

	public PageBean2 getPbReadTxtFileTxt(InputStream is)
			throws IOException, InstantiationException, IllegalAccessException, InvocationTargetException {
		PageBean2 pb = new PageBean2();
		List<String> list = new ArrayList<String>();
		String encoding = "utf-8";
		InputStreamReader read = new InputStreamReader(is, encoding);// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		// 不需要通过柜员号过滤查询信息
		int index = 1;

		while ((lineTxt = bufferedReader.readLine()) != null) {
			if (index == 1) {
				list.add(lineTxt);
				/*
				 * lineTxt = lineTxt.substring(0, lineTxt.indexOf("")); String[] flag =
				 * lineTxt.split("="); rowNum = Long.parseLong(flag[1].trim());
				 */
				// gpu查询结果返回-5 查询异常直接跳出 0没有查询到结果 直接跳出
				if (lineTxt.contains("-5")) {
					System.out.println("gpu查询出错 或者没有查到结果");
					break;
				}
				++index;
			} else {
				list.add(lineTxt);
				++index;
			}
		}
		read.close();
		pb.setRows(list);
//		System.out.println("====gpu====resultSize:" + pb.getRows().size() + "  总记录条数：" + pb.getTotal());
		return pb;

	}
}
