package com.shinhoandroid.mvp.model.utils;

import com.shinhoandroid.mvp.model.YmUrls;
import com.shinhoandroid.mvp.model.bean.BaseResult;
import com.shinhoandroid.mvp.model.bean.DataObj;
import com.shinhoandroid.mvp.model.bean.YmUpgradeObj;


import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author Liupengfei
 * @describe TODO
 * @date on 2019/5/30 15:43
 */

public interface ApiService {

    @GET(YmUrls.GET_APPPAGES_LAYOUT_URL)
    Observable<BaseResult<DataObj>> getApppageCustomData2(@QueryMap Map<String, String> queryMap);


    /**
     * 获取更新信息
     *
     * @param queryMap
     * @return
     */
    @GET(YmUrls.GET_UPGRADE_INFOR)
    Observable<BaseResult<YmUpgradeObj>> getUpgrade(@QueryMap Map<String, String> queryMap);


    /**
     * apk下载
     *
     * @param url
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> apkDownload(@Url String url);


}

