package com.shinhoandroid.mvp.model.bean;

/**
 * @author Liupengfei
 * @describe TODO
 * @date on 2019/5/31 11:37
 */

public class ApiEvent {

    public String status;

    public String status_txt;

    public ApiEvent(String status, String status_txt) {
        this.status = status;
        this.status_txt = status_txt;
    }
}
