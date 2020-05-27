package com.mhz.rxandroid;

import com.mhz.rxandroid.bean.Translation;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GetRequest_Interface {
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation> getCall();
}
