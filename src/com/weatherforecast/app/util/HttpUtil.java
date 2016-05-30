package com.weatherforecast.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpUtil {
	private static final int BUFFER_SIZE=1024*1024;
	public static void sendHttpRequest(final String address,
			final HttpCallbackListener listener){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpURLConnection connection=null;
				try {
					URL url=new URL(address);
					connection=(HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					char[] data = new char[BUFFER_SIZE];
					InputStream in=connection.getInputStream();
					BufferedReader reader=new BufferedReader(new InputStreamReader(in));
					String response;
					String line;
					int len = reader.read(data); 
//					while ((line=reader.readLine())!=null) {
//						response.append(line);
//					}
					response=String.valueOf(data, 0, len);
					if(listener!=null){
						//�ص�onFinish()����
						listener.onFinish(response);
					}
				} catch (Exception e) {
					if(listener!=null){
						//�ص�onError()����
						listener.onError(e);
					}
				} finally{
					if(connection!=null){
						connection.disconnect();
					}
				}
			}
		}).start();
	}
}
