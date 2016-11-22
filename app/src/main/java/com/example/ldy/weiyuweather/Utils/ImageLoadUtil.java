package com.example.ldy.weiyuweather.Utils;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by LDY on 2016/11/10.
 */
public class ImageLoadUtil {
    public static void load(Context context, @DrawableRes int imgRes, ImageView imgView) {
        Glide.with(context).load(imgRes).into(imgView);
    }
}
