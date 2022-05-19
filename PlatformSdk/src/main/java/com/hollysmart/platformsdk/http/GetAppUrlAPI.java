package com.hollysmart.platformsdk.http;

import com.hollysmart.platformsdk.PlatformSdk;
import com.hollysmart.platformsdk.editmenu.FunctionItem;
import com.hollysmart.platformsdk.http.taskpool.INetModel;
import com.hollysmart.platformsdk.utils.Mlog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class GetAppUrlAPI implements INetModel {

    private String token;
    private FunctionItem app;
    private AppUrlIF appUrlIF;

    public GetAppUrlAPI(String token, FunctionItem app, AppUrlIF appUrlIF) {
        this.token = "Bearer" + " " + token;
        this.app = app;
        this.appUrlIF = appUrlIF;
    }

    @Override
    public void request() {
        String url = PlatformSdk.getInstance().getAPP_BASE_URL() + "admin/api/applications/" + app.corpId + "/" + app.appId;
        Mlog.d("http", "获取应用地址:" + url);
        Mlog.d("http", "Header  Authorization:" + token);

        OkHttpUtils.get()
                .url( url )
                .addHeader( "Authorization", token )
                .build().execute( new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Mlog.d( "http", "获取应用地址 Exception = " + e );
                e.printStackTrace();
                appUrlIF.onResult( false, "应用地址获取失败", null );
            }

            @Override
            public void onResponse(String response, int id) {
                Mlog.d( "http", "获取应用地址：" + response );
                try {
                    JSONObject object = new JSONObject( response );
                    int status = object.getInt( "status" );
                    if (status == 200) {
                        JSONObject data = object.getJSONObject( "data" );
                        app.appUrl = data.getString( "appUrl" );
                        app.corpId = data.getString( "corpId" );
                        app.url = data.getString( "url" );
                        appUrlIF.onResult( true, null, app );
                    } else {
                        appUrlIF.onResult( false, object.getString( "msg" ), null );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } );

    }

    public interface AppUrlIF {
        void onResult(boolean isOk, String msg, FunctionItem app);
    }
}



















