package com.chenghui.ekaxin.util;

import com.chenghui.ekaxin.CustomApplcation;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * @ClassName: �ռ�����   �Ի�ȡ��ͼƬ��С����
 * @Description: 
 * @author kcj
 * @date 2015-1-8
 */
public final class ActivityUtil {

    public static float[] getBitmapConfiguration(Bitmap bitmap, ImageView imageView, float screenRadio) {
        int screenWidth=ActivityUtil.getScreenSize()[0];
        float rawWidth=0;
        float rawHeight=0;
        float width=0;
        float height=0;
        if(bitmap == null) {
            // rawWidth = sourceWidth;
            // rawHeight = sourceHeigth;
            width=(float)(screenWidth / screenRadio);
            height=(float)width;
            imageView.setScaleType(ScaleType.FIT_XY);
        } else {
            rawWidth=bitmap.getWidth();
            rawHeight=bitmap.getHeight();
            if(rawHeight > 10 * rawWidth) {
                imageView.setScaleType(ScaleType.CENTER);
            } else {
                imageView.setScaleType(ScaleType.FIT_XY);
            }
            float radio=rawHeight / rawWidth;

            width=(float)(screenWidth / screenRadio);
            height=(float)(radio * width);
        }
        return new float[]{width, height};
    }

    /**
     * ��ȡ��Ļ���
     * @param activity
     * @return
     */
    public static int[] getScreenSize() {
        int[] screens;
        // if (Constants.screenWidth > 0) {
        // return screens;
        // }
        DisplayMetrics dm=new DisplayMetrics();
        dm = CustomApplcation.getInstance().getResources().getDisplayMetrics();
        screens=new int[]{dm.widthPixels, dm.heightPixels};
        return screens;
    }

}
