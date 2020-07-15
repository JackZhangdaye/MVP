package com.zk.mvp.utils.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zk.mvp.utils.PasswordCharSequence;

import static com.blankj.utilcode.util.SizeUtils.dp2px;

/**
 * 密码   带图片删除
 */
public class InputViewGroup extends ViewGroup {
    private static final String TAG = "InputViewGroup";
    private InputEditText editText;
    private ImageView imageView;

    private Context mContext;

    private int maxWidth;
    private int maxHeight;

    public InputViewGroup(Context context) {
        this(context,null);
    }

    public InputViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public InputViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        imageView = new ImageView(context);
        imageView.setVisibility(GONE);
//        imageView.setBackgroundResource(R.drawable.toast_err);

        editText = new InputEditText(context);
        editText.setBackground(null);
        editText.setHint("请输入密码");
        editText.setTextSize(16);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
        editText.setTextColor(Color.parseColor("#FF0000"));
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        DigitsKeyListener digitsKeyListener = DigitsKeyListener.getInstance("1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM");
        editText.setKeyListener(digitsKeyListener);
        editText.setLineSpacing(12,10);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    imageView.setVisibility(VISIBLE);
                }else {
                    imageView.setVisibility(GONE);
                }
            }
        });

        editText.setTransformationMethod(new TransformationMethod() {
            @Override
            public CharSequence getTransformation(CharSequence source, View view) {
                return new PasswordCharSequence(source);
            }

            @Override
            public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction, Rect previouslyFocusedRect) {

            }
        });


        //清空文本内容
        imageView.setOnClickListener(v -> clearContent());

        addView(editText);
        addView(imageView);
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
                getChildAt(i).layout(0,0,maxWidth - dp2px(44),maxHeight);
            }else {
                getChildAt(i).layout(maxWidth - dp2px(32),maxHeight / 2 - dp2px(10),maxWidth - dp2px(12),maxHeight / 2 + dp2px(10));
            }
        }
    }

    public String getTextToString(){
        if (editText.getText() != null) {
            if (!TextUtils.isEmpty(editText.getText().toString())) {
                return editText.getText().toString();
            }
        }
        return "";
    }

    public void clearContent(){
        editText.setText("");
    }



}
