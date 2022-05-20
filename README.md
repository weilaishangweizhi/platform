# 工作台模块

## Gradle引用

```
allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```

```
    implementation 'com.github.weilaishangweizhi:platform:Tag'//tag替换最新版本号
```

## 初始化

- **Application中配置**

```
        PlatformSdk.getInstance().setApplication(this).setLog("com.test");  
        Utils.init(this);
```

- **工作台Fragment引用实例
  
  MainActivity

```
  public class MainActivity extends AppCompatActivity {
    //工作台Fragment
    private NewPlatformFragment fragment;
    //判断是否是刷新操作
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

        //绑定工作台中应用点击事件
        fragment.setPlatformAppItemIF(new JsxInterface.PlatformAppItemIF() {
            @Override
            public void onItem(FunctionItem appItem) {
                Mlog.d("点击了App");
                ToastUtils.showShort("点击了:" + appItem.appName);
            }
        });
        //获取数据
        getData();
    }

    /**
     * 接收刷新事件
     * @param refresh
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(EB_Platform_Refresh refresh) {
        isRefresh = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 1000);
    }

    /**
     * 获取数据，传入工作台模块中
     */
    private void getData() {
        //此处是模拟数据，应该调用接口获取
        AppModel appModel = new Gson().fromJson(apps, AppModel.class);
        //获得数据后，传入工作台模块
        fragment.initAllData(isRefresh, appModel);
    }

      /**
     * 获取标题栏数据，传入工作台
     */
    private void getSelect(){
        List<SelectBean> selectBeanList = new ArrayList<>();
        SelectBean selectBean = new SelectBean();
        selectBean.setId("1");
        selectBean.setName("标题一");
        selectBeanList.add(selectBean);
        selectBean = new SelectBean();
        selectBean.setId("2");
        selectBean.setName("标题二");
        selectBeanList.add(selectBean);
        //获得数据后，传入工作台模块
        fragment.setSelectTitle(selectBeanList);
    }
  
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

  //工作台数据模型，数据模型不能改变。
  private String apps = "{" +
            "        \"customGrouping\":[" +
            "            {" +
            "                \"id\":\"ungrouped\"," +
            "                \"name\":\"全部\"," +
            "                \"vos\":[" +
            "                    {" +
            "                        \"appAlias\":\"OA待办\"," +
            "                        \"appClassify\":\"app_classify_00\"," +
            "                        \"appGroup\":null," +
            "                        \"appId\":\"sx07047cfa6383e4a90\"," +
            "                        \"appName\":\"待办\"," +
            "                        \"appType\":\"00\"," +
            "                        \"isEmbeddedBrowser\":\"\"," +
            "                        \"isOld\":\"0\"," +
            "                        \"isWx\":\"0\"," +
            "                        \"logoUrl\":\"http://gzttech.bsam.com.cn/file/staticFile/202202231158191496514771107803138.jpg\"," +
            "                        \"logoUrlGov\":\"http://gzttech.bsam.com.cn/file/staticFile/202202231158191496514771107803138.jpg\"," +
            "                        \"sort\":2," +
            "                        \"type\":\"01\"," +
            "                        \"visibleRole\":\"0\"" +
            "                    }" +
            "                ]" +
            "            }" +
            "        ]," +
            "        \"vos\":[" +
            "" +
            "        ]" +
            "    }";
```

activity_main.xml

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <FrameLayout
        android:id="@+id/frameLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</LinearLayout>
```
