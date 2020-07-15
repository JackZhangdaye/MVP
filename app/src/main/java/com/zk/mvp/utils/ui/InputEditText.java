package com.zk.mvp.utils.ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * 密码类输入专用    不能换行  不能空格
 */
public class InputEditText extends AppCompatEditText implements TextWatcher {
    private static final String TAG = "InputEditText";

    public InputEditText(Context context) {
        super(context);
    }

    public InputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    //输入之前
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    //正在输入
    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        // 禁止EditText输入空格
        if (charSequence.toString().contains(" ")) {
            String[] str = charSequence.toString().split(" ");
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < str.length; i++) {
                sb.append(str[i]);
            }
            Log.i(TAG, "onTextChanged: 禁止EditText输入空格");
            this.setText(sb.toString());
            this.setSelection(start);
        }

        //设置不能换行
        this.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return true;
            }
        });
    }

    //输入之后
    @Override
    public void afterTextChanged(Editable s) {

    }
}
