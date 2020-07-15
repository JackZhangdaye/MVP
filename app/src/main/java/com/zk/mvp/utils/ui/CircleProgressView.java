package com.zk.mvp.utils.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;

public class CircleProgressView extends View {
    private Long progress = 0L;
    private String text = "0%";
    private Long max = 1L;

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleProgressView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
        paint.setStyle(Paint.Style.STROKE);//风格
        int stroke = 10;//笔触，是最小圆半径的两倍
        int minRadius = stroke/2;
        float width = getMeasuredWidth();
        float radius = (width - minRadius*4) / 2;//最大圆的半径
        paint.setStrokeWidth(stroke);
        paint.setColor(0xFFDBDBDB);//灰色
        canvas.drawCircle(radius + 2*minRadius, radius + 2*minRadius, radius, paint);
        paint.setStrokeWidth(stroke);
        RectF oval = new RectF(2*minRadius, 2*minRadius, radius * 2 + 2*minRadius, radius * 2 + 2*minRadius);
        paint.setColor(0xFF4080F4);//蓝色
        //这是画蓝弧，第三个参数false是指，不连接到圆心
        canvas.drawArc(oval, -90, 360 * progress / max, false, paint);
        paint.setStrokeWidth(stroke);
        paint.setStyle(Paint.Style.FILL);//设置画笔类型为填充
        paint.setColor(0xFFD3623E);//姜黄
        RectF point = new RectF(radius + minRadius,minRadius, radius + 3*minRadius, 3*minRadius);
        //画一个跨度360的弧，那肯定是个圆了，然后第三个参数为true，表示连接到圆心，这就变成了一个实心圆。你也可以直接drawCircle
        canvas.drawArc(point, 0, 360, true, paint);//画一个点
        paint.setStrokeWidth(2*stroke);
        paint.setColor(0xFFEDBC40);//明黄
        canvas.drawCircle((float) (radius + 2*minRadius + radius * Math.sin(360 * progress / max * Math.PI / 180)), (float) (radius + 2*minRadius - radius * Math.cos(360 * progress / max * Math.PI / 180)), stroke, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(SizeUtils.sp2px(16));
        paint.setStrokeWidth(1.0f);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, width / 2 - bounds.width() / 2,
                width / 2 + bounds.height() / 2, paint);
    }

    /**
     * 初始设置当前进度的最大值-默认100
     *
     * @param max
     */
    public void setMax(Long max) {
        this.max = max;
    }

    public Long getMax() {
        return max;
    }

    /**
     * 更新进度和文字
     *
     * @param progress
     * @param text
     */
    public void setProgressAndText(Long progress, String text) {
        this.progress = progress;
        this.text = text;
        postInvalidate();
    }

}