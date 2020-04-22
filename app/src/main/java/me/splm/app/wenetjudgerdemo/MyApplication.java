package me.splm.app.wenetjudgerdemo;

import android.app.Application;

import me.splm.app.wenetjudger.processor.WeNetManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WeNetManager.getDefault().init(this);
    }
}
