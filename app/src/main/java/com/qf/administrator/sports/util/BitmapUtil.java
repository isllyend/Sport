package com.qf.administrator.sports.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by se7en on 16/6/2.
 */
public class BitmapUtil {

    public static Bitmap createCircleImage(Bitmap source)
    {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        int radiu=source.getWidth()<source.getHeight()?source.getWidth()/2:source.getHeight()/2;
        Bitmap target = Bitmap.createBitmap(radiu*2, radiu*2, Bitmap.Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 首先绘制圆形
         */
        canvas.drawCircle(radiu,radiu,radiu, paint);
        /**
         * 使用SRC_IN
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * 绘制图片
         */
        Rect srcRect=new Rect();
        srcRect.left=source.getWidth()/2-radiu;
        srcRect.right=source.getWidth()/2+radiu;
        srcRect.top=source.getHeight()/2-radiu;
        srcRect.bottom=source.getHeight()/2+radiu;
        RectF descRect=new RectF();
        descRect.left=0;
        descRect.right=radiu*2;
        descRect.top=0;
        descRect.bottom=radiu*2;
        canvas.drawBitmap(source,srcRect,descRect, paint);
        return target;
    }
    public static Bitmap createRoundImage(Bitmap source,int corner)
    {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 首先绘制椭圆形
         */
        RectF rect=new RectF();
        rect.left=0;
        rect.top=0;
        rect.right=source.getWidth();
        rect.bottom=source.getHeight();
        canvas.drawRoundRect(rect,corner,corner, paint);
        /**
         * 使用SRC_IN
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * 绘制图片
         */
        Rect srcRect=new Rect();
        srcRect.left=0;
        srcRect.right=source.getWidth();
        srcRect.top=0;
        srcRect.bottom=source.getHeight();
        canvas.drawBitmap(source,srcRect,srcRect, paint);
        return target;
    }

}
