package com.scale_driver.scaledriver1.ble_handle;

import android.app.Activity;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import com.scale_driver.scaledriver1.MainActivity;
import com.scale_driver.scaledriver1.R;
import com.scale_driver.scaledriver1.UartService;

import java.io.UnsupportedEncodingException;

public class BleUtils {

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

    public static void sendMsgBle(Activity activity, UartService service, String s, Boolean deviceConnected){

        if (deviceConnected) {
            byte[] value;
            try {
                value = s.getBytes("UTF-8");
                service.writeRXCharacteristic(value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(activity, R.string.needToConnToDevice, Toast.LENGTH_SHORT).show();
        }
    }

}
