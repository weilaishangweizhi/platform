package com.hollysmart.platformsdk.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;
import com.hollysmart.platformsdk.PlatformSdk;
import com.hollysmart.platformsdk.editmenu.FunctionItem;
import com.hollysmart.platformsdk.http.GetAppUrlAPI;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据小程序的appClassify字段进入对应的小程序页面
 */

public class StartSmallprogramUtil {

    private String access_token;
    private Activity activity;

    public StartSmallprogramUtil(String access_token, Activity activity) {
        this.access_token = access_token;
        this.activity = activity;
    }

    /**
     * 处理点击的小程序，根据appClassify字段判断是哪一类
     *
     * @param functionItem
     */
    public void dealApp(FunctionItem functionItem) {
        if (TextUtils.equals(functionItem.appClassify, FunctionItem.MODEL_BAIDU)) {
            //百度小程序
        } else if (TextUtils.equals(functionItem.appClassify, FunctionItem.MODEL_WX)) {
            new GetAppUrlAPI(access_token, functionItem, new GetAppUrlAPI.AppUrlIF() {
                @Override
                public void onResult(boolean isOk, String msg, FunctionItem app) {
                    if (isOk) {
                        startminichatProgress(functionItem);
                    }
                }
            }).request();
        } else if (TextUtils.equals(functionItem.appClassify, FunctionItem.MODEL_YX)) {
            //小鱼视频
        } else if (TextUtils.equals(functionItem.appClassify, FunctionItem.MODEL_IOS)) {
            ToastUtils.showLong("该应用仅支持苹果手机使用");
        } else if (TextUtils.equals(functionItem.appClassify, FunctionItem.MODEL_ANDROID)) {
            new GetAppUrlAPI(access_token, functionItem, new GetAppUrlAPI.AppUrlIF() {
                @Override
                public void onResult(boolean isOk, String msg, FunctionItem app) {
                    if (isOk) {
                        startAPP(app);
                    }
                }
            }).request();
        } else {
            new GetAppUrlAPI(access_token, functionItem, new GetAppUrlAPI.AppUrlIF() {
                @Override
                public void onResult(boolean isOk, String msg, FunctionItem app) {
                    if (isOk) {
                        startH5(app);
                    }
                }
            }).request();
        }
    }

    /**
     * 调用原生应用
     *
     * @param functionItem
     */
    private void startAPP(FunctionItem functionItem) {
        Map<String, String> stringStringMap = splitStr(functionItem.url);
        if (stringStringMap.containsKey("isopen") && stringStringMap.containsKey("package") && isInstallApp(stringStringMap.get("package"))) {
            String url = stringStringMap.get("url");
            if (stringStringMap.containsKey("code")) {
                url = url + "?code=" + stringStringMap.get("code");
            }
            if (stringStringMap.containsKey("corpId")) {
                url = url + "?corpId=" + stringStringMap.get("corpId");
            }
            Mlog.d("手机的App---url" + url);
            Uri uri = Uri.parse(url);
            String host = uri.getHost();
            String scheme = uri.getScheme();
            //host 和 scheme 都不能为null
            if (!TextUtils.isEmpty(host) && !TextUtils.isEmpty(scheme)) {
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(intent);
            }
        }
    }

    /**
     * 调用h5应用
     *
     * @param functionItem
     */
    private void startH5(FunctionItem functionItem) {
//        Intent intent = new Intent(activity, DWebViewActivity.class);
//        intent.putExtra("app", functionItem);
//        activity.startActivity(intent);
    }

    /**
     * 调用微信小程序
     *
     * @param functionItem
     */
    private void startminichatProgress(FunctionItem functionItem) {
        Mlog.d("---微信小程序----");
    }


    public static Map<String, String> splitStr(String url) {
        Mlog.d("url=====" + url);
        Map<String, String> map = new HashMap<String, String>();
        int i1 = url.indexOf("?");
        String substring = url.substring(i1 + 1);
        try {
            if (substring != null) {
                String[] valuse = substring.split("&");
                if (valuse.length > 0) {
                    for (int i = 0; i < valuse.length; i++) {
                        String s = valuse[i];
                        String[] split = s.split("=");
                        map.put(split[0], URLDecoder.decode(split[1], "UTF-8"));

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static boolean isInstallApp(String packageName) {
        final PackageManager packageManager = PlatformSdk.getInstance().getApplication().getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

}
