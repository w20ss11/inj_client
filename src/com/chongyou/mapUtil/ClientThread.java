package com.chongyou.mapUtil;


import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * �ͻ��˵��߳�
 */
public class ClientThread implements Runnable {
    private Socket s;
    // ������UI�̷߳�����Ϣ��Handler����
    private String res;
    // �������UI�̵߳���Ϣ��Handler����
    public Handler revHandler;
    // ���߳��������Socket����Ӧ��������
    BufferedReader br = null;
    OutputStream os = null;

    public ClientThread(String str) {
        this.res = str;
        Log.i("tag", "���췽��"+res);
    }

    public void run() {
        try {
        	Log.i("tag", "��ʼtry����");
            s = new Socket("192.168.0.102", 10046);
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = s.getOutputStream();
                    if (res != null) {
                        try {
                            os.write((res + "\r\n").getBytes("utf-8"));
                            Log.i("tag", "ִ�з���");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
        } catch (SocketTimeoutException e1) {
            System.out.println("�������ӳ�ʱ����");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

