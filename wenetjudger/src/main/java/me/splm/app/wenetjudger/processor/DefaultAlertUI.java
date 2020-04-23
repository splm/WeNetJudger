package me.splm.app.wenetjudger.processor;


import android.content.Context;
import android.widget.Toast;

public class DefaultAlertUI implements IAlertUI {
    private Context context;

    public DefaultAlertUI(Context context) {
        this.context = context;
    }

    @Override
    public void alert() {
        Toast.makeText(context, "网络状态不佳", Toast.LENGTH_SHORT).show();
    }
}
