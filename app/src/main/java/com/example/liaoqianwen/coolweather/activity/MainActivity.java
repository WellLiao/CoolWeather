package com.example.liaoqianwen.coolweather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.liaoqianwen.coolweather.R;

/**
 * Created by liaoqianwen on 2016/7/28.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button enterCoolWeather = (Button) findViewById(R.id.enterCoolWeather);
        Button forceOffline = (Button) findViewById(R.id.force_offline);
        forceOffline.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.enterCoolWeather:
                Intent intent = new Intent(MainActivity.this, ChooseAreaActivity.class);
                startActivity(intent);
                break;
            case R.id.force_offline:
                Intent intent1 = new Intent("com.liao.broadcastbestpractice.FORCE_OFFLINE");
                sendBroadcast(intent1);
                break;

        }



    }

}
