package com.shinhoandroid.mvp.model.utils;

import android.util.Log;

import com.shinhoandroid.mvp.model.ShinhoConfig;

/**
 * 打印日志
 * 通过isDebug控制是否要打印
 * @author Created by ChinaNetPro on 2016/10/25.
 */

public class L {

    /**
     * 类名
     */
    private static String className;
    /**
     *方法名
     */
    private static String methodName;
    /**
     *行数
     */
    private static int lineNumber;
    private static boolean isDebug = ShinhoConfig.logIsDebug;

    private L() {

    }

    public static boolean isDebuggable() {
        return isDebug;
    }

    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")");
        buffer.append(log);
        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }


    public static void e(String message) {
        if (!isDebuggable()) {
            return;
        }


        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
    }


    public static void i(String message) {
        if (!isDebuggable()) {
            return;
        }

        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }

    public static void d(String message) {
        if (!isDebuggable()) {
            return;
        }

        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }

    public static void v(String message) {
        if (!isDebuggable()) {
            return;
        }

        getMethodNames(new Throwable().getStackTrace());
        Log.v(className, createLog(message));
    }

    public static void w(String message) {
        if (!isDebuggable()) {
            return;
        }

        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
    }

    public static void wtf(String message) {
        if (!isDebuggable()) {
            return;
        }

        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(className, createLog(message));
    }

    public static void longe(String log){

//        if (true){
//
//            return;
//        }
        if (!isDebuggable()) {
            return;
        }

        int showCount = 1000;

        if(log.length() >showCount){
            String show = log.substring(0, showCount);

            //剩下的文本还是大于规定长度
            if((log.length() - showCount)>showCount){
                String partLog = log.substring(showCount,log.length());
                longe(partLog);
            }else{
                String surplusLog = log.substring(showCount, log.length());
                L.e(surplusLog+"");
            }

        }else{

            L.e(log+"");
        }
    }


}
