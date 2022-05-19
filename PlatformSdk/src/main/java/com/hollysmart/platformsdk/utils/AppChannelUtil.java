package com.hollysmart.platformsdk.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.hollysmart.platformsdk.PlatformSdk;


public class AppChannelUtil {

    public static String getChannelId() {
        return getMetaDataStr("CHANNEL_ID");
    }
    public static String getAppName() {
        return getMetaDataStr("APP_NAME");
    }

    public static String getMetaDataStr(String key) {
        String resultData = "";
        if (!TextUtils.isEmpty(key)) {
            Bundle appInfoBundle = getAppInfoBundle();
            if (appInfoBundle != null)
                resultData = appInfoBundle.getString(key);
        }
        return resultData;
    }

    public static int getMetaDataInt(String key) {
        int resultData = 0;
        if (!TextUtils.isEmpty(key)) {
            Bundle appInfoBundle = getAppInfoBundle();
            if (appInfoBundle != null)
                resultData = appInfoBundle.getInt(key);
        }
        return resultData;
    }

    private static Bundle getAppInfoBundle() {
//注意：这里ApplicationInfo不能直接 CommonApplication.mApplication.getApplicationInfo()这样获取，否则会获取不到meta_data的
        ApplicationInfo applicationInfo = getAppInfo();
        if (applicationInfo != null) {
            return applicationInfo.metaData;
        }
        return null;
    }

    private static ApplicationInfo getAppInfo() {
        PackageManager packageManager = PlatformSdk.getInstance().getApplication().getPackageManager();
        ApplicationInfo applicationInfo = null;
        if (packageManager != null) {
            try {
                applicationInfo = packageManager.getApplicationInfo( PlatformSdk.getInstance().getApplication().getPackageName(), PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return applicationInfo;
    }
}
