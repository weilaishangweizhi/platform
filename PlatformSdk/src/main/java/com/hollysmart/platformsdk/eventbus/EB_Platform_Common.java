package com.hollysmart.platformsdk.eventbus;


import com.hollysmart.platformsdk.data.AppItem;

import java.util.List;

public class EB_Platform_Common {

    public static final int ADD = 1;
    public static final int REMOVE = 2;
    public static final int SORT = 3;
    public int type; //1: 添加 2：移除 3：设置页面调整顺序及移除应用
    public AppItem app;
    public List<AppItem> commons;

    public EB_Platform_Common(int type, AppItem app) {
        this.type = type;
        this.app = app;
    }

    public EB_Platform_Common(int type, List<AppItem> commons) {
        this.type = type;
        this.commons = commons;
    }
}
