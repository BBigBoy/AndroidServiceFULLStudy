package com.example.servicestudy.remoteservie.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.servicestudy.AIDLActivity;
import com.example.servicestudy.AIDLService;


/**
 * Created by BigBigBoy on 2015/3/17.
 */
public class AIDLServiceTest extends Service {
    private String TAG = getClass().getSimpleName();
    AIDLActivity aidlActivity;
    private AIDLService.Stub aidlServiceImpl = new AIDLService.Stub() {
        @Override
        public void registerTestCall(AIDLActivity cb) throws RemoteException {
            Log.d(TAG, TAG + "------>registerTestCall()");
            aidlActivity = cb;
        }

        @Override
        public void invokCallBack() throws RemoteException {
            Log.d(TAG, TAG + "------>invokCallBack()");
            aidlActivity.performAction();
        }

    };

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, TAG + "------>onBind()");
        return aidlServiceImpl;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, TAG + "------>onUnbind()");
        return super.onUnbind(intent);
    }
}
