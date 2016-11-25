package com.example.ldy.weiyuweather.Screens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.ldy.weiyuweather.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LDY on 2016/11/24.
 */
public class FeedbackActivity extends AppCompatActivity {
    @Bind(R.id.feedback_web)
    WebView mFeedbackWeb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();
        String str=bundle.getString("url");
        openWeb(str);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void openWeb(String str) {
        mFeedbackWeb.getSettings().setJavaScriptEnabled(true);
        mFeedbackWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mFeedbackWeb.loadUrl(str);
    }
}
