package com.developers.simplekeyboard;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

/**
 * Created by Amanjeet Singh on 07-May-17.
 */

public class Data {
    private static BluetoothSPP bt;

    public static void setBluetoothSpp(BluetoothSPP spp){
        Data.bt=spp;
    }

    public static BluetoothSPP getBt() {
        return bt;
    }
}
