package com.chongyou.main;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chongyou.mapUtil.ClientThread;
import com.chongyou.mapUtil.GetDate;
import com.chongyou.mapUtil.Write2txt;
import com.chongyou.wifiAdmin.WifiAdmin;
import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class MainActivity extends Activity implements OnClickListener {
    private ListView lv;
    private WifiAdmin mWifiAdmin;  
    private List<ScanResult> list;  
    private ScanResult mScanResult;  
    private List<Map<String, String>> data=new ArrayList<Map<String,String>>();
    private Gson gson;
    private String gsonString;
    private Write2txt write2txt=new Write2txt();
    private ClientThread clientThread;
    private final String fileName=GetDate.getTime();
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){  
        public void handleMessage(Message msg) {  
            super.handleMessage(msg);  
            switch(msg.what){  
            case 1:  
                getList();      //ʹ��getSystemService(String)��ȡ��WifiManagerȻ����wifi���룬
                clientThread = new ClientThread(gsonString);
        		new Thread(clientThread).start();
                	break;  
                default:  
                    break;  
            }  
              
        }  
    };
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mWifiAdmin = new WifiAdmin(MainActivity.this);  
	    lv=(ListView) findViewById(R.id.lv);
	    findViewById(R.id.send).setOnClickListener(this); 
	    
//	    Log.i("tag", "ɾ��*************");
//	    Write2txt.deleteTxt();
	    
	    
	    gson=new Gson();
	    getList();
	    new TvThread().start(); 
	}
	public void getList(){
        if(data!=null){
    		data=new ArrayList<Map<String,String>>();
    	}
    	mWifiAdmin.startScan();
    	list=mWifiAdmin.getWifiList();
    	//Log.i("tag", "XIXI222"+list.size());
    	
    	//write2txt.writeTxtToFile(list.toString()+GetDate.getTime(), GetDate.getDate()+".txt");
    	String mWifiInfo=null;
    	StringBuilder sb=new StringBuilder();
    	if(list != null && list.size() > 0){
    		for(int i=0;i<list.size();i++){
    			mScanResult=list.get(i);
    			mWifiInfo=mScanResult.BSSID+"";
    			Map<String, String>map=new HashMap<String, String>();
    			map.put("wifiName", mScanResult.SSID);
    			map.put("wifiStrength", ""+mScanResult.level);
    			map.put("MacAddress",mWifiInfo);
    			data.add(map);
    			sb.append(mScanResult.SSID+":"+mScanResult.level+" ");
    		}
    		write2txt.writeTxtToFile(sb.toString()+" "+GetDate.getTime(),fileName+".txt");
    	}
    	//---------------------------------------------Gson
    	gsonString=gson.toJson(data);
    	Log.i("tag", gsonString);
    	
    	
        SimpleAdapter adapter=new SimpleAdapter(this, 
    			data, 
    			R.layout.wifi_item, 
    			new String[]{"wifiName","wifiStrength","MacAddress"}, 
    			new int[]{R.id.wifi_name,R.id.wifi_strength,R.id.macaddress});
		lv.setAdapter(adapter);
		
    }
    
    class TvThread extends Thread{
        @Override  
        public void run(){  
            do{  
                try{  
                    Thread.sleep(1000);  
                    Message msg = new Message();  
                    msg.what = 1;//what��int���ͣ�δ�������Ϣ���Ա������Ϣ�߿��Լ�����Ϣ�ǹ���ʲô�ġ�ÿ����������Լ�����Ϣ�����ռ䣬���ص��ĳ�ͻ   
                    mHandler.sendMessage(msg);  
                }  
                catch (InterruptedException e){  
                    e.printStackTrace();  
                } 
            }while (true);  
              
        }  
          
    }  
    

	@Override
	public void onClick(View v) {
		clientThread = new ClientThread("count");
		new Thread(clientThread).start();
		write2txt.writeTxtToFile("count "+GetDate.getTime(),fileName+".txt");
		Toast.makeText(MainActivity.this, "����ɹ�", 1).show();
	} 
	
	@SuppressLint("ShowToast")
	protected void onDestroy() { 
		super.onDestroy();
		System.out.println("�رս����ˣ�");
		Log.i("tag", "�رս����ˣ�");
		System.exit(0);
	} 
}
 
