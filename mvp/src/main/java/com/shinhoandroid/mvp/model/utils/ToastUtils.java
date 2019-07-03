package com.shinhoandroid.mvp.model.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.shinhoandroid.mvp.R;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * @author Created by gsd on 2016/10/26.
 */
public class ToastUtils {


    private static Context mContext = AppUtils.getAppContext();
    private static Toast sToast;

    public static void show(String msg) {
        Toast.makeText(AppUtils.getAppContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 展示toast提示
     *
     * @param msg
     * @param isSuccess
     */
    public static void toastNotice(final String msg, final boolean isSuccess) {

        //L.e("lpf--sToast-");
        Observable.just(isSuccess)
                .flatMap(new Func1<Boolean, Observable<View>>() {
                    @Override
                    public Observable<View> call(Boolean aBoolean) {

                        View toastView = LayoutInflater.from(mContext).inflate(R.layout.view_custom_toast, null);

                        TextView tvNoticeMsg = ButterKnife.findById(toastView, R.id.tv_notice_msg);
                        ImageView ivNoticeImg = ButterKnife.findById(toastView, R.id.iv_notice_img);

                        if (aBoolean) {

                            ivNoticeImg.setImageResource(R.drawable.ic_success);
                        } else {
                            ivNoticeImg.setImageResource(R.drawable.ic_fail);
                        }

                        tvNoticeMsg.setText(msg);

                        return Observable.just(toastView);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<View>() {
                    @Override
                    public void call(View view) {
                        //L.e("lpf--sToast-");
                        if (sToast == null) {
                            sToast = new Toast(mContext);
                            sToast.setGravity(Gravity.CENTER, 0, 0);
                            sToast.setDuration(Toast.LENGTH_SHORT);
                        }
                        sToast.setView(view);
                        sToast.show();

                    }
                });
    }

    /**
     * 展示Toast提示
     *
     * @param msg   提示的消息
     * @param imgId 图片资源id  传负数不显示图片
     */
    public static void toastNotice(final String msg, final int imgId) {

        Observable.just(imgId)
                .flatMap(new Func1<Integer, Observable<View>>() {
                    @Override
                    public Observable<View> call(Integer integer) {
                        View toastView = LayoutInflater.from(mContext).inflate(R.layout.view_custom_toast, null);

                        TextView tvNoticeMsg = ButterKnife.findById(toastView, R.id.tv_notice_msg);
                        ImageView ivNoticeImg = ButterKnife.findById(toastView, R.id.iv_notice_img);

                        if (integer < 0) {
                            ivNoticeImg.setVisibility(View.GONE);
                        } else {
                            ivNoticeImg.setVisibility(View.VISIBLE);
                            ivNoticeImg.setImageResource(imgId);
                        }

                        tvNoticeMsg.setText(msg);
                        return Observable.just(toastView);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<View>() {
                    @Override
                    public void call(View view) {
                        if (sToast == null) {
                            sToast = new Toast(mContext);
                            sToast.setGravity(Gravity.CENTER, 0, 0);
                            sToast.setDuration(Toast.LENGTH_SHORT);
                        }
                        sToast.setView(view);
                        sToast.show();
                    }
                });
        }

}
