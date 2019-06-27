package me.splm.app.wenetjudgerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import me.splm.app.wenetjudger.helper.NetType;
import me.splm.app.wenetjudger.processor.NetManager;
import me.splm.app.wenetjudger.processor.WeNetJudger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetManager.getDefault().registerObserver(this);
    }

    @WeNetJudger(NetType.WIFI)
    public void test(@NetType String type) {
        Log.e("***********", "test: " + type);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销目标广播
        NetManager.getDefault().unRegisterObserver(this);
        //注销所有广播
        NetManager.getDefault().unRegisterAllObserver();

    }
}
