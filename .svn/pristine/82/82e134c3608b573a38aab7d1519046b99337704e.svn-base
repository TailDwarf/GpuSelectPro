package com.lactec.crmConfiguration.search.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	public static List<String> getSqlList(String fileName) throws Exception {
		List<String> sqlList = new ArrayList<>();
		URL url = FileUtil.class.getClassLoader().getResource(fileName);
		File file = new File(url.toURI());
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(file));
		String sql = null;
		while ((sql = br.readLine()) != null) {
			sqlList.add(sql);
		}
		return sqlList;
	}

}
