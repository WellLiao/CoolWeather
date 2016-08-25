package com.example.liaoqianwen.coolweather.activity.liulishuo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * 只重写了onMeasure这一个方法，设置播放视频的长宽
 */
public class PreviewVideoView extends VideoView {

    public PreviewVideoView(Context context) {
        super(context);
    }

    public PreviewVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PreviewVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    /**
     * 宽度保持全屏，高度用宽度/0.56，很多人会问，0.56哪来的？ 是因为这里的引导视频
     * 的尺寸格式为1080 X 1920， 宽/长约等于0.56
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(w, (int) (w / 0.56f));
    }
}
