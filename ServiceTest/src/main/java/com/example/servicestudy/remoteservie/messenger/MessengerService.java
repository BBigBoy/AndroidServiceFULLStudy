package com.example.servicestudy.remoteservie.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class MessengerService extends Service {
    /**
     * Command to the service to display a message
     */
    static final int MSG_SAY_HELLO = 1;
    static final int MSG_GET_CLIENT_MESSENGER = 2;
    static final int MSG_FROM_SERVICE = 3;

    private String TAG = getClass().getSimpleName();
    Messenger messengerToClient;

    /**
     * Handler of incoming messages from clients.
     */
    class ServiceIncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, TAG + "------>handleMessage()");
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    Log.d(TAG, "handleMessage------>MSG_SAY_HELLO!");
                    Toast.makeText(getApplicationContext(), "hello!", Toast.LENGTH_SHORT).show();
                    break;
                case MSG_GET_CLIENT_MESSENGER:
                    Log.d(TAG, "handleMessage------>Service收到Activity的messenger对象!");
                    //此处获得可向客户端发送消息的Messenger对象
                    messengerToClient = msg.replyTo;
                    Message serviceMsg = Message.obtain(null, MSG_FROM_SERVICE, 0, 0);
                    try {//向客户端发送消息
                        messengerToClient.send(serviceMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /**
     * 将这个serviceMessenger发送给客户端，客户端就可以通过它联系Service了
     */
    final Messenger serviceMessenger = new Messenger(new ServiceIncomingHandler());

    /**
     * When binding to the service, we return an interface to our messenger
     * for sending messages to the service.
     */
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, TAG + "------>onBind()");
        Toast.makeText(getApplicationContext(), "binding", Toast.LENGTH_SHORT).show();
        return serviceMessenger.getBinder();
    }
}

