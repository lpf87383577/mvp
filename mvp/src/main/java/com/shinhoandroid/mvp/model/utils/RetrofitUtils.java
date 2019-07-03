package com.shinhoandroid.mvp.model.utils;

import com.google.gson.Gson;
import com.shinhoandroid.mvp.model.ShinhoConfig;
import com.shinhoandroid.mvp.model.YmUrls;
import com.shinhoandroid.mvp.model.converter.CustomGsonConverterFactory;
import com.shinhoandroid.mvp.model.intercept.TokenIntercept;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * @author Liupengfei
 * @describe TODO
 * @date on 2019/5/30 14:41
 */

public class RetrofitUtils {
    public static boolean isDebug = ShinhoConfig.logIsDebug;
    private static HttpLoggingInterceptor.Level logLevel = HttpLoggingInterceptor.Level.BODY;
    private static int readTimeOut = 15;
    private static int connectTimeOut = 15;
    private static Retrofit sRetrofit;

    public static Retrofit retrofit() {

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .readTimeout(readTimeOut, TimeUnit.SECONDS)
                .connectTimeout(connectTimeOut, TimeUnit.SECONDS);


        okHttpBuilder.addInterceptor(new TokenIntercept());

        if (isDebug) {
            //日志显示级别
            HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
            //新建log拦截器
            HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    L.e("OkHttp====Message:"+message);
                }
            });

            loggingInterceptor.setLevel(level);

            okHttpBuilder.addInterceptor(loggingInterceptor);

        }
        OkHttpClient okHttpClient = okHttpBuilder.build();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(YmUrls.GLOABL_URL)
                .addConverterFactory(CustomGsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        sRetrofit = retrofitBuilder.client(okHttpClient).build();

        return sRetrofit;
    }

}
