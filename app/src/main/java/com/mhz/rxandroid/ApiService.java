package com.mhz.rxandroid;

import com.mhz.rxandroid.bean.Translation;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation> getCall();

    // 网络请求1
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20register")
    Observable<Translation> getCall_1();

    // 网络请求2
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20login")
    Observable<Translation> getCall_2();

}
