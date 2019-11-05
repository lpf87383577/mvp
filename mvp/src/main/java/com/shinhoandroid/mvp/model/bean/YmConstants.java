package com.shinhoandroid.mvp.model.bean;

public interface YmConstants {

    String APK_NAME = "richangshishi.apk";

    /**
     * 网络请求用到的key value
     */
    String ANDROID = "android";
    String PLATFORM = "platform";
    String VERSION = "version";
    String FIRSTRUN = "firstRun";

    //更新类型

    /**
     *不需要更新
     */
    String UPGRADE_ACTION_NONE = "none";
    /**
     *选择更新
     */
    String UPGRADE_ACTION_OPTIONAL = "optional";
    /**
     *强制更新
     */
    String UPGRADE_ACTION_HAVETO = "haveTo";
    /**
     *一直显示更新
     */
    String UPGRADE_ACTION_SHOW_EVERYTIME = "showsEverytime";

}
