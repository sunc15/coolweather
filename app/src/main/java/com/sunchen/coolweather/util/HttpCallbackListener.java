package com.sunchen.coolweather.util;

/**
 * Created by sunchen on 2016/2/17.
 */
public interface HttpCallbackListener {
    public void OnFinsh(String response);
    public void OnError(Exception e);
}
