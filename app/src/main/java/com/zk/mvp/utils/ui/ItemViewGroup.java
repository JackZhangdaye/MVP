package com.zk.mvp.utils.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.zk.mvp.R;

import static com.blankj.utilcode.util.SizeUtils.dp2px;
import static com.blankj.utilcode.util.SizeUtils.sp2px;

public class ItemViewGroup extends ViewGroup {
    private static final String TAG = "ItemViewGroup";

    private Context mContext;
    private String titleText,newsText;
    /**
     * @param drawable  item图片
     * @param titleText item内容
     * @Param newsText  描述，余额之类什么的  如果需要的话
     * @param isShow    是否有箭头图片
     */
    public void setContent(int drawable,String titleText,String newsText,boolean isShow) {
        if (drawable != 0) {
            ivHead.setImageDrawable(mContext.getDrawable(drawable));
        }
        if (!isShow) {
            ivRight.setVisibility(INVISIBLE);
        }
        addView(ivHead);
        addView(ivRight);
        this.titleText = titleText;
        this.newsText = newsText;
        Log.i(TAG, "setContent: "+titleText);
//        this.requestLayout();
    }

    //布局宽高
    private int maxHeight;
    private int maxWidth;

    private ImageView ivHead;
    private ImageView ivRight;

    public ItemViewGroup(Context context) {
        this(context,null);
    }

    public ItemViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ItemViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        mContext = context;
        ivHead = new ImageView(context);
        ivRight = new ImageView(context);
        ivRight.setImageDrawable(context.getDrawable(R.drawable.to_go_black));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        maxHeight = MeasureSpec.getSize(heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (i == 0) {
                getChildAt(i).layout(dp2px(12),maxHeight / 2 - dp2px(10),dp2px(32),maxHeight / 2 + dp2px(10));
            }else {
                getChildAt(i).layout(maxWidth - dp2px(32),maxHeight / 2 - dp2px(10),maxWidth - dp2px(12),maxHeight / 2 + dp2px(10));
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint mPaint = new Paint();
        Rect bounds = new Rect();
        mPaint.setTypeface(Typeface.SERIF);
        mPaint.setColor(Color.parseColor("#111111"));
        mPaint.getTextBounds(titleText,0,titleText.length(),bounds);
        mPaint.setTextSize(sp2px(18));
        canvas.drawText(titleText,dp2px(42),maxHeight / 2 + (Math.abs(bounds.top) + Math.abs(bounds.bottom)),mPaint);
        if (!TextUtils.isEmpty(newsText)) {
            mPaint.setColor(Color.parseColor("#666666"));
            mPaint.getTextBounds(newsText,0,newsText.length(),bounds);
            mPaint.setTextSize(sp2px(14));
            canvas.drawText(newsText,maxWidth - dp2px(42) - bounds.width(),maxHeight / 2 + bounds.height() / 2 - bounds.bottom,mPaint);
        }
    }
}
