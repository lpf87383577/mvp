package com.shinhoandroid.mvp.presenter;


import com.shinhoandroid.mvp.view.MvpBaseView;

/**
 * @author lpf
 * @param <V>
 */
public class MvpBasePresenter<V extends MvpBaseView> {

    public V view;

    public V getView() {
        return view;
    }

    public void attachView(V view){
        this.view = view;
    }

    public void detachView(){
        this.view = null;
    }


}
