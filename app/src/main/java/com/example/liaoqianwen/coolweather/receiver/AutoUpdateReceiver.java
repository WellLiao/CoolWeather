package com.example.liaoqianwen.coolweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.liaoqianwen.coolweather.service.AutoUpdateService;

/**
 * Created by liaoqianwen on 2016/7/26.
 */
public class AutoUpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("AutoUpdateReceiver","广播接收器接收到消息，开始处理...");
        Intent i = new Intent(context, AutoUpdateService.class);
        context.startService(i);
    }
}
