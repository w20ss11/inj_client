package com.chongyou.mapUtil;

import java.io.File;
import java.io.RandomAccessFile;
import android.annotation.SuppressLint;
import android.util.Log;

public class Write2txt {
	@SuppressLint("SdCardPath")
	private static final String filePath="/sdcard/Test/";
	public Write2txt() {
	}

	public static void deleteTxt(){
		File file=new File(filePath);
		if(file.exists()){
			File[] files=file.listFiles();//��ȡ�ļ��б�
			for(int i=0;i<files.length;i++)
			{
			   if(!files[i].isFile()) continue;//��������ļ����������ų��ļ��еȣ�
			   String fileName=files[i].getName();
			   if(fileName.endsWith(".txt")) files[i].delete();//��׺��Ϊtxt��ɾ��
			}
		}
	}

	// ���ַ���д�뵽�ı��ļ���
	public void writeTxtToFile(String strcontent, String fileName) {
		//�����ļ���֮���������ļ�����Ȼ�����
		makeFilePath(filePath, fileName);

		String strFilePath = filePath+fileName;
		// ÿ��д��ʱ��������д
		String strContent = strcontent + "\r\n";
		try {
			File file = new File(strFilePath);
			if (!file.exists()) {
				Log.d("TestFile", "Create the file:" + strFilePath);
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			RandomAccessFile raf = new RandomAccessFile(file, "rwd");
			raf.seek(file.length());
			raf.write(strContent.getBytes());
			raf.close();
		} catch (Exception e) {
			Log.e("TestFile", "Error on write File:" + e);
		}
	}

	// �����ļ�
	public File makeFilePath(String filePath, String fileName) {
		File file = null;
		makeRootDirectory(filePath);
		try {
			file = new File(filePath + fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	// �����ļ���
	public static void makeRootDirectory(String filePath) {
		File file = null;
		try {
			file = new File(filePath);
			if (!file.exists()) {
				file.mkdir();
			}
		} catch (Exception e) {
			Log.i("error:", e+"");
		}
	}
}
