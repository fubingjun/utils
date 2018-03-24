/**
 * Copyright © 2004-2017 LianlianPay.All Rights Reserved.
 */
package com.exchange.pab.dmz.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/** 
 * 描述说明
 * 
 * @version V1.0
 * @author fubingjun
 * @Date 2017年7月4日 下午9:10:18
 * @since JDK 1.6
 */
public class FileOperator {

	private static final Logger logger = LoggerFactory.getLogger(FileOperator.class); // 日志记录信息

	/**
	 * 创建文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean createFile(File fileName) throws Exception {
		boolean flag = false;
		try {
			if (!fileName.exists()) {
				fileName.createNewFile();
				flag = true;
			}
		} catch (Exception e) {
			logger.error("文件创建失败");
			throw e;
		}
		return flag;
	}

	/**
	 * 读TXT文件内容
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readTxtFile(File fileName) throws Exception {
		String result = null;
		InputStreamReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new InputStreamReader(new FileInputStream(fileName), "GBK");
			bufferedReader = new BufferedReader(fileReader);
			try {
				String read = null;
				while ((read = bufferedReader.readLine()) != null) {
					result = result + read + "\n";
				}
			} catch (Exception e) {
				logger.error("文件读取失败");
				throw e;
			}
		} catch (Exception e) {
			logger.error("文件读取失败");
			throw e;
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (fileReader != null) {
				fileReader.close();
			}
		}
		// System.out.println("读取出来的文件内容是：" + "\r\n" + result);
		return result;
	}

	public static boolean writeTxtFile(String content, File fileName) throws Exception {
		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(fileName);
			o.write(content.getBytes("GBK"));
			o.close();
			// mm=new RandomAccessFile(fileName,"rw");
			// mm.writeBytes(content);
			flag = true;
		} catch (Exception e) {
			logger.error("文件写入失败");
			throw e;
		} finally {
			if (mm != null) {
				mm.close();
			}
		}
		return flag;
	}

	public static void contentToTxt(String filePath, String content) throws Exception {
		String str = new String(); // 原有txt内容
		String s1 = new String();// 内容更新
		try {
			File f = new File(filePath);
			if (f.exists()) {
				// System.out.print("文件存在");
			} else {
				// System.out.print("文件不存在");
				f.createNewFile();// 不存在则创建
			}
			InputStreamReader reder = new InputStreamReader(new FileInputStream(f), "GBK");
			BufferedReader input = new BufferedReader(reder);

			while ((str = input.readLine()) != null) {
				s1 += str + "\n";
			}
			// System.out.println(s1);
			input.close();
			s1 += content;

			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f), "GBK");
			BufferedWriter output = new BufferedWriter(write);
			output.write(s1);
			output.close();
		} catch (Exception e) {
			logger.error("文件写入失败");
			throw e;
		}
	}

}
