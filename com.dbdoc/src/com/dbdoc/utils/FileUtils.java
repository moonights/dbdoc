package com.dbdoc.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/***
 * 
 * 文件工具类
 * 
 * @author moonights
 *
 */
public class FileUtils {

	/**
	 * 获得path路径下的文件或文件夹对象, 如果不存在自动创建
	 * @param path
	 * @return
	 */
	public static File getFile(String path) {
		StringTokenizer directoryTokenzier = new StringTokenizer(path,File.separator);
		File file = null;
		StringBuffer pathBuffer = null;
		while (directoryTokenzier.hasMoreTokens()) {
			String directory = directoryTokenzier.nextToken();
			if (directory != null && !"".equals(directory)) {
				if (pathBuffer == null) {
					pathBuffer = new StringBuffer();
					pathBuffer.append(directory);
				} else {
					pathBuffer.append(File.separator).append(directory);
				}
				file = new File(pathBuffer.toString());
				if (!file.exists() && directoryTokenzier.hasMoreTokens()) {
					file.mkdir();
				} else if (file.isFile()) {
					break;
				}
			}
		}

		return file;
	}

	/**
	 * 文件复制
	 * @param source
	 * @param copy
	 */
	public static void fileCopy(File source, File copy) {
		BufferedReader reader = null;
		PrintWriter writer = null;

		try {
			reader = new BufferedReader(new FileReader(source));
			writer = new PrintWriter(new FileWriter(copy));

			String line = null;
			while ((line = reader.readLine()) != null) {
				writer.println(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (writer != null) {
				writer.close();
			}
		}

	}

	/**
	 * 文件夹复制
	 * @param oldPath
	 * @param newPath
	 */
	public static void copyFileFolder(String oldPath, String newPath) {
		try {
			(new File(newPath)).mkdirs(); //如果文件夹不存在  则建立新文件夹  
			File newfile = new File(oldPath);
			String[] file = newfile.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int lenth;
					while ((lenth = input.read(b)) != -1) {
						output.write(b, 0, lenth);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {//如果是子文件夹  
					copyFileFolder(oldPath + "/" + file[i], newPath + "/"
							+ file[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}