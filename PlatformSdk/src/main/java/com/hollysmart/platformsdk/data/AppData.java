package com.hollysmart.platformsdk.data;


import com.hollysmart.platformsdk.editmenu.FunctionItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppData {
    private static class AppDataHoler {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static AppData instance = new AppData();
    }
    private AppData() {
    }

    public static AppData getInstance(){
        return AppDataHoler.instance;
    }


//    private List<FunctionItem> commonApps = new ArrayList<>();
    private List<FunctionItem> selectData = new ArrayList<>();
    private Map<String, List<FunctionItem>> appTypeModels = new HashMap<>();


//    public List<FunctionItem> getCommonApps() {
//        return commonApps;
//    }

    public List<FunctionItem> getSelectData() {
        return selectData;
    }

    public Map<String, List<FunctionItem>> getAppTypeModels() {
        return appTypeModels;
    }
}
