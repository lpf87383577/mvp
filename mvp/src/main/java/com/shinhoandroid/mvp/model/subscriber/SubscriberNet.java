package com.shinhoandroid.mvp.model.subscriber;


import android.text.TextUtils;

import com.shinhoandroid.mvp.model.bean.ApiEvent;
import com.shinhoandroid.mvp.model.bean.BaseResult;
import com.shinhoandroid.mvp.model.exception.ApiException;
import com.shinhoandroid.mvp.model.exception.NoNetworkException;
import com.shinhoandroid.mvp.model.exception.TokenInvalidException;
import com.shinhoandroid.mvp.model.utils.EventBusUtils;
import com.shinhoandroid.mvp.model.utils.L;
import com.shinhoandroid.mvp.model.utils.ToastUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import rx.Subscriber;

/**
 * 如果需要集中处理结果 可在此处理
 * @author Created by gsd on 2017/1/20.
 */
public abstract class SubscriberNet<T> extends Subscriber<T> {



    protected boolean needShowLoading = true;

    protected boolean needShowToast = true;

    public SubscriberNet() {
    }

    public SubscriberNet(boolean showLoading) {
        needShowLoading = showLoading;
    }

    public SubscriberNet(boolean showLoading,boolean showToast) {
        needShowLoading = showLoading;
        needShowToast = showToast;
    }


    @Override
    public void onCompleted() {
        hide();         //消除加载dialog

    }

    @Override
    public void onError(Throwable e) {
        hide();
        if (e instanceof TokenInvalidException) {
                //token失效 跳转登陆
                TokenInvalidException tokenInvalidException = (TokenInvalidException) e;
                dispatchApiFailture(tokenInvalidException.getStatus(),tokenInvalidException.getStatus_txt());
                onCallError(e);
            }
        if (e instanceof ApiException) {
             ApiException apiException = (ApiException) e;
             onFailtrueNotice(apiException);
             //统一处理 错误情况
             onFailture(apiException);
         }
         if (e instanceof SocketTimeoutException) {
            //连接超时
            SocketTimeoutException socketTimeoutException = (SocketTimeoutException) e;
            onErrorNotice("网络请求超时");
            onCallError(e);
        } else if (e instanceof NoNetworkException) {
            NoNetworkException noNetworkException = (NoNetworkException) e;
            onErrorNotice("当前网络不可用");
            onCallError(e);
        } else if (e instanceof ConnectException) {
            onErrorNotice("网络连接错误");
            onCallError(e);
        } else {
            onCallError(e);
            L.e( "onError: 未知的错误=" + e);
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        show();     //展示加载dialog
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
        if (t instanceof BaseResult) {
            BaseResult baseResult = (BaseResult)t;

            if (TextUtils.equals(baseResult.status, "2") && needShowToast) {
                onCorrectNotice(baseResult.status_txt);
            }
        }

    }

    private void show() {
        if (needShowLoading) {
            EventBusUtils.post(new LoadingEvent(true));
        }
    }

    public void hide() {
        if (needShowLoading) {
            EventBusUtils.post(new LoadingEvent(false));
        }
    }

    public static class LoadingEvent {
        public boolean show;

        public LoadingEvent(boolean show) {
            this.show = show;
        }
    }

    /**
     * 请求成功回调处理
     *
     * @param tResponse
     */
    public abstract void onSuccess(T tResponse);

    /**
     * 服务器给出失败回调
     *
     * @param e
     */
    public abstract void onFailture(ApiException e);

    /**
     * 请求错误回调
     *
     * @param e
     */
    public void onCallError(Throwable e) {

    }

    /**
     * 正确提示
     * 如对应网络请求status=2的情景
     *
     * @param msg
     */
    public void onCorrectNotice(String msg) {
        if (needShowToast){
            ToastUtils.toastNotice(msg, true);
        }

    }

    /**
     * 请求出错提示(api呼叫错误)
     *
     * @param msg
     */
    public void onErrorNotice(String msg) {
        if (needShowToast) {
            ToastUtils.toastNotice(msg, false);
        }
    }

    /**
     * 错误提示(服务器返回的错误)
     *
     * @param apiException
     */
    public void onFailtrueNotice(ApiException apiException) {
        if (needShowToast) {
            ToastUtils.toastNotice(apiException.getStatus_txt(), false);
        }
    }

    //分发需要页面处理的API的失败（比如Token过期需要回到登陆页）
    public synchronized void dispatchApiFailture(String status,String status_txt) {
        EventBusUtils.post(new ApiEvent(status,status_txt));
    }

}
