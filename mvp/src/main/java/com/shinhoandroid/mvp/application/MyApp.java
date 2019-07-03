package com.shinhoandroid.mvp.application;

import android.app.Application;
import android.content.Context;

import com.shinhoandroid.mvp.model.utils.AppUtils;

/**
 * @author Liupengfei
 * @describe TODO
 * @date on 2019/5/31 11:08
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.setContext(this);
    }
}
