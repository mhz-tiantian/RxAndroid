package com.mhz.rxandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mhz.rxandroid.base.BaseActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 *
 * https://www.jianshu.com/p/a406b94f3188
 * Rxjava的基本使用
 */
public class RxJavaBasicActivity extends BaseActivity {



    @Override
    protected void initView() {
        // rxjava 的普通调用
        findViewById(R.id.button_common).setOnClickListener(view -> {
            text1();
        });
        // Rxjava 的链式调用
        findViewById(R.id.button_chained).setOnClickListener(v -> {
            text2();
        });
        findViewById(R.id.button_dispose).setOnClickListener(v -> {
            textDispose();
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_rx_java_basic;
    }

    /**
     * 切断观察者 与被观察者的链接 但是被观察者还是可以发送事件,
     * 但是观察者 对事件已经不做出响应了
     */
    private void textDispose() {
        // 主要是在觀察者的类里面去做
        Observer<Integer> observer = new Observer<Integer>() {

            Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                this.mDisposable = d;
                Log.d(TAG, " 切断链接开始采用  subscribe 链接 ");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, " 切断链接对Next事件 " + value + "做出响应");
                if (value == 2) {
                    mDisposable.dispose();
                    Log.d(TAG, " 已经切换了链接了" + mDisposable.isDisposed());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "切断链接对Error事件做出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "切断链接对Complete事件做出响应 ");
            }
        };
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
            emitter.onComplete();
        }).subscribe(observer);

    }

    // RxJava 链式的调用
    private void text2() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " 链式的调用开始采用  subscribe 链接 ");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, " 链式的调用对Next事件 " + value + "做出响应");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "链式的调用对Error事件做出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "链式的调用对Complete事件做出响应 ");
            }
        });

    }

    //  RxJava普通的调用
    private void text1() {
        // 步骤1> 创建一个被观察者对象  & 生产事件
        // 1. 创建被观察者Observable 对象
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            // 2. 在复写的subscribe()里面定义需要发送的事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                // 通过 ObservableEmitter 类对象产生事件 ,并通知观察者
                // ObservableEmitter 类介绍
                // a. 定义: 事件发射器
                // b. 作用:定义需要发送的事件 & 向观察者发送事件
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });

        // 步骤2 创建观察者observer 并定义响应事件行为
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "普通的调用开始采用 subscribe 链接 ");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "普通的调用对Next事件 " + value + "做出响应");

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "普通的调用对Error事件做出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "普通的调用对Complete事件做出响应 ");
            }
        };

        observable.subscribe(observer);
    }
}
