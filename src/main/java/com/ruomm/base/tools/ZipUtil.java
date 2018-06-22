/**
 *	@copyright wanruome-2018
 * 	@author wanruome
 * 	@create 2018年6月22日 上午11:52:06
 */
package com.ruomm.base.tools;

import java.io.File;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class ZipUtil {
	public static void unZip(String sourcePath, String dest, String passwd) throws ZipException {
		ZipFile zfile = new ZipFile(sourcePath);
		if (!zfile.isValidZipFile()) {
			throw new ZipException("压缩文件不合法，可能已经损坏！");
		}
		File file = new File(dest);
		if (file.isDirectory() && !file.exists()) {
			file.mkdirs();
		}
		if (zfile.isEncrypted()) {
			zfile.setPassword(passwd.toCharArray());
		}
		zfile.extractAll(dest);
	}

	public static void unZip(String sourcePath, String dest) throws ZipException {
		ZipFile zfile = new ZipFile(sourcePath);
		if (!zfile.isValidZipFile()) {
			throw new ZipException("压缩文件不合法，可能已经损坏！");
		}
		File file = new File(dest);
		if (file.isDirectory() && !file.exists()) {
			file.mkdirs();
		}
		if (zfile.isEncrypted()) {
			zfile.setPassword("SJnewpay2018");
		}
		zfile.extractAll(dest);
		String pwdPublic = FileUtils.readFile(dest + "pwd_publickey.txt");
		String pwd = FileUtils.readFile(dest + "pwd_privatekey.txt");
		String pwd3DES = FileUtils.readFile(dest + "pwd_3des.txt");
		FileUtils.delAllFile(dest);
		System.out.println(pwdPublic);
		System.out.println(pwd);
		System.out.println(pwd3DES);
	}

	public static void main(String[] args) {
		try {
			unZip("D:\\temp\\encrptyPwdConfig.zip", "D:\\temp\\temp\\");

		}
		catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
