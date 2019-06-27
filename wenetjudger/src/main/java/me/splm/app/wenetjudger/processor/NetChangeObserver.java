package me.splm.app.wenetjudger.processor;

public interface NetChangeObserver {
    void onConnect(String type);
    void onDisconnected();
}
