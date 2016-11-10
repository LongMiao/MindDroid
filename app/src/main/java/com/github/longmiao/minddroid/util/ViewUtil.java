package com.github.longmiao.minddroid.util;

import android.content.Context;
import android.text.TextPaint;

/**
 * Created by 13208 on 2016/11/11.
 */
public class ViewUtil {

    public static float getTextWidth(Context context, String text, float textSize) {
        TextPaint paint = new TextPaint();
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        paint.setTextSize(scaledDensity * textSize);
        return paint.measureText(text);
    }

}
