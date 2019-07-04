package com.shinhoandroid.mvp.view;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.shinhoandroid.mvp.R;
import com.shinhoandroid.mvp.model.bean.ApiEvent;
import com.shinhoandroid.mvp.model.subscriber.SubscriberNet;
import com.shinhoandroid.mvp.model.utils.AdaptScreenUtils;
import com.shinhoandroid.mvp.model.utils.L;
import com.shinhoandroid.mvp.model.utils.ToastUtils;
import com.shinhoandroid.mvp.presenter.MvpBasePresenter;
import com.shinhoandroid.mvp.view.custom.Loading;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Activity抽象类
 * @author gsd
 * @param <V>
 * @param <P>
 */
public abstract class MvpBaseActivity<V extends MvpBaseView, P extends MvpBasePresenter<V>> extends FragmentActivity implements UiInterface {

    private P presneter;
    private V view;
    public MvpBaseActivity mActivity;

    private Loading mLoadingDialog;

    public EventBus mEventBus;
    /**
     * 是否适应statusbar高度,默认true,特殊处理改写此字段
     */
    protected boolean fitSystemWindow = true;

    private FrameLayout mFrameLayout;

    public View activityView;

    //当前Activity的显示状态1显示2不显示
    int activityStatus = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = this;

        mEventBus = EventBus.getDefault();

        if (this.presneter == null) {
            //创建P层
            this.presneter = createPresneter();
        }

        if (this.view == null) {
            //创建V层
            this.view = createView();
        }
        //判定是否为空
        if (this.presneter == null) {
            throw new NullPointerException("presneter不能够为空");
        }
        if (this.view == null) {
            throw new NullPointerException("view不能够为空");
        }
        //绑定
        this.presneter.attachView(this.view);

        View contenView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.activity_content_base_mvp, null);

        mFrameLayout = (FrameLayout) contenView.findViewById(R.id.fl_content);

        if (setContentId() == 0) {

            return;
        }

        activityView = (ViewGroup) LayoutInflater.from(this).inflate(setContentId(), null);

        mFrameLayout.addView(activityView);

        //装载布局
        setContentView(contenView);

        //获取View
        ButterKnife.bind(this);

        //初始化界面的数据
        initData();

        initListener();
        //加载数据
        loadData();

    }

    /**
     * //并不知道具体的P是哪一个实现类，由他的子类决定(BaseActivity子类决定具体类型)
     *
     * @return
     */
    public abstract P createPresneter();

    /**
     * //并不知道具体的V是哪一个实现类，由他的子类决定(BaseActivity子类决定具体类型)
     *
     * @return
     */
    public abstract V createView();

    @Override
    protected void onDestroy() {
        if (this.presneter != null) {
            this.presneter.detachView();
        }
        super.onDestroy();
    }

    public P getPresneter() {
        return presneter;
    }

    public void dismissLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    public synchronized void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new Loading(this);
            mLoadingDialog.setCanceledOnTouchOutside(false);
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        activityStatus = 2;
        //解除EventBus
        mEventBus.unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityStatus = 1;
        //注册EventBUs
        if (!mEventBus.isRegistered(this)) {

            mEventBus.register(this);
        }
    }

    //特殊API的回调比如需要弹框
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceSpec(ApiEvent event) {

        L.e("lpf--"+event.status+"--"+event.status_txt);


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveLoading(SubscriberNet.LoadingEvent event) {

        if (event.show) {
            //不可见时不执行方法
            if (activityStatus == 2){
                return;
            }
            showLoading();
        } else {
            dismissLoading();
        }
    }

    //适配布局
    @Override
    public Resources getResources() {
        //xml 文件中使用pt为宽高单位
        return AdaptScreenUtils.adaptWidth(super.getResources(), 1050);
    }
}
