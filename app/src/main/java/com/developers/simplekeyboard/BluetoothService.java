package com.developers.simplekeyboard;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.support.annotation.Nullable;
import android.util.Log;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import com.developers.simplekeyboard.SimpleIME;

public class BluetoothService extends Service {


    BluetoothSPP bt;
    SimpleIME simpleIME;

    public BluetoothService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        simpleIME=new SimpleIME();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bt=Data.getBt();
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] data, String message) {
                Log.d("BluetoothService","Recieving "+message);
                simpleIME.printLine(message);
                //int arr[] = {68,69};
                //simpleIME.onKey(65,arr);
            }
        });
        return Service.START_STICKY;
    }

    private final BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent keyBoard=new Intent(context,SimpleIME.class);

        }
    };

}
