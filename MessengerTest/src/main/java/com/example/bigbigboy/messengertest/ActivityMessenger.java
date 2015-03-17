package com.example.bigbigboy.messengertest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ActivityMessenger extends Activity {
    /**
     * Messenger for communicating with the service.
     */
    Messenger messengerToService = null;
    /**
     * Flag indicating whether we have called bind on the service.
     */
    boolean mBound;
    private String TAG = getClass().getSimpleName();
    /**
     * Command to the service to display a message
     */
    static final int MSG_SAY_HELLO = 1;
    static final int MSG_SEND_MESSENGER_TO_SERVICE = 2;
    static final int MSG_FROM_SERVICE = 3;

    /**
     * Class for interacting with the main interface of the service.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the object we can use to
            // interact with the service.  We are communicating with the
            // service using a Messenger, so here we get a client-side
            // representation of that from the raw IBinder object.
            Log.d(TAG, TAG + "------>onServiceConnected()");
            messengerToService = new Messenger(service);
            mBound = true;
            Message msg = Message.obtain(null, MSG_SEND_MESSENGER_TO_SERVICE, 0, 0);
            msg.replyTo = activityMessenger;
            try {//Messenger对象发送消息，这个msg对象将交给Service类中的handleMessage处理
                messengerToService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            Log.d(TAG, TAG + "------>onServiceDisconnected()");
            messengerToService = null;
            mBound = false;
        }
    };

    /**
     * Handler of incoming messages from service.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, TAG + "------>handleMessage()");
            switch (msg.what) {
                case MSG_FROM_SERVICE:
                    Log.d(TAG, TAG + "+MSG_FROM_SERVICE------>Activity收到Service回复的消息!");
                    Toast.makeText(getApplicationContext(), "MSG_FROM_SERVICE!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /**
     * Target we publish for service to send messages to IncomingHandler.
     */
    final Messenger activityMessenger = new Messenger(new IncomingHandler());

    public void sayHello(View v) {
        Log.d(TAG, TAG + "------>sayHello()");
        if (!mBound) return;
        // Create and send a message to the service, using a supported 'what' value
        Message msg = Message.obtain(null, MSG_SAY_HELLO, 0, 0);
        try {//Messenger对象发送消息，这个msg对象将交给Service类中的handleMessage处理
            messengerToService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, TAG + "------>onCreate()");
    }

    @Override
    protected void onStart() {
        Log.d(TAG, TAG + "------>onStart()");
        super.onStart();
        // Bind to the service
        bindService(new Intent("com.example.servicestudy.remoteservie.MessengerService"), mConnection,
                Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        Log.d(TAG, TAG + "------>onStop()");
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }
}
