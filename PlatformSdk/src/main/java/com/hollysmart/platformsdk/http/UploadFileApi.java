package com.hollysmart.platformsdk.http;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;

import com.hollysmart.platformsdk.http.taskpool.INetModel;
import com.hollysmart.platformsdk.interfaces.JsxInterface;
import com.hollysmart.platformsdk.utils.CCM_Bitmap;
import com.hollysmart.platformsdk.utils.FileTool;
import com.hollysmart.platformsdk.utils.Mlog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Iterator;

import okhttp3.Call;

public class UploadFileApi implements INetModel {

    private Object tag;
    private String url;
    private String name;
    private String fileName;
    private String filePath;
    private JSONObject formData;
    private JsxInterface.UploadFileIF uploadFileIF;

    public UploadFileApi(Object tag, Object json, JsxInterface.UploadFileIF uploadFileIF) {
        this.tag = tag;
        this.uploadFileIF = uploadFileIF;
        try {
            JSONObject jsonObject = new JSONObject( String.valueOf( json ) );
            url = jsonObject.getString( "url" );
            name = jsonObject.getString( "name" );
            filePath = jsonObject.getString( "filePath" );
            String[] strs = filePath.split( "/" );
            fileName = strs[strs.length - 1];
            formData = jsonObject.getJSONObject( "formData" );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //    public UploadFileApi(Object tag, String url, String name, String filePath, CaiJsApi.UploadFileIF uploadFileIF) {
//        this.tag = tag;
//        this.url = url;
//        this.name = name;
//        String[] strs = filePath.split( "/" );
//        fileName = strs[strs.length - 1];
//        this.filePath = filePath;
//        this.uploadFileIF = uploadFileIF;
//    }
    @SuppressLint("StaticFieldLeak")
    @Override
    public void request() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... voids) {
                    try {
                        final String ratioFilePath = ((Activity) tag).getFilesDir().getPath() + "/images/" + fileName;
                        FileTool.CreateDir( ((Activity) tag).getFilesDir().getPath() + "/images/" );
                        FileTool.CreateFile( ratioFilePath );
                        FileTool.copy( filePath, ratioFilePath );

                        File file = new File( ratioFilePath );
                        Mlog.d( "文件大小：" + file.length() );
                        if (file.length() > (1 * 1024 * 1024)) {
                            Mlog.d( "进行了压缩" );
                            CCM_Bitmap.compress( Bitmap.CompressFormat.JPEG, ratioFilePath, 50 );
                        }

                        return ratioFilePath;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(String ratioFilePath) {
                    super.onPostExecute( ratioFilePath );

                    if (ratioFilePath != null) {
                        File file = new File( ratioFilePath );
                        Mlog.d( "文件大小" + file.length() );
                        try {

                            PostFormBuilder postFormBuilder = OkHttpUtils
                                    .post()
                                    .addFile( name, fileName, file )
                                    .url( url );

                            Iterator<String> iterator = formData.keys();
                            while (iterator.hasNext()) {
                                String key = iterator.next();
                                postFormBuilder.addParams( key, formData.getString( key ) );
                            }

                            postFormBuilder.build().execute( new StringCallback() {
                                                  @Override
                                                  public void onError(Call call, Exception e, int id) {
                                                      Mlog.d( "错误 ： " + e.toString() );
                                                      uploadFileIF.getResult( false, e.toString() );
                                                  }

                                                  @Override
                                                  public void onResponse(String response, int id) {
                                                      Mlog.d( "返回值 ： " + response );
                                                      uploadFileIF.getResult( true, response );
                                                  }

                                                  @Override
                                                  public void inProgress(float progress, long total, int id) {
                                                      super.inProgress( progress, total, id );
                                                      Mlog.d( "进度 ： " + progress );
                                                      uploadFileIF.getProgressData( (int) (progress * 100) );
                                                  }
                                              }
                                    );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.execute();
        }
    }
}
