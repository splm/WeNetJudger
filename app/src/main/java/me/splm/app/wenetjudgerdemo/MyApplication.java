package me.splm.app.wenetjudgerdemo;

import android.app.Application;

import me.splm.app.wenetjudger.processor.NetManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetManager.getDefault().init(this);
    }
}
