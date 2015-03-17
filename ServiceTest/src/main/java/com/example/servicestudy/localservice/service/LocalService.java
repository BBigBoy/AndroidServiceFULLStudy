package com.example.servicestudy.localservice.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class LocalService extends Service {
    private String TAG = getClass().getSimpleName();
    MyBinder myBinder = new MyBinder();
    ServiceListener myServiceListener;

    public LocalService() {
    }

    public interface ServiceListener {
        public String getActivityInfo();
    }

    private void setListener(ServiceListener myServiceListener) {
        this.myServiceListener = myServiceListener;
    }

    //绑定成功后，Service就可以通过这个方法获得Activity的信息
    private void getActivityInfo() {
        String activityInfo = myServiceListener.getActivityInfo();
        Log.d(TAG, TAG + "+activityInfo------>" + activityInfo);
    }

    private String getInfo() {
        return "Hello,我是LocalService的方法，你可以通过它的对象访问我！";
    }

    public class MyBinder extends Binder {
        public String getServiceInfo() {
            return getInfo();
        }

        public void setServiceListener(ServiceListener myServiceListener) {
            setListener(myServiceListener);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, TAG + "------>onBind()");
        return myBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, TAG + "------>onRebind()");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, TAG + "------>onUnbind()");
        //return false;这里的返回值决定下一次绑定时是否执行onRebind
        return true;
    }
}
