package com.example.liaoqianwen.coolweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.liaoqianwen.coolweather.receiver.AutoUpdateReceiver;
import com.example.liaoqianwen.coolweather.util.HttpCallBackListener;
import com.example.liaoqianwen.coolweather.util.HttpUtil;
import com.example.liaoqianwen.coolweather.util.Utility;

/**
 * Created by liaoqianwen on 2016/7/25.
 */
public class AutoUpdateService extends Service {
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    /**
     * onStartCommand方法会在每次服务启动的时候调用，通常情况下，如果我们希望服务一旦启动就立刻去执行某个动作，
     * 就可以将逻辑写在onStartCommand()方法里。
     * onCreate()方法是在服务第一次创建的时候调用的，而onStartCommand()方法则在每次启动服务的时候都会调用。
     * 注意：虽然每调用一次startService()方法，onStartCommand()就会执行一次，但实际上每个服务都只会存在一个实例。
     * 所以不管你调用了多少次startService()方法，只需调用一次stopService()或stopSelf()方法，服务就会停止下来了。
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.i("AutoUpdateService","onStartCommand executed");

        new Thread(new Runnable() {
            @Override
            public void run() {
                updateWeather();
            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 2 * 60 * 1000; //8小时毫秒数 =8 * 60 * 60 * 1000
        // SystemClock.elapsedRealtime() 系统开机至今所经历时间的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AutoUpdateReceiver.class);
        // 获取一个能够执行广播的PendingIntent
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        // 设置定时任务 从系统开机算起ELAPSED_REALTIME_WAKEUP、从1970年1月1日算起ELAPSED_REALTIME、RTC、RTC_WAKEUP
        // set()方法不准确，有可能延迟一会才能得到执行，这是因为Android在耗电方面做的优化。为了执行精确的任务，应使用setExact()方法
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 更新天气信息
     */
    private void updateWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherCode = prefs.getString("weather_code", "");
        String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
        HttpUtil.sendHttpRequest(address, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                Utility.handleWeatherResponse(AutoUpdateService.this, response);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();

            }
        });
    }
}
