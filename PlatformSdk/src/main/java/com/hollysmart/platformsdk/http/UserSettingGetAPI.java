package com.hollysmart.platformsdk.http;

import com.hollysmart.platformsdk.PlatformSdk;
import com.hollysmart.platformsdk.data.UserSettingInfo;
import com.hollysmart.platformsdk.http.callback.JsonCallback;
import com.hollysmart.platformsdk.http.model.CaiResponse;
import com.hollysmart.platformsdk.http.taskpool.INetModel;
import com.hollysmart.platformsdk.interfaces.JsxInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

public class UserSettingGetAPI implements INetModel {

    private Object tag;
    private String token;
    private String key;
    private String id;
    private JsxInterface.ResultIF<UserSettingInfo> resultIF;

    public UserSettingGetAPI(Object tag, String token, String key, String id, JsxInterface.ResultIF<UserSettingInfo> resultIF) {
        this.tag = tag;
        this.token = token;
        this.key = key;
        this.id = id;
        this.resultIF = resultIF;
    }

    @Override
    public void request() {
        String url = PlatformSdk.getInstance().getAPP_BASE_URL() + "/admin/user-setting/me/" + key + "/" + id;
        OkGo.<CaiResponse<UserSettingInfo>>get(url)
                .tag(tag)
                .headers("Content-Type", "application/json")
                .headers("Authorization", token)
                .execute(new JsonCallback<CaiResponse<UserSettingInfo>>() {
                    @Override
                    public void onSuccess(Response<CaiResponse<UserSettingInfo>> response) {
                        if (response.code() == 200 && response.body() != null && response.body().status == 200) {
                            resultIF.onResult(true, null, response.body().data);
                        } else {
                            resultIF.onResult(false, response.body().msg, null);
                        }
                    }

                    @Override
                    public void onError(Response<CaiResponse<UserSettingInfo>> response) {
                        super.onError(response);
                        resultIF.onResult(false, "请求数据异常", null);
                    }
                });
    }

}













