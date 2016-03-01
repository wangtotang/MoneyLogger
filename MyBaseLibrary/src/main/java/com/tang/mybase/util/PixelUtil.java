package com.tang.mybase.util;

import android.content.res.Resources;


/**
 * 像素转换工具类
 * Created by Wangto Tang on 2015/3/28.
 */
public class PixelUtil {

    static Resources r = Resources.getSystem();

    /**
     * dp转 px.
     *
     * @param dp 独立密度
     * @return px 像素值
     */
    public static int dp2px(float dp) {
        float scale = r.getDisplayMetrics().densityDpi;
        return (int) (dp * (scale / 160) + 0.5f);
    }

    /**
     * sp转px.
     *
     * @param sp 文本可扩展密度
     * @return px 像素值
     */
    public static int sp2px(float sp) {
        float px = sp * r.getDisplayMetrics().scaledDensity;
        return (int) (px + 0.5f);
    }

}
