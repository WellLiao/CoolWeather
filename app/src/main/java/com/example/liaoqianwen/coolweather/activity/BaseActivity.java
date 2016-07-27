package com.example.liaoqianwen.coolweather.activity;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by liaoqianwen on 2016/7/27.
 */
public class BaseActivity extends Activity{
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        ActivityCollector.addActivity(this); // 活动被创建的时候加入活动管理器
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this); // 活动被销毁的时候从活动管理其中去除
    }
}
