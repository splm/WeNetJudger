package me.splm.app.wenetjudgerdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import me.splm.app.wenetjudger.helper.NetHelper;
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
        final TextView test_type = findViewById(R.id.test_type);
        test_type.setText(NetHelper.getNetworkTypeTag());
        Button testBtn_1 = findViewById(R.id.testBtn_1);
        testBtn_1.setText("第二个界面调用");
        testBtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test_type.setText(NetHelper.getNetworkTypeTag());
                WeNetManager.getDefault().invoke(SecondActivity.this,"test2","测试名称2","测试id2");
            }
        });
        Button testBtn_2 = findViewById(R.id.testBtn_2);
        testBtn_2.setText("关闭");
        testBtn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @WeNetJudger(limit = NetType.NETWORK_3G, tag = "test2")
    public void test(String name,String parentId) {
        Toast.makeText(this, "方法Tag：test"+"参数1："+name+",参数2："+parentId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销目标广播
        WeNetManager.getDefault().unRegisterObserver(this);
    }
}
