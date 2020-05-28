package com.mhz.rxandroid;

import android.util.Log;

import com.mhz.rxandroid.base.BaseActivity;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;

/**
 * 合并的操作符
 */
public class RxMergeActivity extends BaseActivity {


    @Override
    protected void initView() {
        findViewById(R.id.button_concat).setOnClickListener(v -> {
            concat();
        });

    }

    private void concat() {
        // concat 作用
        // 组合多个被观察者一起发送数据, 合并后 按发送顺序串行执行
        Observable.concat(
                Observable.just(1, 2, 3),
                Observable.just(5, 6, 7),
                Observable.just(8, 9, 10),
                Observable.just(11, 12, 13)
        ).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "accept: " + integer);

            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_rx_merge;
    }
}
