package com.shinhoandroid.mvp.model.utils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.shinhoandroid.mvp.BuildConfig;

import java.io.File;


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

    /**
     * 安装apk
     *
     * @param file
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(convertFile2Uri(file), "application/vnd.android.package-archive");

        context.startActivity(intent);
    }

    /**
     * file 转 uri
     *
     * @param file
     * @return
     */
    public static Uri convertFile2Uri(File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(AppUtils.getAppContext(), BuildConfig.APPLICATION_ID + ".fileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    /**
     * @param fileName
     * @return
     */
    public static File getApkFile(String fileName) {
        return new File(getAppContext().getExternalFilesDir("apk"), fileName);
    }

}
