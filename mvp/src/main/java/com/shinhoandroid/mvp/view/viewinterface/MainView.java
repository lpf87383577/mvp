package com.shinhoandroid.mvp.view.viewinterface;

import com.shinhoandroid.mvp.model.bean.DataObj;
import com.shinhoandroid.mvp.view.MvpBaseView;

/**
 * @author Liupengfei
 * @describe TODO
 * @date on 2019/5/30 15:38
 */

public interface MainView extends MvpBaseView{

    public void getDataSuccess(DataObj obj);

}
