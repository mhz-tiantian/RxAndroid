package com.mhz.rxandroid.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    protected AppCompatActivity activity;

    protected String TAG = this.getClass().getSimpleName();

    protected static void launchActivity(Context context, Class<? extends BaseActivity> activityClazz) {
        Intent intent = new Intent(context, activityClazz);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        activity = this;
        initView();
    }

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 返回View的布局
     *
     * @return
     */
    protected abstract @LayoutRes
    int getContentView();

}
