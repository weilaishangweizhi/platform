package com.hollysmart.platformdemo;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.hollysmart.platformsdk.NewPlatformFragment;
import com.hollysmart.platformsdk.data.AppModel;
import com.hollysmart.platformsdk.editmenu.FunctionItem;
import com.hollysmart.platformsdk.eventbus.EB_Platform_Refresh;
import com.hollysmart.platformsdk.interfaces.JsxInterface;
import com.hollysmart.platformsdk.utils.Mlog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private NewPlatformFragment fragment;
    private boolean isRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        fragment = new NewPlatformFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
        refresh(null);
        fragment.setPlatformAppItemIF(new JsxInterface.PlatformAppItemIF() {
            @Override
            public void onItem(FunctionItem functionItem) {
                Mlog.d("点击了App");
                ToastUtils.showShort("点击了:" + functionItem.appName);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(EB_Platform_Refresh refresh) {
        isRefresh = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 2000);
    }

    private void getData() {
        AppModel appModel = new Gson().fromJson(apps, AppModel.class);
        fragment.initAllData(isRefresh, appModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private String apps = "{" +
            "        \"customGrouping\": [" +
            "            {" +
            "                \"id\": \"ungrouped\"," +
            "                \"name\": \"全部\"," +
            "                \"vos\": [" +
            "                    {" +
            "                        \"appAlias\": \"OA待办\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx07047cfa6383e4a90\"," +
            "                        \"appName\": \"待办\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202202231158191496514771107803138.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202202231158191496514771107803138.jpg\"," +
            "                        \"sort\": 2," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"0\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"OA已办\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx00e1e4641f973486f\"," +
            "                        \"appName\": \"已办\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202202231158411496514859313995778.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202202231158411496514859313995778.jpg\"," +
            "                        \"sort\": 3," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"012\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"OA待阅\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx053abe6e7d0fa47d6\"," +
            "                        \"appName\": \"待阅\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202202231158571496514926808735745.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202202231158571496514926808735745.jpg\"," +
            "                        \"sort\": 4," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"012\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"OA已阅\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx06dec1813afa241b6\"," +
            "                        \"appName\": \"已阅\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202202231159171496515010665476098.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202202231159171496515010665476098.jpg\"," +
            "                        \"sort\": 5," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"012\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"扫一扫\"," +
            "                        \"appClassify\": \"app_classify_06\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx00137179f913f4d10\"," +
            "                        \"appName\": \"扫一扫\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202111010428151455089289359572993.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202111010428151455089289359572993.jpg\"," +
            "                        \"sort\": 2010," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"0\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"财务影像\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx0df3682d6a4b949a0\"," +
            "                        \"appName\": \"财务影像\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202112171157301471690996298362881.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202112171157301471690996298362881.jpg\"," +
            "                        \"sort\": 2020," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"012\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"电子合同\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx00655d51d035c4f38\"," +
            "                        \"appName\": \"电子合同\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202112170600451471782408113172482.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202112170600451471782408113172482.jpg\"," +
            "                        \"sort\": 2030," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"012\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"公车管理APP\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx05eb3b2aa12bc4990\"," +
            "                        \"appName\": \"公车管理\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202202241200021496515203146260481.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202202241200021496515203146260481.jpg\"," +
            "                        \"sort\": 2050," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"012\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"首信云视频（原生）\"," +
            "                        \"appClassify\": \"app_classify_07\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx01a94463dbd85488a\"," +
            "                        \"appName\": \"首信云视频\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202111020506321455461313613414402.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202111020506321455461313613414402.jpg\"," +
            "                        \"sort\": 2060," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"0\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"通知公告APP\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx0b1da2e908e3d4f50\"," +
            "                        \"appName\": \"通知公告\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202111231011301462967010865524738.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202111231011301462967010865524738.jpg\"," +
            "                        \"sort\": 2080," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"012\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"智能填报\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx06a835edf05f0492f\"," +
            "                        \"appName\": \"智能填报\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"0\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202203110154221502160984196857857.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202203110154221502160984196857857.jpg\"," +
            "                        \"sort\": 2100," +
            "                        \"type\": \"02\"," +
            "                        \"visibleRole\": \"0\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"智能审批\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx0a9116e94a2ad4cde\"," +
            "                        \"appName\": \"智能审批\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"0\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202202170652501494263564280565762.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202202170652501494263564280565762.jpg\"," +
            "                        \"sort\": 2120," +
            "                        \"type\": \"02\"," +
            "                        \"visibleRole\": \"0\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"平台介绍\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx0eb6da45b3d194363\"," +
            "                        \"appName\": \"平台介绍\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"1\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202202280325081498197561117335553.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202202280325081498197561117335553.jpg\"," +
            "                        \"sort\": 9800," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"012\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"使用指南\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx02f2595f386bb49df\"," +
            "                        \"appName\": \"使用指南\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"0\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202202101151121491620740443754498.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202202101151121491620740443754498.jpg\"," +
            "                        \"sort\": 9900," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"012\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"复工复产申请表\"," +
            "                        \"appClassify\": \"\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx01904100e819045a3\"," +
            "                        \"appName\": \"复工复产申请表\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"0\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202205191227091526962604201820162.png\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202205191227091526962604201820162.png\"," +
            "                        \"sort\": 10111," +
            "                        \"type\": \"02\"," +
            "                        \"visibleRole\": \"0\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"智能会议室(APP)\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx004839db81bc846cf\"," +
            "                        \"appName\": \"智能会议室\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202111220304381462678393421115393.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202111220304381462678393421115393.jpg\"," +
            "                        \"sort\": 10210," +
            "                        \"type\": \"02\"," +
            "                        \"visibleRole\": \"0\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"工作周报app\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx0682ec8e4312c4334\"," +
            "                        \"appName\": \"工作周报\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"1\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202204110722351513477609894273026.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202204110722351513477609894273026.jpg\"," +
            "                        \"sort\": 10220," +
            "                        \"type\": \"02\"," +
            "                        \"visibleRole\": \"012\"" +
            "                    }" +
            "                ]" +
            "            }," +
            "            {" +
            "                \"id\": \"bf1551de-9eb1-11ec-be15-fa163ef8b356\"," +
            "                \"name\": \"移动办公\"," +
            "                \"vos\": [" +
            "                    {" +
            "                        \"appAlias\": \"OA待办\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx07047cfa6383e4a90\"," +
            "                        \"appName\": \"待办\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202202231158191496514771107803138.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202202231158191496514771107803138.jpg\"," +
            "                        \"sort\": 2," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"0\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"OA已办\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx00e1e4641f973486f\"," +
            "                        \"appName\": \"已办\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202202231158411496514859313995778.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202202231158411496514859313995778.jpg\"," +
            "                        \"sort\": 3," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"012\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"OA待阅\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx053abe6e7d0fa47d6\"," +
            "                        \"appName\": \"待阅\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202202231158571496514926808735745.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202202231158571496514926808735745.jpg\"," +
            "                        \"sort\": 4," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"012\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"OA已阅\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx06dec1813afa241b6\"," +
            "                        \"appName\": \"已阅\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202202231159171496515010665476098.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202202231159171496515010665476098.jpg\"," +
            "                        \"sort\": 5," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"012\"" +
            "                    }" +
            "                ]" +
            "            }," +
            "            {" +
            "                \"id\": \"bf155255-9eb1-11ec-be15-fa163ef8b356\"," +
            "                \"name\": \"国资小程序\"," +
            "                \"vos\": [" +
            "                    {" +
            "                        \"appAlias\": \"扫一扫\"," +
            "                        \"appClassify\": \"app_classify_06\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx00137179f913f4d10\"," +
            "                        \"appName\": \"扫一扫\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202111010428151455089289359572993.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202111010428151455089289359572993.jpg\"," +
            "                        \"sort\": 2010," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"0\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"财务影像\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx0df3682d6a4b949a0\"," +
            "                        \"appName\": \"财务影像\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202112171157301471690996298362881.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202112171157301471690996298362881.jpg\"," +
            "                        \"sort\": 2020," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"012\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"电子合同\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx00655d51d035c4f38\"," +
            "                        \"appName\": \"电子合同\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202112170600451471782408113172482.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202112170600451471782408113172482.jpg\"," +
            "                        \"sort\": 2030," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"012\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"公车管理APP\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx05eb3b2aa12bc4990\"," +
            "                        \"appName\": \"公车管理\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202202241200021496515203146260481.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202202241200021496515203146260481.jpg\"," +
            "                        \"sort\": 2050," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"012\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"首信云视频（原生）\"," +
            "                        \"appClassify\": \"app_classify_07\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx01a94463dbd85488a\"," +
            "                        \"appName\": \"首信云视频\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202111020506321455461313613414402.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202111020506321455461313613414402.jpg\"," +
            "                        \"sort\": 2060," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"0\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"通知公告APP\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx0b1da2e908e3d4f50\"," +
            "                        \"appName\": \"通知公告\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202111231011301462967010865524738.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202111231011301462967010865524738.jpg\"," +
            "                        \"sort\": 2080," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"012\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"智能填报\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx06a835edf05f0492f\"," +
            "                        \"appName\": \"智能填报\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"0\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202203110154221502160984196857857.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202203110154221502160984196857857.jpg\"," +
            "                        \"sort\": 2100," +
            "                        \"type\": \"02\"," +
            "                        \"visibleRole\": \"0\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"智能审批\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx0a9116e94a2ad4cde\"," +
            "                        \"appName\": \"智能审批\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"0\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202202170652501494263564280565762.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202202170652501494263564280565762.jpg\"," +
            "                        \"sort\": 2120," +
            "                        \"type\": \"02\"," +
            "                        \"visibleRole\": \"0\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"平台介绍\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx0eb6da45b3d194363\"," +
            "                        \"appName\": \"平台介绍\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"1\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202202280325081498197561117335553.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202202280325081498197561117335553.jpg\"," +
            "                        \"sort\": 9800," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"012\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"使用指南\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx02f2595f386bb49df\"," +
            "                        \"appName\": \"使用指南\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"0\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202202101151121491620740443754498.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202202101151121491620740443754498.jpg\"," +
            "                        \"sort\": 9900," +
            "                        \"type\": \"01\"," +
            "                        \"visibleRole\": \"012\"" +
            "                    }" +
            "                ]" +
            "            }," +
            "            {" +
            "                \"id\": \"19ef31f1b98d477890cb3530d4c02569\"," +
            "                \"name\": \"首都信息\"," +
            "                \"vos\": [" +
            "                    {" +
            "                        \"appAlias\": \"智能会议室(APP)\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx004839db81bc846cf\"," +
            "                        \"appName\": \"智能会议室\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202111220304381462678393421115393.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202111220304381462678393421115393.jpg\"," +
            "                        \"sort\": 10210," +
            "                        \"type\": \"02\"," +
            "                        \"visibleRole\": \"0\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"复工复产申请表\"," +
            "                        \"appClassify\": \"\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx01904100e819045a3\"," +
            "                        \"appName\": \"复工复产申请表\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"0\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202205191227091526962604201820162.png\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202205191227091526962604201820162.png\"," +
            "                        \"sort\": 10111," +
            "                        \"type\": \"02\"," +
            "                        \"visibleRole\": \"0\"" +
            "                    }," +
            "                    {" +
            "                        \"appAlias\": \"工作周报app\"," +
            "                        \"appClassify\": \"app_classify_00\"," +
            "                        \"appGroup\": null," +
            "                        \"appId\": \"sx0682ec8e4312c4334\"," +
            "                        \"appName\": \"工作周报\"," +
            "                        \"appType\": \"00\"," +
            "                        \"isEmbeddedBrowser\": \"1\"," +
            "                        \"isOld\": \"0\"," +
            "                        \"isWx\": \"0\"," +
            "                        \"logoUrl\": \"http://gzttech.bsam.com.cn/file/staticFile/202204110722351513477609894273026.jpg\"," +
            "                        \"logoUrlGov\": \"http://gzttech.bsam.com.cn/file/staticFile/202204110722351513477609894273026.jpg\"," +
            "                        \"sort\": 10220," +
            "                        \"type\": \"02\"," +
            "                        \"visibleRole\": \"012\"" +
            "                    }" +
            "                ]" +
            "            }" +
            "        ]," +
            "        \"vos\": []" +
            "    }";

}