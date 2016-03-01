package com.tang.mybase.util;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * 图片修改工具类（现在还有问题，有些图片加载不了或者加载了却修改不了颜色）
 * Created by Wangto Tang on 2015/6/23.
 */
public class ImageUtil {

    static Resources r = Resources.getSystem();

    public static Bitmap alterBitMap(int resId,int width,int height,int color){

        Bitmap bitmap = BitmapFactory.decodeResource(r, resId);
        Bitmap alterBitmap = Bitmap.createBitmap(width,height,bitmap.getConfig());
        Canvas canvas = new Canvas(alterBitmap);
        Paint paint = new Paint();
        paint.setColor(color);
        Matrix matrix = new Matrix();
        canvas.drawBitmap(bitmap,matrix,paint);
        return alterBitmap;

    }

}
