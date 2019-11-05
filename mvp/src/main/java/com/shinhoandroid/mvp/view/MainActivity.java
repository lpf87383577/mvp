package com.shinhoandroid.mvp.view;


import android.Manifest;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.rxbinding.view.RxView;
import com.shinhoandroid.mvp.R;
import com.shinhoandroid.mvp.model.bean.DataObj;
import com.shinhoandroid.mvp.model.update.AppUpdateManager;
import com.shinhoandroid.mvp.model.utils.L;
import com.shinhoandroid.mvp.presenter.MainPresenter;
import com.shinhoandroid.mvp.view.viewinterface.MainView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import rx.functions.Action1;


public class MainActivity extends MvpBaseActivity<MainView,MainPresenter> implements MainView{

    @BindView(R.id.tv_response)
    TextView mTvResponse;
    @BindView(R.id.bt)
    Button mBt;
    @BindView(R.id.bt2)
    Button mBt2;
    @BindView(R.id.bt3)
    Button mBt3;
    @BindView(R.id.bt4)
    Button mBt4;

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
        RxView.clicks(mBt2)
                .throttleFirst(500, TimeUnit.MICROSECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {

                        AppUpdateManager.getInstance().checkAppVersion(false);
                    }
                });

        RxView.clicks(mBt3)
                .throttleFirst(500, TimeUnit.MICROSECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {

                        AppUpdateManager.getInstance().checkAppVersion(true);
                    }
                });
        RxView.clicks(mBt4)
                .throttleFirst(500, TimeUnit.MICROSECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {

                        RxPermissions rxPermissions=new RxPermissions(MainActivity.this);
                        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE,Manifest.permission.INTERNET).subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean){
                                    //申请的权限全部允许
                                    Toast.makeText(MainActivity.this, "允许了权限!", Toast.LENGTH_SHORT).show();

                                }else{
                                    //只要有一个权限被拒绝，就会执行
                                    Toast.makeText(MainActivity.this, "未授权权限，部分功能不能使用", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

    }

    @Override
    public void getDataSuccess(DataObj obj) {
        L.e("lpf--getDataSuccess");
        mTvResponse.setText(new Gson().toJson(obj));
    }
}
