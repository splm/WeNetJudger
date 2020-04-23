package me.splm.app.wenetjudgerdemo;

import android.content.Context;
import android.widget.Toast;

import me.splm.app.wenetjudger.helper.NetType;
import me.splm.app.wenetjudger.processor.WeNetJudger;
import me.splm.app.wenetjudger.processor.WeNetManager;

public class TmpTool {
    private Context context;
    public TmpTool(Context context) {
        this.context = context;
        WeNetManager.getDefault().registerObserver(this);
    }

    @WeNetJudger(limit = NetType.NETWORK_3G, tag = "test1")
    public void test(String name,String parentId) {
        Toast.makeText(context, "方法Tag：test"+"参数1："+name+",参数2："+parentId, Toast.LENGTH_SHORT).show();
    }
}
