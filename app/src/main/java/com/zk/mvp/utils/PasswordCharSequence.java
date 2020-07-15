package com.zk.mvp.utils;

public class PasswordCharSequence implements CharSequence{
    private CharSequence mSource;

    public PasswordCharSequence(CharSequence source) {
        mSource = source; // Store char sequence
    }

    public char charAt(int index) {
        //这里返回的char，就是密码的样式，注意，是char类型的
        return '*';
    }

    public int length() {
        return mSource.length();
    }

    public CharSequence subSequence(int start, int end) {
        return mSource.subSequence(start, end); // Return default
    }
}
