package com.shinhoandroid.mvp.model.intercept;

import android.os.Build;
import android.text.TextUtils;
import android.text.format.DateUtils;
import com.google.gson.Gson;
import com.shinhoandroid.mvp.model.YmUrls;
import com.shinhoandroid.mvp.model.bean.BaseResult;
import com.shinhoandroid.mvp.model.converter.CustomGsonConverterFactory;
import com.shinhoandroid.mvp.model.exception.NoNetworkException;
import com.shinhoandroid.mvp.model.utils.ApiService;
import com.shinhoandroid.mvp.model.utils.AppUtils;
import com.shinhoandroid.mvp.model.utils.L;
import com.shinhoandroid.mvp.model.utils.NetUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;


/**
 * @author Created by gsd on 2017/1/23.
 */
public class TokenIntercept implements Interceptor {

    private String tag = "intercept";
    private Object mInterceptLock = new Object();

    public static final String ACCESS_TOKEN = "access_token";
    /**
     * 设备型号
     */
    public static final String DEVICE_MODEL = "device_model";
    /**
     *系统类型
     */
    public static final String PLATFORM = "platform";
    /**
     *android来源渠道
     */
    public static final String CHANNEL = "channel";
    /**
     *系统版本号
     */
    public static final String SYSTEM_VERSION = "system_version";
    /**
     *app版本号
     */
    public static final String VERSION = "version";
    /**
     *数据签名
     */
    public static final String SIGN = "sign";
    /**
     *统一数据格式请求
     */
    public static final String SUPPRESS_RESPONSE_CODE = "suppress_response_code";
    /**
     *android
     */
    public static final String ANDROID = "ANDROID";

    private static Map<String, String> queryParams;

    public TokenIntercept() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        synchronized (mInterceptLock) {

            if (!NetUtils.isNetworkAvailable()) {
                throw new NoNetworkException();
            }else {
                Request original = chain.request();

                return  chain.proceed(original);
            }

        }
    }




}
