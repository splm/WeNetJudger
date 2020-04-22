package me.splm.app.wenetjudgerdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import me.splm.app.wenetjudger.helper.NetType;
import me.splm.app.wenetjudger.processor.WeNetManager;
import me.splm.app.wenetjudger.processor.WeNetChanger;
import me.splm.app.wenetjudger.processor.WeNetJudger;

@WeNetChanger
public class SecondActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WeNetManager.getDefault().registerObserver(this);
        Button testBtn_1 = findViewById(R.id.testBtn_1);
        testBtn_1.setText("第二个界面调用");
        testBtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeNetManager.getDefault().invoke(SecondActivity.this,"test","测试名称2","测试id2");
            }
        });
    }

    @WeNetJudger(limit = NetType.NETWORK_3G, tag = "test")
    public void test(String name,String parentId) {
        Log.e("***********", "test:2"+name);
        Log.e("***********", "test:id2"+parentId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销目标广播
        WeNetManager.getDefault().unRegisterObserver(this);
    }
}
