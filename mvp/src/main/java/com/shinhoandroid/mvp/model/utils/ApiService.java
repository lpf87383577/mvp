package com.shinhoandroid.mvp.model.utils;

import com.shinhoandroid.mvp.model.YmUrls;
import com.shinhoandroid.mvp.model.bean.BaseResult;
import com.shinhoandroid.mvp.model.bean.DataObj;


import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author Liupengfei
 * @describe TODO
 * @date on 2019/5/30 15:43
 */

public interface ApiService {

    @GET(YmUrls.GET_APPPAGES_LAYOUT_URL)
    Observable<BaseResult<DataObj>> getApppageCustomData2(@QueryMap Map<String, String> queryMap);


}

