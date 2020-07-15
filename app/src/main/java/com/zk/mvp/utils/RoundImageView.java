package com.zk.mvp.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.zk.mvp.R;

public class RoundImageView extends ImageView {
    private static final String TAG = "RoundImageView";
    /*type如果为0表示裁剪为圆形，否则为1就是圆角矩形*/
    private int type;
    /*topLeft*/
    private float topLeft;
    /*topRight表示左上角*/
    private float topRight;
    /*bottomLeft*/
    private float bottomLeft;
    /*bottomRight表示左上角*/
    private float bottomRight;

    private Paint paint = new Paint();

    public RoundImageView(Context context) {
        this(context,null);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        type = typedArray.getColor(R.styleable.RoundImageView_round_type,0);
        topLeft = typedArray.getDimension(R.styleable.RoundImageView_round_topLeft,0);
        topRight = typedArray.getDimension(R.styleable.RoundImageView_round_topRight,0);
        bottomLeft = typedArray.getDimension(R.styleable.RoundImageView_round_bottomLeft,0);
        bottomRight = typedArray.getDimension(R.styleable.RoundImageView_round_bottomRight,0);
        typedArray.recycle();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();//得到原始图

        if(drawable == null){
            drawable = getBackground();
        }
        Bitmap bitmap;
        Bitmap scaleBitmap;
        if (null != drawable) {//原始图不为空，防止空指针
            try {
                bitmap =((BitmapDrawable)drawable).getBitmap();//得到需要操作的位图，因为操作图像矩阵不能用Drawable，只能用Bitmap
                scaleBitmap = scaleSrcBitmap(bitmap);
            }catch (Exception e){
                scaleBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background,null);
            }

            Rect rect;
            Bitmap circleBitmap = null;
            if (type == 0) {//裁成圆形
                //处理Bitmap 转成正方形
                Bitmap newBitmap = dealRawBitmap(scaleBitmap);
                circleBitmap = toRoundCorner(newBitmap);
                rect = new Rect(0,0, getWidth(),getHeight());//指明位置
                paint.reset();
                //绘制到画布上
                canvas.drawBitmap(circleBitmap, rect, rect, paint);
            }else{
                //使用后画笔的16种模式
                circleBitmap = toRoundCorner(scaleBitmap);
                rect = new Rect(0,0,getWidth(),getHeight());//指明位置
                paint.reset();
                //绘制到画布上
            }
            if (circleBitmap != null) {
                canvas.drawBitmap(circleBitmap, rect, rect, paint);
            }else {
                super.onDraw(canvas);
            }
        } else {//为空
            super.onDraw(canvas);
        }
    }


    //将头像按比例缩放
    private Bitmap scaleBitmap(Bitmap bitmap){
        int width = bitmap.getWidth();
        //一定要强转成float 不然有可能因为精度不够 出现 scale为0 的错误
        float scale = (float)width/(float)bitmap.getWidth();
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private Bitmap scaleSrcBitmap(Bitmap bitmap){
        // 获得图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 设置想要的大小
        int newWidth = getWidth();
        int newHeight = getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
                true);
        return newbm;
    }
    //将原始图像裁剪成正方形
    private Bitmap dealRawBitmap(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        //获取宽度
        int minWidth = width > height ?  height:width ;
        //计算正方形的范围
        int leftTopX = (width - minWidth)/2;
        int leftTopY = (height - minWidth)/2;
        //裁剪成正方形
        Bitmap newBitmap = Bitmap.createBitmap(bitmap,leftTopX,leftTopY,minWidth,minWidth,null,false);
        return  scaleBitmap(newBitmap);
    }

    private Bitmap toRoundCorner(Bitmap bitmap) {

        //指定为 ARGB_4444 可以减小图片大小
        if (getHeight() == 0) {
            return null;
        }
        Bitmap output = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        final Rect rect = new Rect(0, 0,getWidth(), getHeight());
        paint.setAntiAlias(true);
        if(type == 0){//画圆形的src
            int x = getWidth();
            canvas.drawCircle(x / 2, x / 2, x / 2, paint);
        }else {//画圆角的矩形的src
            RectF rectF = new RectF();
            rectF.left = 0;
            rectF.right = getWidth();
            rectF.top = 0;
            rectF.bottom = getHeight();
            float round = topLeft != 0 ? topLeft : (topRight != 0 ? topRight : (bottomLeft != 0 ? bottomLeft : (bottomRight != 0 ? bottomRight : 0)));
            canvas.drawRoundRect(rectF, round, round, paint);//先画一个四面都有圆角的矩形
            if (topLeft == 0) {
                canvas.drawRect(0, 0, round, round, paint);
            }
            if (topRight == 0) {
                canvas.drawRect(getWidth() - round, 0, getWidth(), round, paint);
            }
            if (bottomLeft == 0) {
                canvas.drawRect(0, getHeight() - round, round, getHeight(), paint);
            }
            if (bottomRight == 0) {
                canvas.drawRect(getWidth() - round, getHeight() - round, getWidth(), getHeight(), paint);
            }
        }
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
