package com.hollysmart.platformsdk.http;

import com.hollysmart.platformsdk.PlatformSdk;
import com.hollysmart.platformsdk.data.AppModel;
import com.hollysmart.platformsdk.http.callback.JsonCallback;
import com.hollysmart.platformsdk.http.model.CaiResponse;
import com.hollysmart.platformsdk.http.taskpool.INetModel;
import com.hollysmart.platformsdk.interfaces.JsxInterface;
import com.hollysmart.platformsdk.utils.Mlog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

public class GetApplicationsNewAPI implements INetModel {


    private String token;
    private String corpId;
    private JsxInterface.ResultIF resultIF;

    public GetApplicationsNewAPI(String token, String corpId, JsxInterface.ResultIF resultIF) {
        this.token = "Bearer" + " " + token;
        this.corpId = corpId;
        this.resultIF = resultIF;
    }

    @Override
    public void request() {
        String url = PlatformSdk.getInstance().getAPP_BASE_URL() + "admin/api/applications/getApplications/" + corpId;
        Mlog.d("http", "获取应用列表:" + url);
        Mlog.d("http", "Header  Authorization:" + token);

        OkGo.<CaiResponse<AppModel>>get(url)
                .headers("Authorization", token)
                .execute(new JsonCallback<CaiResponse<AppModel>>() {
                    @Override
                    public void onSuccess(Response<CaiResponse<AppModel>> response) {
                        if (response.body().status == 200) {
                            resultIF.onResult(true, null, response.body().data);
                        } else {
                            resultIF.onResult(false, response.body().msg, null);
                        }
                    }
                });
    }

}






