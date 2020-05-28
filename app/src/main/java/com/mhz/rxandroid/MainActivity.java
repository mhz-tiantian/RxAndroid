package com.mhz.rxandroid;

import com.mhz.rxandroid.base.BaseActivity;

public class MainActivity extends BaseActivity {
    @Override
    protected void initView() {
        findViewById(R.id.button_basic).setOnClickListener(v -> {
            launchActivity(activity, RxJavaBasicActivity.class);
        });
        findViewById(R.id.button_create_operator).setOnClickListener(v -> {
            launchActivity(activity, OperatorActivity.class);
        });
        findViewById(R.id.button_network).setOnClickListener(v -> {
            launchActivity(activity, RxNetworkActivity.class);

        });

        findViewById(R.id.button_change_operator).setOnClickListener(v -> {
            launchActivity(activity, ChangeOperatorActivity.class);
        });
        findViewById(R.id.button_network_nest).setOnClickListener(v -> {
            launchActivity(activity, RxNetworkNestActivity.class);
        });
        findViewById(R.id.button_merge).setOnClickListener(v -> {
            launchActivity(activity, RxMergeActivity.class);
        });


    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }
}
