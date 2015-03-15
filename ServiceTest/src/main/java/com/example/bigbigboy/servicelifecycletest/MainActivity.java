package com.example.bigbigboy.servicelifecycletest;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import com.example.bigbigboy.servicelifecycle.ServiceLife;


public class MainActivity extends ActionBarActivity {
    private String TAG = getClass().getSimpleName();
    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "MainActivity------>onCreate()");
        serviceIntent = new Intent(this,ServiceLife.class);
    }

    public void startService(View v) {
        Log.d(TAG, "MainActivity------>startService()");
        startService(serviceIntent);
    }

    public void stopService(View v) {
        Log.d(TAG, "MainActivity------>stopService()");
        stopService(serviceIntent);
    }

    public void bindService(View v) {
        Log.d(TAG, "MainActivity------>bindService()");
        bindService(serviceIntent, serviceConnection, Service.BIND_AUTO_CREATE);
    }

    public void unBindService(View v) {
        Log.d(TAG, "MainActivity------>unBindService()");
        unbindService(serviceConnection);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "MainActivity------>onDestroy()");
        super.onDestroy();
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "MainActivity------>onServiceConnected()");
            ServiceLife.MyBinder binder = (ServiceLife.MyBinder) service;
            ServiceLife serviceLife = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "MainActivity------>onServiceDisconnected()");
        }
    };
}
