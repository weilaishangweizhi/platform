package com.hollysmart.platformsdk.http;

import com.hollysmart.platformsdk.PlatformSdk;
import com.hollysmart.platformsdk.http.callback.JsonCallback;
import com.hollysmart.platformsdk.http.model.CaiResponse;
import com.hollysmart.platformsdk.http.taskpool.INetModel;
import com.hollysmart.platformsdk.interfaces.JsxInterface;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

public class GetPlatformImageAPI implements INetModel {

    private JsxInterface.ResultIF<String> resultIF;

    public GetPlatformImageAPI(JsxInterface.ResultIF<String> resultIF) {
        this.resultIF = resultIF;
    }

    @Override
    public void request() {
        String url = PlatformSdk.getInstance().getAPP_BASE_URL() + "admin/api/user/getWorkImageUrl";
        OkGo.<CaiResponse<String>>get(url)
                .execute(new JsonCallback<CaiResponse<String>>() {
                    @Override
                    public void onSuccess(Response<CaiResponse<String>> response) {
                        if (response.body().status == 200) {
                            resultIF.onResult(true, null, response.body().data);
                        } else {
                            resultIF.onResult(false, response.body().msg, null);
                        }
                    }
                });
    }

}



