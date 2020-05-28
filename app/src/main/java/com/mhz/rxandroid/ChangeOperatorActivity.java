package com.mhz.rxandroid;

import android.util.Log;

import com.mhz.rxandroid.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


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


        findViewById(R.id.button_concat_map).setOnClickListener(v -> {
            change_concatMap();
        });


    }

    /**
     * concatMap
     */
    private void change_concatMap() {
        // concatMap 功能与flatMap类似,  最大的区别就是可以保证顺序,  flatMap是无序的, 而concatMap则是一个有序的
        Observable.just(1, 2, 3, 4, 5).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> flatMapList = new ArrayList<>();
                int delay = 0;
                if (integer == 3) {
                    delay = 500;//延迟500ms发射
                }
                for (int i = 0; i < 3; i++) {
                    flatMapList.add("我是" + integer + "事件,分发出来的" + i);
                }
                return Observable.fromIterable(flatMapList).delay(delay, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "accept: " + s);
                    }
                });


    }

    private void change_flatMap() {
        // flatMap的简单例子
        // 将被观察者发送的事件序列进行 拆分 & 单独转换，再合并成一个新的事件序列，最后再进行发送
        // 为事件序列中每个事件都创建一个 Observable 对象；
        //将对每个 原始事件 转换后的 新事件 都放入到对应 Observable对象；
        //将新建的每个Observable 都合并到一个 新建的、总的Observable 对象；
        //新建的、总的Observable 对象 将 新合并的事件序列 发送给观察者（Observer）
        // 需要注意的是flatMap 是一个无序的, 就是无法保证,发送的顺序, 无法上游的顺序, flatMap转换以后还是一样的顺序

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onNext(5);

            }

        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> flatMapList = new ArrayList<>();
                int delay = 0;
                if (integer == 3) {
                    delay = 500;//延迟500ms发射
                }
                for (int i = 0; i < 3; i++) {
                    flatMapList.add("我是" + integer + "事件,分发出来的" + i);
                }
                return Observable.fromIterable(flatMapList).delay(delay, TimeUnit.MILLISECONDS);

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
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
