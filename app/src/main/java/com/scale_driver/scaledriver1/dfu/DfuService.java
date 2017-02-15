package com.scale_driver.scaledriver1.dfu;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import no.nordicsemi.android.dfu.DfuBaseService;

public class DfuService extends DfuBaseService {
    public DfuService() {
    }

    @Override
    protected Class<? extends Activity> getNotificationTarget() {
        return NotificationActivity.class;
    }

    protected boolean isDebug(){
        return true;
    }
}
