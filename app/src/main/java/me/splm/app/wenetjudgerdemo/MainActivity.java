package me.splm.app.wenetjudgerdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.splm.app.wenetjudger.helper.NetHelper;
import me.splm.app.wenetjudger.processor.WeNetManager;
import me.splm.app.wenetjudger.processor.WeNetChanger;

@WeNetChanger
public class MainActivity extends BaseActivity {
    private TmpTool tmpTool = new TmpTool(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView test_type = findViewById(R.id.test_type);
        test_type.setText(NetHelper.getNetworkTypeTag());
        Button testBtn_1 = findViewById(R.id.testBtn_1);
        testBtn_1.setText("第一个界面调用");
        Button testBtn_2 = findViewById(R.id.testBtn_2);
        testBtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test_type.setText(NetHelper.getNetworkTypeTag());
                WeNetManager.getDefault().invoke(tmpTool,"test1","测试名称1","测试id1");
            }
        });
        testBtn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销目标广播
        WeNetManager.getDefault().unRegisterObserver(this);
        //注销所有广播
        WeNetManager.getDefault().unRegisterAllObserver();
    }
}
