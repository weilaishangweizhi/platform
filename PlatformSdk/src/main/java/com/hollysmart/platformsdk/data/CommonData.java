package com.hollysmart.platformsdk.data;

import android.content.Context;
import android.content.SharedPreferences;


import com.hollysmart.platformsdk.PlatformSdk;
import com.hollysmart.platformsdk.editmenu.FunctionItem;
import com.hollysmart.platformsdk.utils.ACache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommonData {

    public static String CACHE_COMMON = "commonData";

    public static List<FunctionItem> getCommon(String key) {
        List<FunctionItem> commons = new ArrayList<>();
        Object object = ACache.get(PlatformSdk.getInstance().getApplication(), CACHE_COMMON)
                .getAsObject(CACHE_COMMON + key);

        if (object != null) {
            commons = (List<FunctionItem>) object;
        }
        return commons;
    }

    public static void saveCommon(String key, List<FunctionItem> commons) {
        ACache.get(PlatformSdk.getInstance().getApplication(), CACHE_COMMON)
                .put(CACHE_COMMON + key, (Serializable) commons);
    }

    public static boolean isFristSetCommon(){
        SharedPreferences sp = PlatformSdk.getInstance().getApplication().getSharedPreferences("Common", Context.MODE_PRIVATE);
        boolean isFrist = sp.getBoolean("isfrist", true);
        if (isFrist){
            sp.edit().putBoolean("isfrist", false).apply();
        }
        return isFrist;
    }


    /**
     * 判断 list中是否包含 app
     *
     * @param list
     * @param app
     * @return
     */
    public static boolean contains(List<FunctionItem> list, FunctionItem app) {
        for (FunctionItem item : list) {
            if (item.appName.equals(app.appName)) {
                return true;
            }
        }
        return false;
    }

}
