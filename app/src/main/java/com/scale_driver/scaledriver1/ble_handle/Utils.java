package com.scale_driver.scaledriver1.ble_handle;

import android.content.IntentFilter;
import android.util.Log;

import com.scale_driver.scaledriver1.UartService;

public class Utils {



    public static IntentFilter makeGattUpdateIntentFilter(){
        final IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(UartService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(UartService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(UartService.DEVICE_DOES_NOT_SUPPORT_UART);

        return intentFilter;
    }

    public static String hexToString(byte[] data){
        final StringBuilder stringBuilder = new StringBuilder(data.length);

        for(byte byteChar : data){
            stringBuilder.append(String.format("%02X", byteChar));
        }
        return stringBuilder.toString();
    }

    public static void showmsg(String msg){
        String TAG = "myUart";
        Log.d(TAG, msg);
    }
}
