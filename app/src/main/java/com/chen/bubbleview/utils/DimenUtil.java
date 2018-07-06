package com.chen.bubbleview.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.WindowManager;

public class DimenUtil {

    private static final String TAG = "DimenUtil";

    /**
     * 得到ActionBar的高度,获取成功。返回正确高度，获取失败。返回-1
     */
    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize,
                tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, context
                    .getResources().getDisplayMetrics());
        }
        LogUtils.e("获取ActionBar的高度失败");
        return -1;
    }

    public static int ps2px(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue * scale);
    }


    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 得到屏幕宽高
     *
     * @param context
     * @return
     */
    public static Point getSrcRect(Context context) {
        // 去的屏幕宽高
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        return size;
    }

    /**
     * 得到屏幕的宽度
     */
    public static int getWindowWidth(Context context) {
        return getSrcRect(context).x;
    }

    /**
     * 得到屏幕的高度
     */
    public static int getWindowHeight(Context context) {
        return getSrcRect(context).y;
    }

}
