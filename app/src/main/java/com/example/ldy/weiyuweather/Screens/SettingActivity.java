package com.example.ldy.weiyuweather.Screens;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.ldy.weiyuweather.R;
import com.example.ldy.weiyuweather.Utils.FontsUtil;
import com.example.ldy.weiyuweather.Utils.SharedPreferenceUtil;
import com.example.ldy.weiyuweather.Utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LDY on 2016/11/14.
 * http://blog.sina.com.cn/s/blog_97eedec40102vr7e.html
 * https://github.com/yongjhih/SwitchPreferenceCompat
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.settings_toolbar)
    Toolbar mSettingToolbar;

    @Bind(R.id.collapsing_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Bind(R.id.settings_switch_compat)
    SwitchCompat mSwitchCompat;

    @Bind(R.id.update)
    TextView mUpdate;

    @Bind(R.id.update_txt)
    TextView mUpdateText;

    @Bind(R.id.github_item)
    TextView githubText;

    @Bind(R.id.feedback_item)
    TextView feedbackText;

    BottomSheetDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setSupportActionBar(mSettingToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);


        collapsingToolbarLayout.setTitle("未雨");
        collapsingToolbarLayout.setCollapsedTitleTypeface(FontsUtil.getFont("fonts/WenYue-GuDianMingChaoTi-NC-W5.otf", this));
        collapsingToolbarLayout.setExpandedTitleTypeface(FontsUtil.getFont("fonts/WenYue-GuDianMingChaoTi-NC-W5.otf", this));
        //通过CollapsingToolbarLayout修改字体颜色
        collapsingToolbarLayout.setExpandedTitleColor(Color.BLACK);//设置还没收缩时状态下字体颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        //设置文字位置
        //collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);
        collapsingToolbarLayout.setExpandedTitleGravity(Gravity.BOTTOM);

        mUpdateText.setText(SharedPreferenceUtil.getInstance().getUpdateTime());
        boolean checked = SharedPreferenceUtil.getInstance().getUpdateChecked();
        mSwitchCompat.setChecked(checked);
        mUpdate.setEnabled(true);
        if (!checked) {
            mUpdate.setEnabled(false);
            mUpdate.setTextColor(getResources().getColor(R.color.txt_enable_color));
            mUpdateText.setTextColor(getResources().getColor(R.color.txt_enable_color));
        }
        mSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switch (String.valueOf(b)) {
                    case "true":
                        ToastUtil.showShort("自动更新已打开");
                        SharedPreferenceUtil.getInstance().setUpdateChecked(true);
                        mUpdate.setEnabled(true);
                        mUpdate.setTextColor(getResources().getColor(R.color.txt_able_color));
                        mUpdateText.setTextColor(getResources().getColor(R.color.txt_able_color));
                        break;
                    case "false":
                        ToastUtil.showShort("自动更新已关闭");
                        mUpdate.setEnabled(false);
                        SharedPreferenceUtil.getInstance().setUpdateChecked(false);
                        mUpdate.setTextColor(getResources().getColor(R.color.txt_enable_color));
                        mUpdateText.setTextColor(getResources().getColor(R.color.txt_enable_color));
                        break;
                }
            }
        });

        mUpdate.setOnClickListener(this);
        githubText.setOnClickListener(this);
        feedbackText.setOnClickListener(this);
    }

    private void showDialog() {
        dialog = new BottomSheetDialog(SettingActivity.this);
        View dialogView= LayoutInflater.from(SettingActivity.this)
                .inflate(R.layout.bottom_sheets_layout, null);
        TextView item1 = (TextView) dialogView.findViewById(R.id.dialog_item3);
        TextView item2 = (TextView) dialogView.findViewById(R.id.dialog_item6);
        TextView item3 = (TextView) dialogView.findViewById(R.id.dialog_item12);
        TextView item4 = (TextView) dialogView.findViewById(R.id.dialog_item24);
        item1.setOnClickListener(this);
        item2.setOnClickListener(this);
        item3.setOnClickListener(this);
        item4.setOnClickListener(this);
        dialog.setContentView(dialogView);
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        int updateTime = 0;
        String time = "";
        switch (view.getId()) {
            case R.id.update:
                showDialog();
                return;
            case R.id.github_item:
                goToHtml("https://github.com/FingerDancer/WeiYu");
                return;
            case R.id.feedback_item:
                openWebActivity("http://form.mikecrm.com/zk3BIR");
                return;
            case R.id.dialog_item3:
                time = "3小时";
                updateTime = 3;
                break;
            case R.id.dialog_item6:
                time = "6小时";
                updateTime = 6;
                break;
            case R.id.dialog_item12:
                time = "12小时";
                updateTime = 12;
                break;
            case R.id.dialog_item24:
                time = "24小时";
                updateTime = 24;
                break;
            default:
                break;
        }
        mUpdateText.setText(time);
        dialog.dismiss();
        Intent intent = new Intent();
        intent.putExtra("test", updateTime);
        setResult(10, intent);
        SharedPreferenceUtil.getInstance().setUpdateTime(time);
    }

    private void goToHtml(String url) {
        Uri uri = Uri.parse(url);   //指定网址
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);           //指定Action
        intent.setData(uri);                            //设置Uri
        startActivity(intent);        //启动Activity
    }

    private void openWebActivity(String s) {
        Intent intent=new Intent();
        intent.setClass(SettingActivity.this, FeedbackActivity.class);//从一个activity跳转到另一个activity
        intent.putExtra("url", s);
        startActivity(intent);
    }
}
