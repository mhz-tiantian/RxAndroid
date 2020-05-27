package com.mhz.rxandroid;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mhz.rxandroid.base.BaseActivity;
import com.mhz.rxandroid.bean.Translation;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxNetworkActivity extends BaseActivity {


    @Override
    protected void initView() {
        initNetwork();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_rx_network;
    }

    private void initNetwork() {
        Observable.interval(2, 1, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "onNext: " + value);
                        // a. 创建Retrofit对象
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                                .build();

                        // b. 创建 网络请求接口 的实例
                        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
                        // c. 采用Observable<...>形式 对 网络请求 进行封装
                        Observable<Translation> observable = request.getCall();
                        observable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Translation>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(Translation result) {
                                        result.show();

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");

                    }
                });
    }
}
