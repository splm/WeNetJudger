package me.splm.app.wenetjudger.helper;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({NetType.AUTO, NetType.WIFI, NetType.CMNET, NetType.CMWAP, NetType.NONE})
public @interface NetType {
    //有网络，包括Wifi/gprs
    String AUTO = "AUTO";
    //wifi
    String WIFI = "WIFI";
    //PC/笔记本/PDA
    String CMNET = "CMNET";
    //手机端
    String CMWAP = "CMWAP";
    //没有网络
    String NONE = "NONE";
}
