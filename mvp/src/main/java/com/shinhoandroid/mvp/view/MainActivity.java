package com.shinhoandroid.mvp.view;


import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jakewharton.rxbinding.view.RxView;
import com.shinhoandroid.mvp.R;
import com.shinhoandroid.mvp.model.bean.DataObj;
import com.shinhoandroid.mvp.model.utils.L;
import com.shinhoandroid.mvp.presenter.MainPresenter;
import com.shinhoandroid.mvp.view.viewinterface.MainView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;


public class MainActivity extends MvpBaseActivity<MainView,MainPresenter> implements MainView{

    @BindView(R.id.tv_response)
    TextView mTvResponse;
    @BindView(R.id.bt)
    Button mBt;


    @Override
    public MainPresenter createPresneter() {
        return new MainPresenter();
    }

    @Override
    public MainView createView() {
        return this;
    }

    @Override
    public int setContentId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void initListener() {

        RxView.clicks(mBt)
                .throttleFirst(500, TimeUnit.MICROSECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {

                        //获取数据
                        getPresneter().getData();
                    }
                });

    }

    @Override
    public void getDataSuccess(DataObj obj) {
        L.e("lpf--getDataSuccess");
        mTvResponse.setText(new Gson().toJson(obj));
    }
}
