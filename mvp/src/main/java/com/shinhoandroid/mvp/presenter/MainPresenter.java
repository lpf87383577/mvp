package com.shinhoandroid.mvp.presenter;

import com.shinhoandroid.mvp.model.bean.BaseResult;
import com.shinhoandroid.mvp.model.bean.DataObj;
import com.shinhoandroid.mvp.model.exception.ApiException;
import com.shinhoandroid.mvp.model.global.ObservableHandler;
import com.shinhoandroid.mvp.model.subscriber.SubscriberNet;
import com.shinhoandroid.mvp.model.utils.ApiService;
import com.shinhoandroid.mvp.model.utils.L;
import com.shinhoandroid.mvp.model.utils.RetrofitUtils;
import com.shinhoandroid.mvp.view.viewinterface.MainView;

import java.util.HashMap;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Liupengfei
 * @describe TODO
 * @date on 2019/5/30 15:39
 */

public class MainPresenter extends MvpBasePresenter<MainView> {


    public synchronized void getData(){

        HashMap queryMap = new HashMap<>();

        queryMap.put("pageType", "main");

        Observable<BaseResult<DataObj>> observable = RetrofitUtils.retrofit().create(ApiService.class).getApppageCustomData2(queryMap);

        ObservableHandler.handle(observable).subscribe(new SubscriberNet<BaseResult<DataObj>>() {
            @Override
            public void onSuccess(BaseResult<DataObj> tResponse) {
                getView().getDataSuccess(tResponse.results.get(0));
                L.e("lpf--"+tResponse.results.size());

            }

            @Override
            public void onFailture(ApiException e) {

                L.e("lpf--onFailture--");
            }

            @Override
            public void onCallError(Throwable e) {
                L.e("lpf--onCallError--");
            }

        });


    }


}
