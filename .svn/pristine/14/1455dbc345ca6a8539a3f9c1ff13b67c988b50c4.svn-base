package com.lactec.crmConfiguration.search.jdbc_odbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class test {

	public static void main(String[] args) {
		get22();
	}

	public static void get22() {
		for (int i = 0; i < 1; i++) {
			long startT = System.currentTimeMillis();
			test t = new test();
			String sql = "select dept_no from departments";
			t.search22(sql);
			long endT = System.currentTimeMillis();
			System.out.println("响应时间:" + (endT - startT) + "ms");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void search22(String sql) {
		String tmp = sql.substring(7, sql.indexOf("from"));
		String cons[] = tmp.trim().split(",");
		String url = "jdbc:odbc:DTDBC";
		Connection con;
		Statement stmt;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = "";
		List<String> info = new ArrayList<String>();
		try {
			con = DriverManager.getConnection(url);
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			int num = 5;// 打印条数控制
			int n = 0;
			while (rs.next() && n < num) {
				result = "";
				for (int i = 0; i < cons.length; i++) {
					if (i != 0) {
						result += "|" + rs.getString(i + 1);
					} else {
						result += rs.getString(i + 1);
					}
				}
				info.add(result);
				System.out.println(result);
				n++;
			}
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}