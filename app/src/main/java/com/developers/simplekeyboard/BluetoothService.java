package com.developers.simplekeyboard;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BluetoothService extends Service {

    private BluetoothAdapter mAdapter;
    private Set<BluetoothDevice> paired;
    private BluetoothDevice mDevice = null;
    private UUID uuid;
    private BluetoothSocket mSocket;
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
        Log.e("Bluetooth", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mAdapter == null) {
            Log.d("Bluetooth Service", "device not support");
        }
        paired = mAdapter.getBondedDevices();
        if (paired.size() > 0) {
            Log.d("BluetoothService","In pair >0");
            for (BluetoothDevice device : paired) {

                if (device.getName().equals("HC-05")) {
                    Log.d("BluetoothService","for loop");
                    mDevice = device;
                }
            }
            if(mDevice!=null) {
                ParcelUuid[] ids = mDevice.getUuids();
                for (ParcelUuid uuid1 : ids) {
                    uuid = uuid1.getUuid();
                    Log.e("Bluetooth Service","UUID "+uuid);
                }
            }
            try{
                mSocket = mDevice.createRfcommSocketToServiceRecord(uuid);
                mSocket.connect();
                Log.d("Bluetooth", "conn");
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        /*ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mSocket.isConnected()) {
                        try {
                            InputStream mInputStream = mSocket.getInputStream();
                            //OutputStream outputStream = mSocket.getOutputStream();
                            Log.e("Bluetooth", "Reading Message");
                            int i;
                            while ((i = mInputStream.read()) != -1) {
                                Log.e("TAG", i + "");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.d("Bluetooth", "not found socket");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 3000, TimeUnit.MILLISECONDS);*/
        return Service.START_STICKY;
    }
}
