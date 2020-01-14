package com.lactec.crmConfiguration.search.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return 如果为null或者为"" 则返回true
	 */
	public static boolean isNull(String str) {
		if (null2String(str).length() <= 0)
			return true;
		return false;
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isNull(Object o) {
		if (o == null)
			return true;
		if (o instanceof String) {
			if (null2String(o).length() <= 0)
				return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否不为空
	 * 
	 * @param str
	 * @return 如果字符串 不为null 并且不为 "" 返回true 否则返回false
	 */
	public static boolean isNotNull(String str) {
		if (null2String(str).length() <= 0)
			return false;
		return true;
	}

	/**
	 * 判断对象是否不为空
	 * 
	 * @param o
	 * @return 如果为不为null 返回true 否则返回false
	 */
	public static boolean isNotNull(Object o) {
		if (o == null)
			return false;
		return true;
	}

	/**
	 * null2string
	 * 
	 * @param str
	 * @return
	 */
	public static String null2String(String str) {
		if (str == null || str.trim().length() <= 0 || str.trim().toLowerCase().equals("null")
				|| str.trim().toLowerCase().equals("nullnull") || str.trim().toLowerCase().equals("nullnullnull")
				|| str.trim().toLowerCase().equals("nullnullnullnull"))
			return "";
		return str;
	}

	/**
	 * null2String
	 * 
	 * @param o
	 * @return
	 */
	public static String null2String(Object o) {
		if (o == null)
			return "";
		return o.toString();
	}

	/**
	 * 返回整数 如果异常 则返回0
	 * 
	 * @param str
	 * @return
	 */
	public static int string2int(String str) {
		if (isNotNull(str)) {
			try {
				return Integer.valueOf(str).intValue();
			} catch (Exception ex) {
				return 0;
			}
		}
		return 0;
	}

	/**
	 * 返回整数 如果异常 则返回0
	 * 
	 * @param str
	 * @return
	 */
	public static int string2int(Object o) {
		String str = null2String(o);
		return string2int(str);
	}

	/**
	 * 对字符串进行trim
	 * 
	 * @param str
	 * @return
	 */
	public static String stringTrim(String str) {
		return null2String(str).trim();
	}

	/**
	 * 去掉字符串的\r \n
	 * 
	 * @param str
	 * @return
	 */
	public static String stringReturn(String str) {
		str = null2String(str);
		str = str.replaceAll("\r", " ");
		str = str.replaceAll("\n", " ");
		return str;
	}

	/**
	 * 校验字符串数组是否存在指定的字符 进行了过滤开始和结束的空格
	 * 
	 * @param strs
	 *            字符串数组
	 * @param str
	 *            指定的字符串
	 * @return 如果参数有一个为空返回-1 如果存在 返回下标 否则返回-1
	 * @throws Exception
	 */
	public static int isExistsStr(String[] strs, String str) throws Exception {
		if (Utils.isNull(strs) || Utils.isNull(str))
			return -1;
		for (int i = 0; i < strs.length; i++) {
			if (Utils.isNotNull(strs[i]) && strs[i].trim().equals(str.trim()))
				return i;
		}
		return -1;
	}

	/**
	 * 文件转换成字节流
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static byte[] file2byte(File f) throws Exception {
		if (f == null || !f.exists())
			return null;
		// 2.输出byte[]到前台
		FileInputStream fis = new FileInputStream(f);
		BufferedInputStream bis = new BufferedInputStream(fis);
		ByteArrayOutputStream baos = new ByteArrayOutputStream(Integer.valueOf(f.length() + "").intValue());
		int MaxLen = 1024 * 5;
		byte[] buf = new byte[MaxLen];
		int num = -1;
		try {
			while ((num = bis.read(buf, 0, MaxLen)) != -1) {
				baos.write(buf, 0, num);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			bis.close();
			fis.close();
		}
		return baos.toByteArray();
	}

	/**
	 * 用来把字符串数数组转换成字符串，默认取数组的第一个内容 主要是用来处理页面传递的参数
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> stringArry2String(Map<String, String[]> map) throws Exception {
		if (map == null)
			return null;
		Map<String, String> tmp = new HashMap<String, String>();
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			if (isNull(entry.getKey()))
				continue;
			if (isNotNull(entry.getValue()) && entry.getValue().length > 0) {
				tmp.put(entry.getKey(), entry.getValue()[0]);
			} else {
				tmp.put(entry.getKey(), null);
			}
		}
		return tmp;
	}

	/**
	 * 获取id
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getUUID() throws Exception {
		UUID id = UUID.randomUUID();
		if (Utils.isNotNull(id))
			return id.toString().replaceAll("-", "");
		return null;
	}

	/**
	 * 判断是否是汉字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isContainChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

	public static List<Map<String, Object>> convertMapList(List<Map<String, Object>> lists) {
		List<Map<String, Object>> _list = new ArrayList<Map<String, Object>>();

		for (Map<String, Object> map : lists) {
			Map<String, Object> _map = new HashMap<String, Object>();
			Iterator iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = String.valueOf(entry.getKey());
				String val = String.valueOf(entry.getValue());
				_map.put(key.toLowerCase(), val);
			}
			_list.add(_map);
		}
		return _list;
	}

	/**
	 * 获取两个List的不同元素
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static List<String> getDiffrent(List<String> list1, List<String> list2) {
		long st = System.currentTimeMillis();
		Map<String, Integer> map = new HashMap<String, Integer>(list1.size() + list2.size());
		List<String> diff = new ArrayList<String>();
		List<String> maxList = list1;
		List<String> minList = list2;
		if (list2.size() > list1.size()) {
			maxList = list2;
			minList = list1;
		}
		for (String string : maxList) {
			map.put(string, 1);
		}
		for (String string : minList) {
			Integer cc = map.get(string);
			if (cc != null) {
				map.put(string, ++cc);
				continue;
			}
			map.put(string, 1);
		}
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 1) {
				diff.add(entry.getKey());
			}
		}
		System.out.println("getDiffrent4 total times " + (System.currentTimeMillis() - st));
		return diff;

	}

	/**
	 * @author TengBei 使用异或运算 --简单的加密解密
	 */
	private static final String key0 = "ABCDEFGHIJKLMNOPQRSTUVWXYZacbdefghljklmnopqrstuvwxyz1234567890";
	private static final Charset charset = Charset.forName("UTF-8");
	private static byte[] keyBytes = key0.getBytes(charset);

	public static String encode(String enc) {
		byte[] b = enc.getBytes(charset);
		for (int i = 0, size = b.length; i < size; i++) {
			for (byte keyBytes0 : keyBytes) {
				b[i] = (byte) (b[i] ^ keyBytes0);
			}
		}
		return new String(b);
	}

	public static String decode(String dec) {
		byte[] e = dec.getBytes(charset);
		byte[] dee = e;
		for (int i = 0, size = e.length; i < size; i++) {
			for (byte keyBytes0 : keyBytes) {
				e[i] = (byte) (dee[i] ^ keyBytes0);
			}
		}
		return new String(e);
	}

	/**
	 * 测试主方法
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		String s = "~424=5<";
		String dec = decode(s);// 解密
		System.out.println(dec);
	}

}
