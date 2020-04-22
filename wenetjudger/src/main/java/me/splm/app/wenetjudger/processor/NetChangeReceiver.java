package me.splm.app.wenetjudger.processor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.splm.app.wenetjudger.helper.NetHelper;
import me.splm.app.wenetjudger.helper.NetType;

public class NetChangeReceiver extends BroadcastReceiver {
    private Map<Object, NetChangeProcessor> netChangeProcessorMap = new HashMap<>();
    NetChangeProcessor mProcessor;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        if (intent.getAction().equalsIgnoreCase(NetHelper.ANDROID_NET_CHANGE_ACTION)) {
            boolean isNetWorked = NetHelper.isNetWorkAvailable();
            mProcessor.post(isNetWorked);
        }
    }

    public void post2(Object object, String tag, Object... objects) {
        NetChangeProcessor processor = netChangeProcessorMap.get(object);
        if (processor != null) {
            processor.post2(object, tag, objects);
        }
    }

    public void registerObserver(Object register) {
        //获取当前Activity or Fragment中所有的网络监听注解方法
        mProcessor = netChangeProcessorMap.get(register);
        if (mProcessor == null) {
            mProcessor = new NetChangeProcessor();
            netChangeProcessorMap.put(register, mProcessor);
        }
        mProcessor.registerObserver(register);
    }

    public void unRegisterObserver(Object register) {
        mProcessor.unRegisterObserver(register);
    }

    public void unRegisterAllObserver() {
        mProcessor.unRegisterAllObserver();
    }
}
