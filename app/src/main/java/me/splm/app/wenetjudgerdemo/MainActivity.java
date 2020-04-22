package me.splm.app.wenetjudgerdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import me.splm.app.wenetjudger.processor.WeNetManager;
import me.splm.app.wenetjudger.processor.WeNetChanger;

@WeNetChanger
public class MainActivity extends BaseActivity {

    private TmpTool tmpTool = new TmpTool();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //WeNetManager.getDefault().registerObserver(this);
        Button testBtn_1 = findViewById(R.id.testBtn_1);
        testBtn_1.setText("第一个界面调用");
        Button testBtn_2 = findViewById(R.id.testBtn_2);
        testBtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeNetManager.getDefault().invoke(tmpTool,"test","测试名称","测试id");
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
