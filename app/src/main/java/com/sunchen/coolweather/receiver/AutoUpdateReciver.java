package com.sunchen.coolweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sunchen.coolweather.service.AutuUpdateService;

/**
 * Created by sunchen on 2016/2/19.
 */
public class AutoUpdateReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i=new Intent(context, AutuUpdateService.class);
        context.startService(i);
    }
}
