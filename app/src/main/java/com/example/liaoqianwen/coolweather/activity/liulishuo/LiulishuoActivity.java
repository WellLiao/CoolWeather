package com.example.liaoqianwen.coolweather.activity.liulishuo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.liaoqianwen.coolweather.R;
import com.example.liaoqianwen.coolweather.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by liaoqianwen on 2016/8/25.
 */
/**
 * 18s的动画分成了三部分
 引导页也是三页，而且每一页都在循环对应的6s
 播放mp4可以用VideoView
 播放至指定位置可以用VideoView暴露的seekTo(int msec)
 */
public class LiulishuoActivity extends AppCompatActivity{
    private PreviewVideoView mVideoView;
    private ViewPager mVpImage;
    private PreviewIndicator mIndicator;

    private List<View> mViewList = new ArrayList<>();
    // 每个引导页上的文字图片
    private int[] mImageResIds = new int[]{R.mipmap.intro_text_1, R.mipmap.intro_text_2, R.mipmap.intro_text_3};
    private CustomPagerAdapter mAdapter;

    private int mCurrentPage = 0;
    private Subscription mLoop;

    private Button toLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_liulishuo);
//播放视频的VideoView
        mVideoView = (PreviewVideoView) findViewById(R.id.vv_preview);
//      左右滑动的ViewPager
        mVpImage = (ViewPager) findViewById(R.id.vp_image);
//        循环指示器，就是那几个点
        mIndicator = (PreviewIndicator) findViewById(R.id.indicator);
/**     VideoView准备播放 */
        mVideoView.setVideoURI(Uri.parse(getVideoPath()));

        for (int i = 0; i < mImageResIds.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.preview_item, null, false);
            ((ImageView) view.findViewById(R.id.iv_intro_text)).setImageResource(mImageResIds[i]);
            mViewList.add(view);
        }

        mAdapter = new CustomPagerAdapter(mViewList);
        mVpImage.setAdapter(mAdapter);
        mVpImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 当最后一个你再向左滑动的时候就会进入LoginActivity了
                if( position == mViewList.size()-1){
                    LoginActivity.startActivity(LiulishuoActivity.this);
                    finish();
                }

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                mIndicator.setSelected(mCurrentPage);
                startLoop();

                // liaoqianwen 添加
                toLogin = (Button)findViewById(R.id.toLogin);
                if(mCurrentPage == 2) {
                    toLogin.setVisibility(View.VISIBLE);
                    toLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LoginActivity.startActivity(LiulishuoActivity.this);
                        }
                    });
                }else {
                    toLogin.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        startLoop();

    }

    /**
     * 获取video文件的路径
     *
     * @return 路径
     */
    private String getVideoPath() {
        return "android.resource://" + this.getPackageName() + "/" + R.raw.intro_video;
    }

    /**
     * 开启轮询   何时startLoop呢？？  一个是启动后，一个是ViewPager滑动后
     * 轮询的目的是让视频不断的重复播放，  如果不用Observable则播放完一遍之后就停止了
     */
    private void startLoop() {
        if (null != mLoop) {
            mLoop.unsubscribe();
        }
//        创建一个按固定时间间隔发射整数序列的Observable
        mLoop = Observable.interval(0, 6 * 1000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
//                        播放至指定位置
                        mVideoView.seekTo(mCurrentPage * 6 * 1000);
                        if (!mVideoView.isPlaying()) {
                            mVideoView.start();
                        }
                    }
                });
          /** 考虑不用Observable 会是怎样的效果*/
//        mVideoView.seekTo(mCurrentPage * 6 * 1000);
//        if (!mVideoView.isPlaying()) {
//            mVideoView.start();
//        }

    }

    @Override
    protected void onDestroy() {
        if (null != mLoop) {
            mLoop.unsubscribe();
        }
        super.onDestroy();
    }

    public static class CustomPagerAdapter extends PagerAdapter {

        private List<View> mViewList;

        public CustomPagerAdapter(List<View> viewList) {
            mViewList = viewList;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
