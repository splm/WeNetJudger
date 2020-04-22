package me.splm.app.wenetjudger.processor;

import java.lang.reflect.Method;

import me.splm.app.wenetjudger.helper.NetType;

public class MethodHolder {
    private String name;
    private String holderName;
    private Class<?>[] parameterTypes;
    @NetType
    private int type;
    private String tag;
    private Method method;

    public MethodHolder(String name, String holderName, String tag , @NetType int type, Method method, Class<?>[] parameterTypes) {
        this.name = name;
        this.holderName = holderName;
        this.parameterTypes = parameterTypes;
        this.tag = tag;
        this.type = type;
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
