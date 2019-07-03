package com.shinhoandroid.mvp.model.global;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.shinhoandroid.mvp.model.bean.BaseResult;
import com.shinhoandroid.mvp.model.exception.OauthFailtureException;
import com.shinhoandroid.mvp.model.exception.ServiceErrorException;
import com.shinhoandroid.mvp.model.exception.TokenInvalidException;
import com.shinhoandroid.mvp.model.utils.L;


import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author Created by gsd on 2017/1/25.
 */
public class ObservableHandler {

    /**
     * 对网络请求返回的obervable统一处理
     *
     * @param observable
     * @param <T>
     * @return
     */
    public static <T> Observable<T> handle(Observable<T> observable) {

        return observable.onErrorResumeNext(new Func1<Throwable, Observable<T>>(){

            @Override
            public Observable<T> call(Throwable throwable) {

                if (throwable instanceof HttpException) {

                    try {
                        HttpException httpException = (HttpException) throwable;
                        Response<?> response = httpException.response();
                        ResponseBody responseBody = response.errorBody();
                        String errorJson = null;

                        errorJson = responseBody.string();
                        BaseResult errorResult = new Gson().fromJson(errorJson, BaseResult.class);
                        int code = response.code();
                        L.e("lpf-HttpException-" + code);
                        // //服务器错误
                        // if (code >= YmConstants.KEY_INT_FIVE_HUNDRED) {
                        //     return Observable.error(new ServiceErrorException(errorResult));
                        // } else {
                        //     if (TextUtils.equals(errorResult.status, StatusCodes.OAUTH_FAILTURE) || TextUtils.equals(errorResult.status, StatusCodes.OAUTH_FAILTURE_CODE)) {
                        //         //认证失败异常 直接去登陆页面
                        //         return Observable.error(new OauthFailtureException(errorResult));
                        //     } else if (TextUtils.equals(errorResult.status, StatusCodes.TOKEN_INVALID)) {
                        //         //token失效异常 去刷新token
                        //         return Observable.error(new TokenInvalidException(errorResult));
                        //     } else if (!TextUtils.equals(errorResult.status, StatusCodes.SUCCESS)
                        //             && !TextUtils.equals(errorResult.status, StatusCodes.SUCCESS_WITH_DEAL)) {
                        //         //特定 API 的错误，在相应的 Subscriber 的 onError 的方法中进行处理
                        //         return Observable.error(new ApiException(errorResult));
                        //     } else {
                        //         return Observable.error(throwable);
                        //     }
                        //
                        // }
                        return Observable.error(throwable);

                    } catch (IOException e) {
                        e.printStackTrace();
                        return Observable.error(throwable);
                    }

                } else {
                    return Observable.error(throwable);
                }

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }


}
