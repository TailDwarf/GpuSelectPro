package com.lactec.crmConfiguration.search.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;

/**
 * ftp实现文件的上传下载
 */
public class ftpUtil {
	private int port = 21;
	private String username = "anonymous";
	private String password = "11@11.com";

	public ftpUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ftpUtil(int port) {
		super();
		this.port = port;
	}

	/**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @param url      FTP服务器hostname
	 * @param port     FTP服务器端口
	 * @param username FTP登录账号
	 * @param password FTP登录密码
	 * @param ftpPath  FTP服务器保存目录
	 * @param filename 上传到FTP服务器上的文件名
	 */
	public static boolean upload(String url, int port, String username, String password, String ftpPath,
			String filename) {
		FTPClient ftpClient = new FTPClient();
		InputStream fis = null;
		boolean flag = false;
		try {
			ftpClient.connect(url, port);
			ftpClient.login(username, password);

			ftpClient.enterLocalPassiveMode();

			File srcFile = new File(filename);

			fis = new FileInputStream(srcFile);

			// 设置文件类型（二进制）
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

			// String name = srcFile.getName();
			flag = ftpClient.storeFile(ftpPath, fis);
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
			throw new RuntimeException("FTP客户端出错！", e);
		} finally {
			IOUtils.closeQuietly(fis);
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("关闭FTP连接发生异常！", e);
			}
		}
		return flag;
	}

	public boolean uploadFile(String host, int port, String username, String password, String ftpPath, String filename,
			InputStream fis) {
		FTPClient ftpClient = new FTPClient();
		boolean flag = false;
		try {
			ftpClient.connect(host, port);
			ftpClient.login(username, password);
			ftpClient.enterLocalPassiveMode();
			// 设置文件类型（二进制）
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			// String name = srcFile.getName();
			filename = ftpPath + filename;
			System.out.println(filename);
			flag = ftpClient.storeFile(filename, fis);
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
			throw new RuntimeException("FTP客户端出错！", e);
		} finally {
			IOUtils.closeQuietly(fis);
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("关闭FTP连接发生异常！", e);
			}
		}
		return flag;
	}

	/**
	 * Description: 从FTP服务器下载文件
	 * 
	 * @param hostname  FTP服务器hostname
	 * @param port      FTP服务器端口
	 * @param username  FTP登录账号
	 * @param password  FTP登录密码
	 * @param ftpFile   FTP服务器上的要下载的文件名
	 * @param localPath 下载后保存到本地的路径
	 */
	public InputStream download(String hostname, String ftpFile) {
		FTPClient ftpClient = new FTPClient();
		InputStream is = null;
		try {
			ftpClient.connect(hostname, port);
			ftpClient.login(username, password);
			ftpClient.enterLocalPassiveMode();
			// 设置文件类型（二进制）
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			is = ftpClient.retrieveFileStream(ftpFile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("FTP客户端出错！", e);
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("关闭FTP连接发生异常！", e);
			}
		}
		return is;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String testFTP(String ftpIP) throws Exception {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(ftpIP, port);
			ftpClient.login(username, password);
			return "FTP 连接成功！";
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				ftpClient.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
}
