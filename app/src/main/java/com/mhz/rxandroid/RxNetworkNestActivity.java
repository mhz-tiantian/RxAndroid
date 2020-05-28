package com.mhz.rxandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.mhz.rxandroid.base.ApiRetrofit;
import com.mhz.rxandroid.base.BaseActivity;
import com.mhz.rxandroid.bean.Translation;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava 操作符,flatMap 网络嵌套
 */
public class RxNetworkNestActivity extends BaseActivity {


    @Override
    protected void initView() {
        findViewById(R.id.button_start).setOnClickListener(v -> {
            networkStart();
        });

    }

    private void networkStart() {
        ApiRetrofit.newInstance().getApiService().getCall_1()
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<Translation>() {
                    @Override
                    public void accept(Translation translation) throws Exception {
                        Log.d(TAG, "accept: 第一次网络请求成功");
                        translation.show();

                    }
                })
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Translation, ObservableSource<Translation>>() {
                    @Override
                    public ObservableSource<Translation> apply(Translation translation) throws Exception {
                        return ApiRetrofit.newInstance().getApiService().getCall_2();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Translation>() {
                    @Override
                    public void accept(Translation translation) throws Exception {
                        Log.d(TAG, "accept:  第二次网络请求成功");
                        translation.show();
                    }
                });


    }

    @Override
    protected int getContentView() {
        return R.layout.activity_rx_network_nest;
    }
}
