package com.hollysmart.platformsdk.http;

import com.google.gson.Gson;
import com.hollysmart.platformsdk.PlatformSdk;
import com.hollysmart.platformsdk.http.callback.JsonCallback;
import com.hollysmart.platformsdk.http.model.CaiResponse;
import com.hollysmart.platformsdk.http.taskpool.INetModel;
import com.hollysmart.platformsdk.interfaces.JsxInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.Map;

public class UserSettingSaveAPI implements INetModel {

    private Object tag;
    private String token;
    private String key;
    private Map<String, String> args;
    private JsxInterface.ResultIF<String> resultIF;

    public UserSettingSaveAPI(Object tag, String token, String key, Map<String, String> args, JsxInterface.ResultIF resultIF) {
        this.tag = tag;
        this.token = token;
        this.key = key;
        this.args = args;
        this.resultIF = resultIF;
    }

    @Override
    public void request() {
        String url = PlatformSdk.getInstance().getAPP_BASE_URL() + "/admin/user-setting/me/" + key;
        OkGo.<CaiResponse<String>>post(url)
                .tag(tag)
                .headers("Content-Type", "application/json")
                .headers("Authorization", token)
                .upJson(new Gson().toJson(args))
                .execute(new JsonCallback<CaiResponse<String>>() {
                    @Override
                    public void onSuccess(Response<CaiResponse<String>> response) {
                        if (response.code() == 200 && response.body() != null && response.body().status == 200) {
                            resultIF.onResult(true, null, response.body().data);
                        } else {
                            resultIF.onResult(false, response.body().msg, null);
                        }
                    }

                    @Override
                    public void onError(Response<CaiResponse<String>> response) {
                        super.onError(response);
                        resultIF.onResult(false, "请求数据异常", null);
                    }
                });
    }

}













