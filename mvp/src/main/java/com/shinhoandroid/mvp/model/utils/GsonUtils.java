package com.shinhoandroid.mvp.model.utils;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * Json解析工具类
 * @author Created by Administrator on 2016/8/18.
 */
public class GsonUtils {

    /**
     * 直接类型解析
     *
     * @param json
     * @param clz
     * @return
     */
    public static Object parseJson2Obj(String json, Class clz) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, clz);
    }

    /**
     * 带泛型等的解析
     * Type type = new TypeToken<BaseResult<YMApppageResultObj>>() {
     * }.getType();
     *
     * @param json
     * @param type
     * @return
     */
    public static Object parseJson2Obj(String json, Type type) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Gson gson = new Gson();

        return gson.fromJson(json, type);
    }

    /**
     * @param obj
     * @return
     */
    public static String parseObj2Json(Object obj) {
        String toJson = new Gson().toJson(obj);
        return toJson;
    }

    /**
     * 解析arrayJson
     *
     * @param jsonString
     * @param clz
     * @param <T>        GsonUtils.<YMUserPamsObj[]>parseJson2Array(memberProfileObj.pamsJson, YMUserPamsObj[].class);
     * @return
     */
    public static <T> List parseJson2Array(String jsonString, Class clz) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        T[] arr = (T[]) new Gson().fromJson(jsonString, clz);
        if (!CollectionUtils.isEmpty(arr)) {
            return Arrays.asList(arr);
        }
        return null;
    }
//    public static ArrayList<Object> parseJson2Array(String json) {
//        Type listType = new TypeToken<ArrayList<Object>>() {
//        }.getType();
//        ArrayList<Object> arrayList = new Gson().fromJson(json, listType);
//
//        return arrayList;
//    }
}
