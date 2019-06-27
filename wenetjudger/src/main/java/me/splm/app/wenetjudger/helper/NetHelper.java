package me.splm.app.wenetjudger.helper;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import me.splm.app.wenetjudger.processor.NetManager;

public class NetHelper {

    public static final String ANDROID_NET_CHANGE_ACTION="android.net.conn.CONNECTIVITY_CHANGE";

    public static boolean isNetWorkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) NetManager.getDefault().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        Network[] networks = manager.getAllNetworks();
        NetworkInfo networkInfo;
        for (Network mNetwork : networks) {
            networkInfo = manager.getNetworkInfo(mNetwork);
            if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return 网络类型
     */
    public static String getNetworkType() {
        ConnectivityManager manager = (ConnectivityManager) NetManager.getDefault().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return NetType.NONE;
        }
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NetType.NONE;
        }
        int type = networkInfo.getType();
        if (type == ConnectivityManager.TYPE_MOBILE) {
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                return NetType.CMNET;
            } else {
                return NetType.CMWAP;
            }
        } else if (type == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI;
        }
        return NetType.AUTO;
    }

    /**
     * 打开网络设置界面
     *
     * @param context
     * @param requestCode 请求跳转
     */
    public static void openNetSetting(Context context, int requestCode) {
        Intent intent = new Intent("/");
        ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cn);
        intent.setAction("android.intent.action.VIEW");
        ((Activity) context).startActivityForResult(intent, requestCode);
    }
}
