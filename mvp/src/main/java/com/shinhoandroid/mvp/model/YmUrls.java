package com.shinhoandroid.mvp.model;

/**
 * @author Liupengfei
 * @describe TODO
 * @date on 2019/5/30 14:42
 */

public class YmUrls {

    static boolean isDebug = ShinhoConfig.isDebug;

    public static final String GLOABL_URL = isDebug ? "https://ec-api.shinho.net.cn" : "https://api.shinshop.com";

    public static final String GET_APPPAGES_LAYOUT_URL = "/v1/apppages/layout";

    /**
     * 获取app应用版本
     */
    public static final String GET_UPGRADE_INFOR = "/v1" + "/defaults/upgrade";

}
