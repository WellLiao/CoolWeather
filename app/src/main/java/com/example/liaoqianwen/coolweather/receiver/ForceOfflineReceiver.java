package com.example.liaoqianwen.coolweather.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;

import com.example.liaoqianwen.coolweather.activity.ActivityCollector;
import com.example.liaoqianwen.coolweather.activity.LoginActivity;

/**
 * Created by liaoqianwen on 2016/7/28.
 */
public class ForceOfflineReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.i("ForceOfflineReceiver",String.valueOf(Thread.currentThread().getId()));
        Log.i("ForceOfflineReceiver",context.toString());
        Log.i("ForceOfflineReceiver","接收到强制下线的广播并开始处理");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle("Warning");
        dialogBuilder.setMessage("You are forced to be offline. Pleage try to login again");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCollector.finishAll(); //销毁所有活动
                Intent intent = new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent); // 跳到登录界面
            }
        });
        AlertDialog alertDialog = dialogBuilder.create();
        // 需要设置AlertDialog的类型， 保证在广播接收器中可以正常弹出

        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.show();
    }
}
