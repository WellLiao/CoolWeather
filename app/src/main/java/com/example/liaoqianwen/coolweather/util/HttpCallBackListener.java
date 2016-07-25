package com.example.liaoqianwen.coolweather.util;

/**
 * Created by liaoqianwen on 2016/7/25.
 */
public interface HttpCallBackListener {
    void onFinish(String response);

    void onError(Exception e);
}
