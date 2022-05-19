package com.hollysmart.platformsdk;

import android.app.Application;

import com.hollysmart.platformsdk.utils.Mlog;

public class PlatformSdk {
    private PlatformSdk(){
    }
    private static class PlatformSdkInstance {
        private static final PlatformSdk INSTANCE = new PlatformSdk();
    }
    public static PlatformSdk getInstance() {
        return PlatformSdkInstance.INSTANCE;
    }

    private Application application;
    private String APP_BASE_URL;

    public Application getApplication() {
        return application;
    }

    public PlatformSdk setApplication(Application application) {
        this.application = application;
        return this;
    }

    public String getAPP_BASE_URL() {
        return APP_BASE_URL;
    }

    public PlatformSdk setAPP_BASE_URL(String APP_BASE_URL) {
        this.APP_BASE_URL = APP_BASE_URL;
        return this;
    }

    public PlatformSdk setLog(String tag){
        Mlog.TAG = tag;
        Mlog.OPENLOG = true;
        return this;
    }

}
