package com.shinhoandroid.mvp.model.exception;


import com.shinhoandroid.mvp.model.bean.BaseResult;

/**
 * @author Liupengfei
 * @describe 账号注销错误
 * @date on 2019/5/29 15:12
 */

public class TokenCancellationException extends RuntimeException {
    private String status;
    private String status_txt;

    public TokenCancellationException() {

    }

    public TokenCancellationException(BaseResult baseResult) {
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
