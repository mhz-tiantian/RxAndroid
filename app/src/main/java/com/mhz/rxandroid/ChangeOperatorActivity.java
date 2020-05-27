package com.mhz.rxandroid;

import android.util.Log;

import com.mhz.rxandroid.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


/**
 * 变换操作符
 */
public class ChangeOperatorActivity extends BaseActivity {


    @Override
    protected void initView() {

        findViewById(R.id.button_map).setOnClickListener(v -> {
            change_map();
        });
        findViewById(R.id.button_flat_map).setOnClickListener(v -> {
            change_flatMap();
        });


    }

    private void change_flatMap() {
        // flatMap的简单例子
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> flatMapList = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    flatMapList.add("我是" + integer + "事件,分发出来的" + i);
                }
                return Observable.fromIterable(flatMapList);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept: " + s);

            }
        });

    }

    private void change_map() {
        // map的使用就是在
        // 对 被观察者发送的每1个事件都通过 指定的函数 处理，从而变换成另外一种事件
        // 即 将被观察者发送的事件转换为任意的类型事件
        // 应用场景: 数据类型转换
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onComplete();

            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {

                return "使用 Map变换操作符 将事件" + integer +
                        "的参数从 整型" + integer + " 变换成 字符串类型"
                        + integer;
            }
        }).subscribe(new Consumer<String>() {
            // Consumer 是只响应  onNext()的事件
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept: " + s);

            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_change_operator;
    }
}
