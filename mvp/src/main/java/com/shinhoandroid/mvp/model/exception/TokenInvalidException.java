package com.shinhoandroid.mvp.model.exception;


import com.shinhoandroid.mvp.model.bean.BaseResult;

/**
 * token失效的错误
 * <p>
 * @author Created by gsd on 2017/1/19.
 */
public class TokenInvalidException extends RuntimeException {
    private String status;
    private String status_txt;

    public TokenInvalidException() {

    }

    public TokenInvalidException(BaseResult baseResult) {
        this.status = baseResult.status;
        this.status_txt = baseResult.status_txt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_txt() {
        return status_txt;
    }

    public void setStatus_txt(String status_txt) {
        this.status_txt = status_txt;
    }
}
