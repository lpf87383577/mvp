package com.shinhoandroid.mvp.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gsd on 2017/1/19.
 */
public class BaseResult<T> extends TokenResult implements Serializable {

    public String status;
    public String status_txt;
    public String time;
    public String time_taken;
    public String sign_pass;
    public String right_sign;
    public List<?> links;
    public ArrayList<T> results;


    public String data_type;

}
