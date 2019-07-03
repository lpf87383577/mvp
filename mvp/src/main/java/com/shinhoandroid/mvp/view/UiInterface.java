package com.shinhoandroid.mvp.view;

/**
 * @author gsd
 */
public interface UiInterface {

    /**
     * 设置布局id
     *
     * @return
     */
    int setContentId();

    /**
     * 初始化数据
     */
    void initData();


    /**
     * 加载数据
     */
    void loadData();

    /**
     * initListener
     */
    void initListener();
}
