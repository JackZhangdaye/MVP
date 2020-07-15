package com.zk.mvp.utils;

import android.content.Context;
import android.graphics.Typeface;

import androidx.annotation.NonNull;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 字体样式
 */
public class TextStyleUtils {


    /**
     * 设置单个字体风格
     *
     * @param context
     * @param fontPath
     * @return
     */
    public static Typeface createTypeface(Context context, String fontPath) {
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }


    // ------------------------------------   start 全局字体风格    -------------------------------------------------
    private void modifyObjectField(Object obj, String fieldName, Object value) {
        try {
            Field defaultField = Typeface.class.getDeclaredField(fieldName);
            defaultField.setAccessible(true);
            try {
                defaultField.set(obj, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private static Utils sUtils = null;

    public static Utils getInstance() {
        // double check
        if (sUtils == null) {
            synchronized (Utils.class) {
                if (sUtils == null) {
                    sUtils = new Utils();
                }
            }
        }
        return sUtils;
    }

    public void replaceSystemDefaultFontFromAsset(@NonNull Context context, @NonNull String fontPath) {
        replaceSystemDefaultFont(createTypefaceFromAsset(context, fontPath));
    }

    private void replaceSystemDefaultFont(@NonNull Typeface typeface) {
        modifyObjectField(null, "MONOSPACE", typeface);
    }

    private Typeface createTypefaceFromAsset(Context context, String fontPath) {
        SoftReference<Typeface> typefaceRef = mCache.get(fontPath);
        Typeface typeface = null;
        if (typefaceRef == null || (typeface = typefaceRef.get()) == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), fontPath);
            typefaceRef = new SoftReference<>(typeface);
            mCache.put(fontPath, typefaceRef);
        }
        return typeface;
    }

    private Map<String, SoftReference<Typeface>> mCache = new HashMap<>();

    // ------------------------------------   end 全局字体风格    -------------------------------------------------
}
