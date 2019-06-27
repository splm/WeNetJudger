package me.splm.app.wenetjudger.processor;

import android.app.Application;
import android.content.IntentFilter;

import me.splm.app.wenetjudger.helper.NetHelper;

public class NetManager {
    private static volatile NetManager instance;

    private NetChangeReceiver mReceiver;
    private Application mApplication;

    public NetManager() {
        mReceiver = new NetChangeReceiver();
    }

    public static NetManager getDefault() {
        if (instance == null) {
            synchronized (NetManager.class) {
                if (instance == null) {
                    instance = new NetManager();
                }
            }
        }
        return instance;
    }

    public Application getApplication() {
        if (mApplication == null) {
            throw new RuntimeException("NetworkManager.getDefault().init()没有初始化");
        }
        return mApplication;
    }

    public void init(Application application) {
        this.mApplication = application;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(NetHelper.ANDROID_NET_CHANGE_ACTION);
        mApplication.registerReceiver(mReceiver, intentFilter);
    }


    public void logout() {
        getApplication().unregisterReceiver(mReceiver);
    }

    public void registerObserver(Object activity) {
        mReceiver.registerObserver(activity);
    }

    public void unRegisterObserver(Object activity) {
        mReceiver.unRegisterObserver(activity);
    }

    public void unRegisterAllObserver() {
        mReceiver.unRegisterAllObserver();
    }

}
