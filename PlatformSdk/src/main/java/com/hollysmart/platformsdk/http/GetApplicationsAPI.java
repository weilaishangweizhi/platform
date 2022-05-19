package com.hollysmart.platformsdk.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hollysmart.platformsdk.PlatformSdk;
import com.hollysmart.platformsdk.editmenu.FunctionItem;
import com.hollysmart.platformsdk.http.taskpool.INetModel;
import com.hollysmart.platformsdk.utils.Mlog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;

public class GetApplicationsAPI implements INetModel {


    private String token;
    private String corpId;
    private AppListIF appListIF;

    public GetApplicationsAPI(String token, String corpId, AppListIF appListIF) {
        this.token = "Bearer" + " " + token;
        this.corpId = corpId;
        this.appListIF = appListIF;
    }

    @Override
    public void request() {
        String url = PlatformSdk.getInstance().getAPP_BASE_URL() + "admin/api/applications/" + corpId;
        Mlog.d("http", "获取应用列表:" + url);
        Mlog.d("http", "Header  Authorization:" + token);
        OkHttpUtils.get()
                .url( url )
                .addHeader( "Authorization", token )
                .build().execute( new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Mlog.d( "http", "获取应用列表 Exception = " + e );
                e.printStackTrace();
                appListIF.onResult( false, "应用列表获取失败", null );
            }

            @Override
            public void onResponse(String response, int id) {
                Mlog.d( "获取应用列表：" + response );
                try {
                    JSONObject object = new JSONObject( response );
                    int status = object.getInt( "status" );
                    if (status == 200) {
                        JSONArray data = object.getJSONArray( "data" );
                        Gson mGson = new GsonBuilder().setDateFormat( "yyyy-MM-dd HH:mm" ).create();
                        List<FunctionItem> apps = mGson.fromJson( data.toString(), new TypeToken<List<FunctionItem>>() {
                        }.getType() );
                        appListIF.onResult( true, null, apps );
                    } else {
                        appListIF.onResult( false, object.getString( "msg" ), null );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } );

    }

    public interface AppListIF {
        void onResult(boolean isOk, String msg, List<FunctionItem> apps);
    }

}



















