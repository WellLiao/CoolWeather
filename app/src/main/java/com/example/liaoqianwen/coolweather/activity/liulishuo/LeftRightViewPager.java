package com.example.liaoqianwen.coolweather.activity.liulishuo;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.google.android.gms.dynamic.LifecycleDelegate;

/**
 * Created by liaoqianwen on 2016/8/26.
 */
public class LeftRightViewPager extends ViewPager {
    private boolean left = false;
    private boolean right = false;
    private boolean isScrolling = false;
    private int lastValue = -1;
//    private ChangeViewCallback changeViewCallback = null;

    public LeftRightViewPager(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        init();
    }
    public LeftRightViewPager(Context context){
        super(context);
        init();
    }

    private void init(){
        setOnPageChangeListener(listener);
    }

    public OnPageChangeListener listener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (isScrolling) {
                if (lastValue >positionOffsetPixels){
                    //递减，向右滑动
                    right =true;
                    left = false;
                }else if(lastValue <positionOffsetPixels){
                    //递增，向左滑动
                    right = false;
                    left = true;
                }else if(lastValue == positionOffsetPixels){
                    right = left = false;
                }
            }
            lastValue = positionOffsetPixels;

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == 1){
                isScrolling = true;
            }else {
                isScrolling = false;
            }

        }
    };

    /**
     * 得到是否向右侧滑动
     */
    public boolean getMoveRight() {
        return right;
    }
}
