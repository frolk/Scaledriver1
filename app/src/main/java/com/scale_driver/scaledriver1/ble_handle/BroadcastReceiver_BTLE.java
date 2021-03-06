package com.scale_driver.scaledriver1.ble_handle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.scale_driver.scaledriver1.UartService;

public class BroadcastReceiver_BTLE extends BroadcastReceiver {

    private static final String TAG = "myUart";
    private Handler mHandler;
    private boolean mConnected = false;
    private Context activityContext;

    public BroadcastReceiver_BTLE(Context activityContext) {
        this.activityContext = activityContext;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        final Intent mIntent = intent;

        if (action.equals(UartService.ACTION_GATT_CONNECTED)) {
            //BleUtils.showmsg("This is from separate Broadcast file. We connected");
            Toast.makeText(activityContext, "hello man how are you?", Toast.LENGTH_SHORT).show();
            BleUtils.showmsg("Connected. Congratulation!!!");
        }

        if (action.equals(UartService.ACTION_GATT_DISCONNECTED)) {
            Toast.makeText(activityContext, "we disconnected bro", Toast.LENGTH_SHORT).show();
        }

        if(action.equals(UartService.ACTION_GATT_SERVICES_DISCOVERED)){
            BleUtils.showmsg("service discovered ++");

            //mHandler.sendEmptyMessage(222);
            }

        if(action.equals(UartService.ACTION_DATA_AVAILABLE)){
            Toast.makeText(activityContext, "data available", Toast.LENGTH_SHORT).show();

//            final byte[] txValue = intent.getByteArrayExtra(UartService.EXTRA_DATA);
//            try{
//                String text = new String(txValue, "UTF-8");
//                Toast.makeText(activityContext, "text get", Toast.LENGTH_SHORT).show();
//                BleUtils.showmsg("text get");
//            } catch (Exception e) {
//                Log.e(TAG, e.toString());
//            }
        }


//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try{
//                            tvData = (TextView) findViewById(R.id.tvData);
//                            String text = new String(txValue, "UTF-8");
//                            BleUtils.showmsg(text);
//                            tvData.setText(text);
//                        } catch (Exception e) {
//                            Log.e(TAG, e.toString());
//                        }
//                    }
//                });
//            }



    }

    ;


}

