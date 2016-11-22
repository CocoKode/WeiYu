package com.example.ldy.weiyuweather.Utils;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by LDY on 2016/11/13.
 */
public class FontsUtil {
    private static Map<String, Typeface> fontsCache = new ConcurrentHashMap<>();

    public static void init(Context context) {
        Typeface tf1 = Typeface.createFromAsset(context.getAssets(), "fonts/FuturaLTBold.ttf");
        Typeface tf2 = Typeface.createFromAsset(context.getAssets(), "fonts/WenYue-GuDianMingChaoTi-NC-W5.otf");
        Typeface tf3 = Typeface.createFromAsset(context.getAssets(), "fonts/SiYuan.ttf");

        fontsCache.put("futura", tf1);
        fontsCache.put("mingChao", tf2);
        fontsCache.put("siYuan", tf3);
    }

    public static Typeface getFont(String name, Context context) {
        Typeface tf = fontsCache.get(name);

        if (tf == null) {
            tf = Typeface.createFromAsset(context.getAssets(), name);
            fontsCache.put(name, tf);
        }

        return tf;
    }
}
