package me.splm.app.wenetjudger.helper;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({NetType.NETWORK_NONE, NetType.NETWORK_WIFI, NetType.NETWORK_2G, NetType.NETWORK_3G, NetType.NETWORK_4G, NetType.NETWORK_MOBILE})
public @interface NetType {
    int NETWORK_NONE = -1; // 没有网络连接
    int NETWORK_AVAILABLE = 1; // 有网络连接
    int NETWORK_2G = 2; // 2G
    int NETWORK_3G = 3; // 3G
    int NETWORK_4G = 4; // 4G
    int NETWORK_WIFI = 5; // wifi连接
    int NETWORK_MOBILE = 6; // 手机流量
}
