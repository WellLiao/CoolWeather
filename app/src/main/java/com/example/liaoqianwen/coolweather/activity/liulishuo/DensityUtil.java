package com.example.liaoqianwen.coolweather.activity.liulishuo;

import android.content.Context;

/**
 */
public class DensityUtil {

    private DensityUtil() {
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
