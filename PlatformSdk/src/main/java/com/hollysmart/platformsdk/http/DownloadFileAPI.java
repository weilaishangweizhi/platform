package com.hollysmart.platformsdk.http;


import com.hollysmart.platformsdk.http.taskpool.INetModel;
import com.hollysmart.platformsdk.utils.Mlog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;

public class DownloadFileAPI implements INetModel {

    private String url;
    private String filePath;
    private String fileName;
    private DownloadIF downloadIF;

    public DownloadFileAPI(String url, String filePath, String fileName, DownloadIF downloadIF) {
        this.url = url;
        this.filePath = filePath;
        this.fileName = fileName;
        this.downloadIF = downloadIF;
    }

    @Override
    public void request() {
        OkHttpUtils.get().url( url ).build().execute(new FileCallBack( filePath, fileName ) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Mlog.d( "下载失败" );
                downloadIF.result( false );
            }

            @Override
            public void onResponse(File response, int id) {
                Mlog.d( "下载成功" );
                downloadIF.result( true );
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress( progress, total, id );
                downloadIF.inProgress( progress, total, id );
            }
        } );
    }
    public interface DownloadIF {
        void inProgress(float progress, long total, int id);

        void result(boolean isOK);
    }
}
























