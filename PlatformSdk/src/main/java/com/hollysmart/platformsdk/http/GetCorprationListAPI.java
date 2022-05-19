package com.hollysmart.platformsdk.http;
import com.hollysmart.platformsdk.PlatformSdk;
import com.hollysmart.platformsdk.data.CorpBean;
import com.hollysmart.platformsdk.http.callback.JsonCallback;
import com.hollysmart.platformsdk.http.model.CaiResponse;
import com.hollysmart.platformsdk.http.taskpool.INetModel;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

public class GetCorprationListAPI implements INetModel {

    private Object tag;
    private String token;
    private GorpIF gorpIF;

    public GetCorprationListAPI(Object tag, String token, GorpIF gorpIF) {
        this.tag = tag;
        this.token = token;
        this.gorpIF = gorpIF;
    }

    @Override
    public void request() {
        String url = PlatformSdk.getInstance().getAPP_BASE_URL() + "admin/api/corprations/me";
        OkGo.<CaiResponse<List<CorpBean>>>get(url)
                .tag(tag)
                .headers("Content-Type", "application/json")
                .headers("Authorization", token)
                .execute(new JsonCallback<CaiResponse<List<CorpBean>>>() {
                    @Override
                    public void onSuccess(Response<CaiResponse<List<CorpBean>>> response) {
                        if (response.code() == 200 && response.body() != null && response.body().status == 200) {
                            gorpIF.onResult(true, null, response.body().data);
                        } else {
                            gorpIF.onResult(false,  response.body().msg, null);
                        }
                    }

                    @Override
                    public void onError(Response<CaiResponse<List<CorpBean>>> response) {
                        super.onError(response);
                        gorpIF.onResult(false, "请求数据异常, 请检查网络", null);
                    }
                });

    }

    public interface GorpIF{
        void onResult(boolean isOk, String msg, List<CorpBean> corpBeanList);
    }

}













