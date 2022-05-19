package com.hollysmart.platformdemo;


import androidx.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;
import com.hollysmart.platformsdk.PlatformSdk;

public class BaseApp extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        PlatformSdk.getInstance()
                .setApplication(this)
                .setLog("com.test");
        Utils.init(this);
    }
}
