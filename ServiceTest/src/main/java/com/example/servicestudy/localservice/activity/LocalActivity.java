package com.example.servicestudy.localservice.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import com.example.servicestudy.localservice.service.LocalService;
import com.example.servicestudy.R;

public class LocalActivity extends ActionBarActivity {
    private String TAG = getClass().getSimpleName();
    Intent serviceIntent;
    LocalService.MyBinder myBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
        Log.d(TAG, TAG + "------>onCreate()");
        serviceIntent = new Intent(this, LocalService.class);
    }

    public void bindService(View v) {
        Log.d(TAG, TAG + "------>bindService()");
        bindService(serviceIntent, serviceConnection, Service.BIND_AUTO_CREATE);
    }

    public void unBindService(View v) {
        Log.d(TAG, TAG + "------>unBindService()");
        unbindService(serviceConnection);
    }

    public void getServiceInfo(View v) {
        String localServiceInfo = myBinder.getServiceInfo();
        Log.d(TAG, TAG + "+getServiceInfo------>" + localServiceInfo);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, TAG + "------>onDestroy()");
        super.onDestroy();
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, TAG + "------>onServiceConnected()");
            myBinder = (LocalService.MyBinder) service;
            myBinder.setServiceListener(new LocalService.ServiceListener() {
                @Override
                public String getActivityInfo() {
                    return "Hello,我在LocalActivity中，LocalService可以调用我获得LocalActivity的消息！";
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, TAG + "------>onServiceDisconnected()");
        }
    };
}
