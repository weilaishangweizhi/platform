package com.hollysmart.platformsdk.editmenu;
import java.io.Serializable;

public class FunctionItem implements Serializable {

    public static final String MODEL_H5 = "app_classify_00";        //H5应用
    public static final String MODEL_WX = "app_classify_01";        //微信小程序
    public static final String MODEL_BAIDU = "app_classify_02";     //百度小程序
    public static final String MODEL_ANDROID = "app_classify_03";   //Android原生应用
    public static final String MODEL_IOS = "app_classify_04";       //IOS原生应用
    public static final String MODEL_OLD_H5 = "app_classify_05";    //旧H5应用
    public static final String MODEL_YX = "app_classify_06";        //原生方法

    public String appId;
    public String appName;
    public String appType;
    public String appTypeName;
    public String isOld;
    public String isWx;
    public String logoUrl;
    public String sort;
    public String type;

    public String appUrl;
    public String corpId;
    public String url;

    public String appClassify; //H5应用app_classify_00|微信小程序app_classify_01|百度小程序app_classify_02|Android原生应用app_classify_03|IOS原生应用app_classify_04

    public boolean isSelect = true;   //默认情况，在互联网取到的选中状态

    public int functionType = 1;  // -1、更多按钮   1、原生

    public boolean isCommon = false;  //是否是常用

    public String operationEntrance; //操作入口  开屏页/常用应用/全部应用/分组名称



}
