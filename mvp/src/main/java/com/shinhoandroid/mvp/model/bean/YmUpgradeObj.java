package com.shinhoandroid.mvp.model.bean;

import android.text.TextUtils;


/**
 * @author Created by gsd on 2017/4/18.
 */

public class YmUpgradeObj {


    /**
     * action : none
     * lastShortVersion : 1.1.4
     * lastVersion : 5
     * title : 一米市集有新版本了
     * desc : 我们的新版本体验更好了，
     * 现在就更新吧！
     * cancelBtn : 现在还不要
     * confirmBtn : 马上更新
     * image :
     * appUrl : itms-apps://itunes.apple.com/app/id1135289579
     */

    public String action;
    public String lastShortVersion;
    public String lastVersion;
    public String title;
    public String desc;
    public String cancelBtn;
    public String confirmBtn;
    public String image;
    public String appUrl;

    /**
     * platform : ios
     * version : fdsafda
     * firstRun :
     * development : true
     */

    public String platform;
    public String version;
    public String firstRun;
    public String development;

    /**
     * 是否强制更新
     *
     * @return
     */
    public boolean isMustUpgrade() {
        //强制更新判断
        if (TextUtils.equals(action, YmConstants.UPGRADE_ACTION_HAVETO)) {
            return true;
        }
        return false;
    }

    /**
     * 是否需要更新
     *
     * @return
     */
    public boolean isNeedUpgrade() {
        if (TextUtils.equals(action, YmConstants.UPGRADE_ACTION_NONE)) {
            return false;
        }
        return true;
    }

}
