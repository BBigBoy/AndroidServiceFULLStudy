package com.example.bigbigboy.servicelifecycle;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ServiceLife extends Service {
    private String TAG = getClass().getSimpleName();
    MyBinder myBinder=new MyBinder();
    public ServiceLife() {
    }

    public class MyBinder extends Binder {
        public ServiceLife getService() {
            return ServiceLife.this;
        }
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "ServiceLife------>onCreate()");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "ServiceLife------>onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "ServiceLife------>onBind()");
        return myBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "ServiceLife------>onRebind()");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "ServiceLife------>onUnbind()");
        return false;
        //return true;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "ServiceLife------>onDestroy()");
        super.onDestroy();
    }
}
