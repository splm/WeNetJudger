package me.splm.app.wenetjudger.processor;

import android.app.Application;
import android.content.IntentFilter;

import me.splm.app.wenetjudger.helper.NetHelper;

public class WeNetManager {
    private static volatile WeNetManager instance;

    private NetChangeReceiver mReceiver;
    private Application mApplication;

    public WeNetManager() {
        mReceiver = new NetChangeReceiver();
    }

    public static WeNetManager getDefault() {
        if (instance == null) {
            synchronized (WeNetManager.class) {
                if (instance == null) {
                    instance = new WeNetManager();
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

    public void invoke(Object object,String tag, Object... objects) {
        mReceiver.post2(object,tag,objects);
    }


    public void logout() {
        getApplication().unregisterReceiver(mReceiver);
    }

    public void registerObserver(Object target) {
        mReceiver.registerObserver(target);
    }

    public void unRegisterObserver(Object activity) {
        mReceiver.unRegisterObserver(activity);
    }

    public void unRegisterAllObserver() {
        mReceiver.unRegisterAllObserver();
    }

}
