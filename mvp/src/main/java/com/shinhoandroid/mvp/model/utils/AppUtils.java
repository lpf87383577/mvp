package com.shinhoandroid.mvp.model.utils;


import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.util.List;

/**
 * @author gsd 2016/8/15.
 */
public class AppUtils {

    private static Application mContext;

    /**
     * 获取全局上下文工具类
     *
     * @return
     */
    public static Context getAppContext() {
        return mContext;
    }

    public static void setContext(Application context) {
         mContext = context;
    }


}
