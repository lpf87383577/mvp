package com.shinhoandroid.mvp.model.exception;


import com.shinhoandroid.mvp.model.bean.BaseResult;
import com.shinhoandroid.mvp.model.utils.GsonUtils;

/**
 * TODO 特定的api的错误
 * <p>
 * @author : gsd
 * @date : 2017/1/19.
 */
public class ApiException extends RuntimeException {
    private String status;
    private String status_Txt;
    private String resultJson;

    public ApiException(BaseResult baseResult) {
        this.resultJson = GsonUtils.parseObj2Json(baseResult.results);
        this.status = baseResult.status;
        this.status_Txt = baseResult.status_txt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_txt() {
        return status_Txt;
    }

    public void setStatus_text(String status_text) {
        this.status_Txt = status_text;
    }

    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }
}
