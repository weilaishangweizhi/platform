package com.hollysmart.platformsdk.http.taskpool;

/**
 * Created by cai on 16/6/6.
 */
public interface OnNetRequestListener<T> {
    void onFinish();
    void OnNext(int taskTag, T data, int total);
    void onFailed(String msg);
}
