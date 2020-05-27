package com.mhz.rxandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mhz.rxandroid.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * https://www.jianshu.com/p/e19f8ed863b1
 * Rxjava操作符的简单介绍
 */
public class OperatorActivity extends BaseActivity {

    Integer i = 10;



    @Override
    protected void initView() {
        findViewById(R.id.button_create).setOnClickListener(v -> {
            operatorCreate();
        });
        // just 创建 被观察者
        findViewById(R.id.button_just).setOnClickListener(v -> {
            operatorJust();
        });

        findViewById(R.id.button_from_array).setOnClickListener(v -> {
            operatorFromArray();
        });

        findViewById(R.id.button_from_iterable).setOnClickListener(v -> {
            operatorFromIterable();
        });

        findViewById(R.id.button_defer).setOnClickListener(v -> {
            operatorDefer();
        });
        findViewById(R.id.button_timer).setOnClickListener(v -> {
            operatorTimer();
        });
        findViewById(R.id.button_interval).setOnClickListener(v -> {
            operatorInterval();
        });
        findViewById(R.id.button_interval_range).setOnClickListener(v -> {
            operatorIntervalRange();
        });

        findViewById(R.id.button_range).setOnClickListener(v -> {
            operatorRange();
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_operator;
    }

    private void operatorRange() {
        // range 指定发送事件的范围,
        // 与 intervalRange 类似, 就是没有延迟发送的机制
        Observable.range(3, 10).subscribe(new Observer<Integer>() {
            //  这个例子:就是从3 开始发送事件, 每次递增1 , 发送10次
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");

            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "onNext: " + value);

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

    private void operatorIntervalRange() {
        // intervalRange  1. 快速创建一个被观察者对象(Observable )
        //  2.每隔指定时间 就发送事件  可以指定发送数据的数量
        // 发送的事件序列 ==从0 开始, 无限递增1  的整数序列
        /**
         * 参数说明
         * 1.时间序列起始点
         * 2. 时间的数量
         * 3. 第一次事件延迟发送事件
         * 4. 间隔时间数字
         * 5. 时间单位
         *
         */
        Observable.intervalRange(3, 10, 2, 1, TimeUnit.SECONDS)
                // 延迟2秒, 每隔间隔1秒, 从数字3 发送事件, 发送10次,也就是
                // 3,4,5,6,7,8,9,10,11,12
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");

                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "onNext: " + value);

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


    private void operatorInterval() {
        // interval  1. 快速创建一个被观察者对象(Observable )
        //  2.每隔指定时间 就发送事件
        // 发送的事件序列 = =从0 开始, 无限递增1  的整数序列
        /**
         * 参数说明
         * 1. 第一次延迟时间
         * 2. 间隔时间数字
         * 3. 时间单位
         */
        Observable.interval(3, 2, TimeUnit.SECONDS)
                // 延迟3秒 发送事件, 每隔2秒 产生一个数字(从0开始)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "onNext: " + value);

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

    private void operatorTimer() {
        // timer
        // 快速创建1个被观察者对象（Observable）
        //发送事件的特点：延迟指定时间后，发送1个数值0（Long类型）
        // 本质 = 延迟指定时间后，调用一次 onNext(0)    这里就是调用一次事件就结束了
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "onNext: " + value);

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

    /**
     * 利用操作符 defer延迟 创建被观察者
     * <p>
     * defer 作用:直到有观察者(Observer )订阅时, 才动态创建被观察者对象(Observable) && 发送事件
     * <p>
     * 通过 Observable工厂方法创建被观察者对象（Observable）
     * 每次订阅后，都会得到一个刚创建的最新的Observable对象，这可以确保Observable对象里的数据是最新的
     * <p>
     * 应用场景
     * 动态创建被观察者对象（Observable） & 获取最新的Observable对象数据
     */
    private void operatorDefer() {
        // 第一次 对i 进行赋值
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(i);
            }
        });
        // 第二次对i 进行赋值
        i = 15;
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "onNext: " + value);

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

    private void operatorFromIterable() {
        // 快速创建 被观察者对象（Observable） & 可以发送10个以上事件  集合的形式
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        integerList.add(4);
        Observable.fromIterable(integerList).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "onNext: " + value);

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


    private void operatorFromArray() {
        // //快速创建 被观察者对象（Observable） & 可以发送10个以上事件 数组的形式
        Integer[] items = {0, 1, 2, 3, 4};
        Observable.fromArray(items).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");

            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "onNext: " + value);

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


    private void operatorJust() {
        //快速创建 被观察者对象（Observable） & 发送10个以下事件 .因为just 是一个重载的方法, 只能接受10个参数
        Observable.just(1, 2, 3, 4, 5, 7, 8, 9, 10, 11).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "onNext: " + value);

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


    /**
     * 通过操作符create创建
     */
    private void operatorCreate() {

        // 生成 观察者

        // 1 通过create 创建被观察者 Observable  的对象
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            // 2. 在复写 subscribe 里定义需要发送的事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                // 通过 ObservableEmitter 类对象生产 & 发送事件
                // ObservableEmitter 类 定义
                //a. 事件发射器
                //b .作用:定义需要发送的事件,向观察者发送事件
                // todo 注意: 建议发送事件前检查观察者的 isUnsubscribed状态，以便在没有观察者时，让Observable停止发射数据
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });

        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "onNext: " + value);
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
