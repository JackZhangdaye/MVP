package com.zk.mvp.base;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//发送消息通知其它activity做一些操作
//使用单例模式
public class ListenerManager {
    private static ListenerManager listenerManager;

    //把activity装在一起
    private List<BaseListener> listeners = new CopyOnWriteArrayList<BaseListener>();

    public static synchronized ListenerManager getInstance(){
        if(listenerManager == null) {
            synchronized (ListenerManager.class){
                if (listenerManager == null) {
                    listenerManager = new ListenerManager();
                }
            }
        }
        return listenerManager;
    }

    //注册监听
    public void registerListener(BaseListener iListener) {
        listeners.add(iListener);
    }

    //注销监听
    public void loginOutListener(BaseListener iListener) {
        if(listeners.contains(iListener)){
            listeners.remove(iListener);
        }
    }

    //发送广播
    public void sendBroadCast(String str) {
        for (BaseListener iListener : listeners) {
            iListener.notifyAllActivity(str);
        }
    }

}
