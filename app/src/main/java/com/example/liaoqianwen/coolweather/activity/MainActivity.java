package com.example.liaoqianwen.coolweather.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        enterCoolWeather.setOnClickListener(this);
        forceOffline.setOnClickListener(this);
        Button dialogTest = (Button) findViewById(R.id.dialogTest);
        dialogTest.setOnClickListener(this);
        Button viewDemo = (Button) findViewById(R.id.viewDemo);
        viewDemo.setOnClickListener(this);

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
                Log.i("MainActivity 线程Id:",String.valueOf(Thread.currentThread().getId()));
                Log.i("MainActivity",intent1.toString());
                break;
            case R.id.dialogTest:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setTitle("Warning");
                dialogBuilder.setMessage("This is AlertDialog Test");
//                dialogBuilder.setCancelable(false);
                dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
                break;
            case R.id.viewDemo:
                Intent intent2 = new Intent(MainActivity.this, ViewDemoActivity.class);
                startActivity(intent2);

        }



    }

}
