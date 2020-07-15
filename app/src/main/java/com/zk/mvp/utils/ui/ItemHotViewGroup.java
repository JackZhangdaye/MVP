package com.zk.mvp.utils.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zk.mvp.R;

import java.util.List;

public class ItemHotViewGroup extends ViewGroup {

    /*布局的总宽度*/
    private int width;
    /*布局左边内边距*/
    private int paddingLeft;
    /*布局上边内边距*/
    private int paddingTop;
    /*布局右边内边距*/
    private int paddingRight;
    /*布局下边内边距*/
    private int paddingBottom;
    /*子标签之间的横轴边距*/
    private int paddingX;
    /*子标签之间的y轴边距*/
    private int paddingY;
    //字体大小
    private int textSize;
    private Context context;
    private List<String> data;

    public ItemHotViewGroup(Context context) {
        this(context,null);
    }

    public ItemHotViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ItemHotViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemHotViewGroup);
        paddingLeft = typedArray.getDimensionPixelOffset(R.styleable.ItemHotViewGroup_paddingLeft,12);
        paddingTop = typedArray.getDimensionPixelOffset(R.styleable.ItemHotViewGroup_paddingTop,24);
        paddingRight = typedArray.getDimensionPixelOffset(R.styleable.ItemHotViewGroup_paddingRight,12);
        paddingBottom = typedArray.getDimensionPixelOffset(R.styleable.ItemHotViewGroup_paddingBottom,24);
        paddingX = typedArray.getDimensionPixelOffset(R.styleable.ItemHotViewGroup_paddingX,12);
        paddingY = typedArray.getDimensionPixelOffset(R.styleable.ItemHotViewGroup_paddingY,12);
//        textSize = typedArray.getDimensionPixelOffset(R.styleable.HotRecommendViewGroup_textSize,12);

        typedArray.recycle();
    }

    /**
     * 布局的宽度要指明为具体的，比如match_parent或者具体的dp等
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        this.width = width;

        //--------------计算布局的高度 start -------------------
        //标签的行数
        int lineCount = 0;
        //某一行的所有孩子的宽度总和
        int childWidthSum = 0;
        //某一行孩子的总个数
        int childLineCount = 0;
        int childHeight = 0;

        for (int i = 0; i < getChildCount(); i++) {
            measureChild(getChildAt(i),widthMeasureSpec,heightMeasureSpec);
            if (i == 0) {
                childHeight = getChildAt(0).getMeasuredHeight();
            }

            childLineCount++;
            childWidthSum += getChildAt(i).getMeasuredWidth();
            if(childWidthSum + (childLineCount -1)*paddingX> (width - paddingLeft - paddingRight)){//换行

                childWidthSum = getChildAt(i).getMeasuredWidth();
                childLineCount = 0;
                lineCount++;
            }
            getChildAt(i).setTag(lineCount);
        }

        //重新设置自己的宽高
        setMeasuredDimension(width,(lineCount+1)*childHeight+(lineCount)*paddingY+paddingTop+paddingBottom);
        //--------------计算布局的高度 end -------------------
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int lastLeft = -1;//上个孩子的左边
        for (int i = 0; i < getChildCount(); i++) {

            int left;
            if(lastLeft == -1){
                left = paddingLeft;
            }else{
                left = lastLeft+getChildAt(i-1).getMeasuredWidth()+paddingX;
            }
            int top = ((Integer) getChildAt(i).getTag())*paddingY+ getChildAt(i).getMeasuredHeight()*((Integer) getChildAt(i).getTag())+paddingTop;
            int right = left + getChildAt(i).getMeasuredWidth();
            int bottom = top + getChildAt(i).getMeasuredHeight();
            getChildAt(i).layout(left,top,right,bottom);

            if(i!=getChildCount()-1){
                if(((Integer) getChildAt(i).getTag()) != ((Integer) getChildAt(i+1).getTag())){
                    lastLeft = -1;
                }else{
                    lastLeft = left;
                }
            }else{
                lastLeft = -1;
            }
        }
    }

    public void setData(List<String> data) {
        this.data = data;
        addChild();
    }

    private void addChild() {
        removeAllViews();//
        if (data == null) {
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            TextView textView = new TextView(context);
            //自定自己在父布局中的宽高，位子等参数
            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            textView.setText(data.get(i));
            textView.setTextSize(14);
            textView.setTextColor(Color.parseColor("#8D8D8D"));
//            textView.setBackgroundResource(R.drawable.re_solid_r8_white);
            textView.setPadding(12,6,12,6);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(((TextView)v).getText().toString());
                }
            });
            addView(textView,lp);
        }
        requestLayout();//重新调用onMeasure，onLayout
    }

    public interface MyClickListener{
        void onClick(String tv);
    }

    public MyClickListener listener;

    public void setMyClickListener(MyClickListener listener){
        this.listener = listener;
    }
}