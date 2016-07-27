package com.example.liaoqianwen.coolweather.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by liaoqianwen on 2016/7/25.
 */
public class HttpUtil {

    public static void sendHttpRequest(final String address, final HttpCallBackListener listener){
        new Thread( new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
//                    String line;
//
//                    while ((line = reader.readLine() ) != null){
//                        response.append(line);
//                    }
                    int ch;
                    int i = 0;
                    InputStreamReader inReader = new InputStreamReader(in);
                    while ((ch=inReader.read()) != -1) { //TODO inReader输入流没有结束标记，所以这里把方法写死，当读到第二个"}"时就认为结束了
                        char ch1 = (char)ch;
                        response.append(Character.toString(ch1));
                        if (ch1 == '}') {
                            i++;
                        }
                        if(i==2){
                            break;
                        }

                    }

                    if(listener != null){
                        //
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    if (listener != null) {
                        //
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }


}
