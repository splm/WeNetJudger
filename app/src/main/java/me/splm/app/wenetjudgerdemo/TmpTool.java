package me.splm.app.wenetjudgerdemo;

import android.util.Log;

import me.splm.app.wenetjudger.helper.NetType;
import me.splm.app.wenetjudger.processor.WeNetManager;
import me.splm.app.wenetjudger.processor.WeNetJudger;

public class TmpTool {
    public TmpTool() {
        WeNetManager.getDefault().registerObserver(this);
    }

    @WeNetJudger(limit = NetType.NETWORK_3G, tag = "test")
    public void test(String name,String parentId) {
        Log.e("***********", "test:"+name);
        Log.e("***********", "test:id"+parentId);
    }
}
