package com.example.ldy.weiyuweather.Utils;

import android.telecom.CallScreeningService;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ldy.weiyuweather.R;

/**
 * Created by LDY on 2016/9/29.
 */
public class Utils {
    public static void setupItem(final View view, final LibraryObject libraryObject) {
        final TextView txt = (TextView) view.findViewById(R.id.txt_item);
        txt.setText(libraryObject.getTitle());

        final ImageView img = (ImageView) view.findViewById(R.id.img_item);
        img.setImageResource(libraryObject.getRes());
    }

    public static void setupDetailItem(final View view, final LibraryObject libraryObject) {
        final TextView txt = (TextView) view.findViewById(R.id.detail_txt);
        txt.setText(libraryObject.getTitle());

        final ImageView img = (ImageView) view.findViewById(R.id.detail_img);
        img.setImageResource(libraryObject.getRes());
    }
    public static class LibraryObject {
        private String mTitle;
        private int mRes;

        public LibraryObject(final int res, final String title) {
            mRes = res;
            mTitle = title;
        }

        public String getTitle() {
            return mTitle;
        }
        public int getRes() {
            return mRes;
        }
        public void setTitle(final String title) {
            mTitle = title;
        }
        public void setRes(final int res) {
            mRes = res;
        }
    }
}
