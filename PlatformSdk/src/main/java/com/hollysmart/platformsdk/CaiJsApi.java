package com.hollysmart.platformsdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hollysmart.platformsdk.dsbridge.CompletionHandler;
import com.hollysmart.platformsdk.interfaces.JsxInterface;
import com.hollysmart.platformsdk.utils.ACache;
import com.hollysmart.platformsdk.utils.Mlog;
import com.lqr.imagepicker.ImagePicker;
import com.lqr.imagepicker.bean.ImageItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;

public class CaiJsApi {
    public static final int REQUEST_CODE = 1000;
    private Context mContext;
    private Activity activity;
    private Fragment fragment;
    private JsxInterface.JSXCallBack jsxCallBack;

    public CaiJsApi(Activity activity) {
        this.mContext = activity;
        this.activity = activity;
        this.jsxCallBack = (JsxInterface.JSXCallBack) activity;
    }

    public CaiJsApi(Fragment fragment) {
        this.mContext = fragment.getContext();
        this.fragment = fragment;
        this.jsxCallBack = (JsxInterface.JSXCallBack) fragment;
    }


    /**
     * 二维码扫描
     *
     * @param json
     * @param handler
     */
    @JavascriptInterface
    public void scanCode(Object json, final CompletionHandler<String> handler) {
        Mlog.d("调用了扫一扫页面");
        jsxCallBack.scanCode(result -> {
            Map<String, String> args = new HashMap<>();
            args.put("result", result);
            handler.complete(new Gson().toJson(args));
        });
    }


    /**
     * 打开本地相机  异步API
     *
     * @param json
     * @param handler
     */
    @JavascriptInterface
    public void takePhoto(Object json, final CompletionHandler<String> handler) {
        Mlog.d("调用了相机接口");
        jsxCallBack.setImagePathIF(datas -> {
            if (datas != null && datas.size() > 0) {
                String paths = datas.remove(0).path;
                Map<String, Object> args = new HashMap<>();

                args.put("tempImagePath", paths);

                Mlog.d("调用了相机接口 " + new Gson().toJson(args));

                handler.complete(new Gson().toJson(args));
            }
        });

        if (fragment == null) {
            ImagePicker.picker().cameraPick(fragment, REQUEST_CODE);
        } else {
            ImagePicker.picker().cameraPick(activity, REQUEST_CODE);
        }

    }

    /**
     * 打开本地相册包含相机  异步API
     *
     * @param json
     * @param handler
     */
    @JavascriptInterface
    public void chooseImage(Object json, final CompletionHandler<String> handler) {
        Mlog.d("调用了相册接口：" + json);
        try {
            JSONObject jsonObject = new JSONObject(valueOf(json));
            int count = 9;
            if (jsonObject.has("count")) {
                count = jsonObject.getInt("count");
            }
            jsxCallBack.setImagePathIF(datas -> {
                if (datas != null && datas.size() > 0) {
                    JsonArray paths = new JsonArray();
                    for (ImageItem bean : datas) {
                        paths.add(bean.path);
                    }
                    JsonObject args = new JsonObject();
                    args.add("tempFilePaths", paths);
                    Mlog.d("调用了相册接口 " + new Gson().toJson(args));
                    handler.complete(new Gson().toJson(args));
                }
            });

            if (fragment == null) {
                ImagePicker.picker().enableMultiMode(count).showCamera(true)
                        .pick(activity, REQUEST_CODE);
            } else {
                ImagePicker.picker().enableMultiMode(count).showCamera(true)
                        .pick(fragment, REQUEST_CODE);
            }

        } catch (Exception e) {
        }
    }

    /**
     * 上传文件  异步API
     *
     * @param json    {"formData":{"id":"WU_FILE_6551382593836148","name":"6551382593836148.jpg",
     *                "type":"image\/jpeg","lastModifiedDate":"2020-06-28T08:19:16.103Z","size":42784},
     *                "timeOut":300000,
     *                "url":"http:\/\/test.hollysmart.com.cn:9001\/sztran\/net\/controller.ashx?action=uploadimage&encode=utf-8",
     *                "filePath":"\/storage\/emulated\/0\/DCIM\/Camera\/IMG_20200628_102446.jpg",
     *                "name":"upfile"}
     * @param handler
     */
    @JavascriptInterface
    public void uploadFile(Object json, final CompletionHandler<String> handler) {
        Mlog.d("调用了上传文件");
        Mlog.d("js 传过来的 json" + json);

        jsxCallBack.uploadFile(json, new JsxInterface.UploadFileIF() {
            @Override
            public void getProgressData(int progressData) {
//                handler.setProgressData( String.valueOf( progressData ) );
            }

            @Override
            public void getResult(boolean isOk, String result) {
                if (isOk) {
                    JsonObject args = new JsonObject();
                    args.addProperty("data", result);
                    handler.complete(new Gson().toJson(args));
                }
            }
        });
    }

    /**
     * 分享到短信  异步API
     *
     * @param json
     */
    @JavascriptInterface
    public String sharedSms(Object json) {
        Mlog.d("调用了分享到短信");
        Mlog.d("js 传过来的 json" + json);
        try {
            JSONObject jsonObject = new JSONObject(valueOf(json));
            String msg_content = "";
            String number = "";
            if (jsonObject.has("msg_content")) {
                msg_content = jsonObject.getString("msg_content");
            }
            if (jsonObject.has("number")) {
                number = jsonObject.getString("number");
            }
            sendSmsWithBody(mContext, number, msg_content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "调用成功";
    }

    /**
     * 调用系统界面，给指定的号码发送短信，并附带短信内容
     *
     * @param context
     * @param number  可以传“”，单个手机号，多个手机号（英文“,”拼接）
     * @param body
     */
    private void sendSmsWithBody(Context context, String number, String body) {
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(Uri.parse("smsto:" + number));
        sendIntent.putExtra("sms_body", body);
        context.startActivity(sendIntent);
    }


    @JavascriptInterface
    public String makeCalls(Object json) {
        Mlog.d("调用了拨打电话");
        Mlog.d("js 传过来的 json" + json);
        try {
            JSONObject jsonObject = new JSONObject(valueOf(json));
            String phoneNum = "";
            if (jsonObject.has("phoneNum")) {
                phoneNum = jsonObject.getString("phoneNum");
            }
            makeCalls(mContext, phoneNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "调用成功";
    }

    /**
     * 拨打电话
     *
     * @param context
     * @param phoneNum
     */
    private void makeCalls(Context context, String phoneNum) {
        if (TextUtils.isEmpty(phoneNum)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /**
     * 行为采集-事件追踪  异步API
     *
     * @param json
     */
    @JavascriptInterface
    public void onAction(Object json) {
        Mlog.d("调用了行为采集-事件追踪");
        Mlog.d("js 传过来的 json" + json);
    }

    /**
     * 行为采集-页面追踪 异步API
     *
     * @param json
     */
    @JavascriptInterface
    public void onPageStart(Object json) {
        Mlog.d("调用了行为采集-页面追踪");
        Mlog.d("js 传过来的 json" + json);
    }

    /**
     * 关闭窗口
     *
     * @param json
     */
    @JavascriptInterface
    public String closeWindow(Object json) {
        Mlog.d("调用了关闭窗口");
        Mlog.d("js 传过来的 json" + json);
        if (activity != null) {
            activity.finish();
        }
        return "调用成功";
    }


    /**
     * 捕获Back键
     *
     * @param json
     */
    @JavascriptInterface
    public void onBack(Object json) {
        Mlog.d("捕获Back键");
        jsxCallBack.onBack(true);
    }

    /**
     * 停止捕获Back键监听
     *
     * @param json
     */
    @JavascriptInterface
    public void stopOnBack(Object json) {
        Mlog.d("调用了 停止捕获Back键监听");
        jsxCallBack.onBack(false);
    }

    /**
     * 重新加载页面
     *
     * @param json
     */
    @JavascriptInterface
    public void refreshPage(Object json) {
        Mlog.d("调用了 重新加载页面");
        jsxCallBack.refreshPage();
    }


    /***
     * 从网络资源下载到手机
     */
    @JavascriptInterface
    public void downloadFile(Object msg) {
        Mlog.d("downloadFile");
        String filePath = "";
        try {
            JSONObject object = new JSONObject(msg.toString());
            filePath = object.getString("filePath");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(filePath)) {
            Toast.makeText(mContext.getApplicationContext(), "文件路径为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        Mlog.d("文件路径========" + filePath);
        Uri uri = Uri.parse(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        mContext.startActivity(intent);

    }


    /**
     * 添加联系人
     *
     * @param msg {"name":"", "phoneNumber":""}
     */
    @JavascriptInterface
    public void addContact(Object msg) {
        Mlog.d("添加系统联系人" + msg);
        String name = "";
        String phoneNumber = "";
        try {
            JSONObject object = new JSONObject(msg.toString());
            name = object.getString("name");
            phoneNumber = object.getString("phoneNumber");
            Intent addIntent = new Intent(Intent.ACTION_INSERT,
                    Uri.withAppendedPath(Uri.parse("content://com.android.contacts"), "contacts"));
            addIntent.setType("vnd.android.cursor.dir/person");
            addIntent.setType("vnd.android.cursor.dir/contact");
            addIntent.setType("vnd.android.cursor.dir/raw_contact");
            addIntent.putExtra(ContactsContract.Intents.Insert.NAME, name); //名称：
            addIntent.putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber);// 电话：
            mContext.startActivity(addIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 保存数据
     *
     * @param json {"key":"","value":""}
     * @return
     */
    @JavascriptInterface
    public boolean saveJsonStr(Object json) {
        Mlog.d("保存数据：" + json);
        Map<String, String> args = new Gson().fromJson(valueOf(json), Map.class);
        ACache.get(activity.getApplicationContext()).put(args.get("key"), args.get("value"));
        return true;
    }

    /**
     * 获取数据
     *
     * @param key
     */
    @JavascriptInterface
    public String getJsonStr(Object key) {
        String content = ACache.get(activity.getApplicationContext()).getAsString(valueOf(key));
        Mlog.d("获取数据：" + key + "+" + content);
        return content;
    }

    /**
     * 删除数据
     *
     * @param key
     */
    @JavascriptInterface
    public void removeJsonStr(Object key) {
        Mlog.d("删除数据：" + key);
        ACache.get(activity.getApplicationContext()).remove(valueOf(key));
    }

}














