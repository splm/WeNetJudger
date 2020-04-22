package me.splm.app.wenetjudger.processor;

import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.splm.app.wenetjudger.helper.NetHelper;
import me.splm.app.wenetjudger.helper.NetType;

public class NetChangeProcessor {
    private List<INetChangedNotifier> mClassLists = new ArrayList<>();
    private Map<Object, List<MethodHolder>> mNetworkLists = new HashMap<>();
    private List<MethodHolder> mMethodLists = new ArrayList<>();

    public void registerObserver(Object register) {
        //获取当前Activity or Fragment中所有的网络监听注解方法
        mMethodLists = mNetworkLists.get(register);
        if (mMethodLists == null) {
            mMethodLists = findAnnotationMethods(register);
            mNetworkLists.put(register, mMethodLists);
        }
        mClassLists = findAnnotationClasses(register);
    }

    public void post2(Object holder, String tag, Object... objects) {
        boolean isContain = mNetworkLists.containsKey(holder);
        if (isContain) {
            List<MethodHolder> list = mNetworkLists.get(holder);
            if (list != null) {
                for (MethodHolder methodHolder : list) {
                    String t = methodHolder.getTag();
                    if (TextUtils.equals(tag, t)) {
                        int mt = methodHolder.getType();
                        int ct = NetHelper.getNetworkType();
                        if (mt <= ct) {
                            invoke(methodHolder, holder, objects);
                        }else{
                            if (ct == NetType.NETWORK_NONE) {
                                //TODO 当没有网络时执行
                                if(holder instanceof INetChangedNotifier){
                                    ((INetChangedNotifier) holder).onNetFound(false);
                                }
                                return;
                            }
                            //TODO 当网络类型不匹配时执行
                            Log.e("*********", "post2: 网络类型不匹配最低要求");
                        }
                    }
                }
            }
        } else {
            throw new UnsupportedOperationException("没有可以调用的方法");
        }
    }

    private void invoke(MethodHolder methodHolder, Object holder, Object... objects) {
        try {
            methodHolder.getMethod().invoke(holder, objects);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void post(boolean isNetWorked) {
        for (INetChangedNotifier notifier : mClassLists) {
            notifier.onNetFound(isNetWorked);
        }
    }

    private List<INetChangedNotifier> findAnnotationClasses(Object register) {
        List<INetChangedNotifier> clazzList = new ArrayList<>();
        Class<?> clazz = register.getClass();
        WeNetChanger annotation = clazz.getAnnotation(WeNetChanger.class);
        if (annotation != null) {

            Class<?>[] interfaces = clazz.getSuperclass().getInterfaces();
            for (Class<?> c : interfaces) {
                if (c == INetChangedNotifier.class) {
                    clazzList.add((INetChangedNotifier) register);
                }
            }
        }
        return clazzList;
    }

    private List<MethodHolder> findAnnotationMethods(Object register) {
        List<MethodHolder> methodList = new ArrayList<>();
        Class<?> clazz = register.getClass();
        Method[] method = clazz.getMethods();
        //遍历方法
        for (Method m : method) {
            //找出所有注解方法
            WeNetJudger annotation = m.getAnnotation(WeNetJudger.class);
            if (annotation == null) {
                continue;
            }
            //判断返回类型
            Type genericReturnType = m.getGenericReturnType();
            if (!"void".equals(genericReturnType.toString())) {
                throw new RuntimeException(m.getName() + "返回类型必须是void");
            }
            //参数校验
            Class<?>[] parameterTypes = m.getParameterTypes();
            MethodHolder methodHolder = new MethodHolder(m.getName(), clazz.getSimpleName(), annotation.tag(), annotation.limit(), m, parameterTypes);
            methodList.add(methodHolder);
        }
        return methodList;
    }

    public void unRegisterObserver(Object register) {
        if (!mNetworkLists.isEmpty()) {//说明有广播被注册过
            mNetworkLists.remove(register);
        }
    }

    public void unRegisterAllObserver() {
        if (!mNetworkLists.isEmpty()) {//说明有广播被注册过
            mNetworkLists.clear();
        }
        WeNetManager.getDefault().logout();//注销
    }
}
