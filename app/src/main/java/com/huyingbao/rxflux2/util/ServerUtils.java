package com.huyingbao.rxflux2.util;

import com.huyingbao.dm.BuildConfig;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.orhanobut.logger.Logger;

/**
 * 服务器工具类
 * Created by liujunfeng on 2017/1/1.
 */
public class ServerUtils {
    private static String DEBUG_SERVER_IN_URL = "http://10.10.1.33:10000/";
    private static String DEBUG_SERVER_OUT_URL = "http://221.130.30.148:10000/";

    private static String DEFAULT_SERVER_IN_URL = "http://10.0.10.203:10000/";
    private static String DEFAULT_SERVER_OUT_URL = "http://119.187.133.94:10000/";

    public static String getServerUrl() {
        return getServerState() ? getServerInUrl() : getServerOutUrl();
    }

    public static boolean getServerState() {
        return LocalStorageUtils.getInstance().getBoolean(ActionsKeys.SERVER_STATE, true);
    }

    public static void setServerState(boolean serverState) {
        //日志
        Logger.e("切换<" + (serverState ? "内" : "外") + ">服务器");
        //修改当前网络状态
        LocalStorageUtils.getInstance().setBoolean(ActionsKeys.SERVER_STATE, serverState);
        //修改api host
        AppUtils.getApplicationComponent().getHttpInterceptor().setBaseUrl(serverState ? getServerInUrl() : getServerOutUrl());
    }

    public static String getServerInUrl() {
        return LocalStorageUtils.getInstance().getString(ActionsKeys.SERVER_IN_URL, BuildConfig.DEBUG ? DEBUG_SERVER_IN_URL : DEFAULT_SERVER_IN_URL);
    }

    public static void setServerInUrl(String serverInHost) {
        LocalStorageUtils.getInstance().setString(ActionsKeys.SERVER_IN_URL, serverInHost);
    }

    public static String getServerOutUrl() {
        return LocalStorageUtils.getInstance().getString(ActionsKeys.SERVER_OUT_URL, BuildConfig.DEBUG ? DEBUG_SERVER_OUT_URL : DEFAULT_SERVER_OUT_URL);
    }

    public static void setServerOutUrl(String serverOutHost) {
        LocalStorageUtils.getInstance().setString(ActionsKeys.SERVER_OUT_URL, serverOutHost);
    }
}
