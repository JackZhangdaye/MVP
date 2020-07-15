package com.zk.mvp.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.widget.EditText;

import java.math.BigDecimal;

/**
 * 金额输入
 */
public class DoubleInputUtils {

    //保留两位小数
    public static String RetainDecimal(Float value) {
        BigDecimal bg = new BigDecimal(value);
        String data = "" + bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return data;
    }

    //禁止0开头
    public static void ProhibitEditTextInput(EditText editText, Editable s) {
        String text = editText.getText().toString();
        int len = s.toString().length();
        if (len > 1 && text.startsWith("0")) {
            s.replace(0, 1, "");
        }
    }

    /*** 小数点后的位数 */
    private static final int POINTER_LENGTH = 2;

    private static final String POINTER = ".";

    private static final String ZERO = "0";

    private static String number;
    private static int curSelection;

    /***
     * 保留两位小数
     * @param editText
     * @param length 整数数字长度
     */
    public static void keepTwoDecimals(EditText editText, int length) {
        number = editText.getText().toString();
        //第一位不能输入小点
        if (number.length() == 1 && TextUtils.equals(number.substring(0, 1), POINTER)) {
            editText.setText("");
            return;
        }

        //第一位0时，后续不能输入其他数字
        if (number.length() > 1 && TextUtils.equals(number.substring(0, 1), ZERO) &&
                !TextUtils.equals(number.substring(1, 2), POINTER)) {
            editText.setText(number.substring(0, 1));
            editText.setSelection(editText.length());
            return;
        }

        String[] numbers = number.split("\\.");
        //已经输入小数点的情况下
        if (numbers.length == 2) {
            //小数处理
            int decimalsLength = numbers[1].length();
            if (decimalsLength > POINTER_LENGTH) {
                curSelection = editText.getSelectionEnd();
                editText.setText(number.substring(0, numbers[0].length() + 1 + POINTER_LENGTH));
                editText.setSelection(curSelection > number.length() ?
                        number.length() :
                        curSelection);
            }
            //整数处理
            if (numbers[0].length() > length) {
                curSelection = editText.getSelectionEnd();
                editText.setText(number.substring(0, length) + number.substring(length + 1));
                editText.setSelection(curSelection > length ?
                        length :
                        curSelection);
            }
        } else {
            //整数处理
            if (editText.length() > length) {
                if (number.contains(POINTER)) return;
                curSelection = editText.getSelectionEnd();
                editText.setText(number.substring(0, length));
                editText.setSelection(curSelection > length ?
                        length :
                        curSelection);
            }
        }
    }
}
