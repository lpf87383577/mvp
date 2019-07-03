package com.shinhoandroid.mvp.model.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author Created by gsd on 2017/2/15.
 */

public class NetUtils {

    /**
     * 网络状态枚举
     */
    public enum NetState {
        //无网络
        NET_NO,
        NET_2G,
        NET_3G,
        NET_4G,
        NET_WIFI,
        //未知
        NET_UNKNOWN
    }

    /**
     * 获取网络连接状态
     *
     * @return
     */
    public static NetState getNetState() {

        NetState state = NetState.NET_NO;

        ConnectivityManager cm = (ConnectivityManager) AppUtils.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            switch (networkInfo.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    state = NetState.NET_WIFI;
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    switch (networkInfo.getSubtype()) {
                        //联通2g
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                            //电信2g
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                            //移动2g
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            state = NetState.NET_2G;
                            break;
                        //电信3g
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            state = NetState.NET_3G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            state = NetState.NET_4G;
                            break;
                        default:
                            state = NetState.NET_UNKNOWN;
                    }
                    break;
                default:
                    state = NetState.NET_UNKNOWN;
            }
        }
        return state;
    }

    /**
     * 网络状态是否可用
     *
     * @return
     */
    public static boolean isNetworkAvailable() {

        ConnectivityManager cm = (ConnectivityManager) AppUtils.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo[] info = cm.getAllNetworkInfo();

            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * wifi是否打开
     *
     * @return
     */
    public static boolean isWifiEnabled() {

        Context appContext = AppUtils.getAppContext();
        ConnectivityManager cm = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager tm = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);

        return (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED)
                || tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS;
    }

    /**
     * 判断当前网络是否是wifi
     *
     * @return
     */
    public static boolean isWifi() {
        return getNetState() == NetState.NET_WIFI;
    }

    /**
     * 判断是否是手机连接
     */
    public static boolean isMobile(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null || cm.getActiveNetworkInfo() == null) {
            return false;
        } else {
            return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE;
        }
    }

    /**
     * 获取手机ip
     *
     * @return
     */
    public static String getPhoneIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        // if (!inetAddress.isLoopbackAddress() && inetAddress
                        // instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 对表单提交的数据进行判空处理
     *
     * @param object
     * @return
     */
    public static String postNullDeal(Object object) {
        if (object == null) {
            return "";
        }
        return String.valueOf(object);
    }

}
