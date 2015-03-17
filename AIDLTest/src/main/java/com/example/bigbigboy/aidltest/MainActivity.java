package com.example.bigbigboy.aidltest;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.example.servicestudy.AIDLActivity;
import com.example.servicestudy.AIDLService;


public class MainActivity extends Activity {
    private String TAG = getClass().getSimpleName();
    AIDLService aidlBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, TAG + "------>onStart()");
        bindService(new Intent("com.example.servicestudy.remoteservie.aidl.AIDLService"), serviceConnection, Service.BIND_AUTO_CREATE);
        super.onStart();
    }

    public void invokServiceMethod(View v) {
        Log.d(TAG, TAG + "------>invokServiceMethod()");
        try {
            aidlBinder.invokCallBack();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        Log.d(TAG, TAG + "------>onStop--->unbindService()");
        unbindService(serviceConnection);
        super.onStop();
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        synchronized public void onServiceConnected(ComponentName name, IBinder service) {
            aidlBinder = AIDLService.Stub.asInterface(service);
            Log.d(TAG, TAG + "------>onServiceConnected()");
            try {
                aidlBinder.registerTestCall(new AIDLActivity.Stub() {
                    @Override
                    public void performAction() throws RemoteException {
                        Log.d(TAG, TAG + "+performAction------>Hello,我是在客户端中实现的代码，通过回调机制，Service可以调用我！");
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
