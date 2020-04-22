package me.splm.app.wenetjudgerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import me.splm.app.wenetjudger.processor.INetChangedNotifier;
import me.splm.app.wenetjudger.processor.WeNetManager;
import me.splm.app.wenetjudger.processor.WeNetChanger;

@WeNetChanger
public class BaseActivity extends AppCompatActivity implements INetChangedNotifier {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WeNetManager.getDefault().registerObserver(this);
    }

    @Override
    public void onNetFound(boolean isWhat) {
        if(isWhat){
            Log.e("---------", "onNetFound: base网络可用" );
        }else{
            Log.e("---------", "onNetFound: base网络不可用" );
        }
    }
}
