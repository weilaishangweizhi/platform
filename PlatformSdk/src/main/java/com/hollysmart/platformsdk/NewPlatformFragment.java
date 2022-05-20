package com.hollysmart.platformsdk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.hollysmart.platformsdk.adapter.AppCommonGridRVAdapter;
import com.hollysmart.platformsdk.data.AppItem;
import com.hollysmart.platformsdk.data.AppModel;
import com.hollysmart.platformsdk.data.CommonData;
import com.hollysmart.platformsdk.data.SelectBean;
import com.hollysmart.platformsdk.dialog.SelectListDialog;
import com.hollysmart.platformsdk.eventbus.EB_Platform_Common;
import com.hollysmart.platformsdk.eventbus.EB_Platform_Refresh;
import com.hollysmart.platformsdk.interfaces.JsxInterface;
import com.hollysmart.platformsdk.utils.Mlog;
import com.hollysmart.platformsdk.views.indicator.ScaleTransitionPagerTitleView;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class NewPlatformFragment extends Fragment implements OnRefreshListener, View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_platform_new, container, false);
        EventBus.getDefault().register(this);
        findView(root);
        init();
        return root;
    }

    private LinearLayout ll_title;
    private TextView tv_title;
    private ImageView iv_arrow;
    private View view_bg;
    private SmartRefreshLayout refreshLayout;
    private ViewPager vp_data;
    private RecyclerView rv_common;
    private MagicIndicator indicator;
    private LinearLayout ll_common_add;
    private ImageView iv_platform;

    private void findView(View root) {
        ll_title = root.findViewById(R.id.ll_title);
        ll_title.setOnClickListener(this);
        tv_title = root.findViewById(R.id.tv_title);
        iv_arrow = root.findViewById(R.id.iv_arrow);
        view_bg = root.findViewById(R.id.view_bg);

        refreshLayout = root.findViewById(R.id.smart_refresh);
        //添加刷新监听
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableHeaderTranslationContent(true);//是否下拉Header的时候向下平移列表或者内容
        refreshLayout.setEnableFooterTranslationContent(false);//是否上拉Footer的时候向上平移列表或者内容
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setOnRefreshListener(this);

        rv_common = root.findViewById(R.id.rv_common);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 5);
        rv_common.setLayoutManager(gridLayoutManager);
        indicator = root.findViewById(R.id.indicator);
        vp_data = root.findViewById(R.id.vp_data);

        ll_common_add = root.findViewById(R.id.ll_common_add);
        ll_common_add.setOnClickListener(this::onClick);
        iv_platform = root.findViewById(R.id.iv_platform);
    }

    private Animation down2upRotate;
    private Animation up2downRotate;
    private SelectListDialog selectListDialog;
    private BasePagerAdapter adapter;
    private AppCommonGridRVAdapter commonAdapter;
    private AppModel appModel;
    private List<AppItem> commonApps;  //常用应用列表
    private String commonKey = "defaule";
    private JsxInterface.PlatformAppItemIF platformAppItemIF;

    private void init() {
        down2upRotate = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_down2up);//创建动画
        down2upRotate.setInterpolator(new LinearInterpolator());//设置为线性旋转
        down2upRotate.setFillAfter(!down2upRotate.getFillAfter());//每次都取相反值，使得可以不恢复原位的旋转
        up2downRotate = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_up2down);//创建动画
        up2downRotate.setInterpolator(new LinearInterpolator());//设置为线性旋转
        up2downRotate.setFillAfter(!up2downRotate.getFillAfter());//每次都取相反值，使得可以不恢复原位的旋转
        selectListDialog = new SelectListDialog(getContext(), selectIF);
    }

    /**
     * 设置应用列表
     */
    public void initAllData(boolean isRefresh, AppModel data) {
        appModel = data;
        initCommon();
        commonTag();
        if (isRefresh && refreshLayout != null)
            refreshLayout.finishRefresh();
    }

    /**
     * 设置title筛选
     * @param selectBeanList
     */
    public void setSelectTitle(List<SelectBean> selectBeanList){
        tv_title.setText(selectBeanList.get(0).getName());
        selectListDialog.setPopupData(selectBeanList);
    }

    /**
     * 设置图片
     * @param imgUrl
     */
    public void setPlatform(String imgUrl){
        Glide.with(this)
                .load(imgUrl)
                .error(R.drawable.icon_platform)
                .into(iv_platform);
    }

    /**
     * 设置应用点击事件监听
     * @param platformAppItemIF
     */
    public void setPlatformAppItemIF(JsxInterface.PlatformAppItemIF platformAppItemIF) {
        this.platformAppItemIF = platformAppItemIF;
    }

    private SelectListDialog.PopupIF selectIF = new SelectListDialog.PopupIF() {
        @Override
        public void onDismissListener() {
            iv_arrow.startAnimation(up2downRotate);
            view_bg.setVisibility(View.GONE);
        }

        @Override
        public void item(SelectBean bean, int position) {
            refreshLayout.autoRefresh();
        }
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_title) {
            iv_arrow.startAnimation(down2upRotate);
            selectListDialog.showPopuWindow(ll_title);
            view_bg.setVisibility(View.VISIBLE);
        } else if (id == R.id.ll_common_add) {
            Intent intent = new Intent(getContext(), CommonAddActivity.class);
            intent.putExtra("commons", (Serializable) commonApps);
            intent.putExtra("appModle", appModel);
            startActivity(intent);
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        Mlog.d("刷新");
        EventBus.getDefault().post(new EB_Platform_Refresh());
    }

    /**
     * 设置常用标签
     */
    private void commonTag() {
        //判断第一次进入改版后的工作台页面。默认设置全部应用的前九个应用为常用应用
        if (commonApps.size() == 0 && appModel != null
                && appModel.getCustomGrouping().get(0).vos != null
                && appModel.getCustomGrouping().get(0).vos.size() > 0
                && CommonData.isFristSetCommon()) {

            //将全部应用的前9个应用设置为常用应用不足9个是取全部
            commonApps.addAll(appModel.getCustomGrouping().get(0).vos.
                    subList(0, appModel.getCustomGrouping().get(0).vos.size() > 9 ?
                            8 : appModel.getCustomGrouping().get(0).vos.size()));

            ll_common_add.setVisibility(View.GONE);
            rv_common.setVisibility(View.VISIBLE);
            commonAdapter.notifyDataSetChanged();
            CommonData.saveCommon(commonKey, commonApps);
        }

        //清除常用应用中已经不是全部列表中的应用
        delApp(appModel.getCustomGrouping().get(0).vos, commonApps);

        //同步全部列表 标记常用标签
        for (AppItem item : appModel.getVos()) {
            if (CommonData.contains(commonApps, item)) {
                item.isCommon = true;
            }
        }
        //同步所有分类列表 标记常用标签
        for (AppModel.Custom custom : appModel.getCustomGrouping()) {
            for (AppItem item : custom.vos) {
                if (CommonData.contains(commonApps, item)) {
                    item.isCommon = true;
                }
            }
        }
        adapter = new BasePagerAdapter(getChildFragmentManager());
        vp_data.setAdapter(adapter);
        initMagicIndicator();
    }

    /**
     * 测试删除某个app
     *
     * @param appName
     */
    private void testDelData(String appName) {
        Iterator<AppItem> aData = appModel.getCustomGrouping().get(0).vos.iterator();
        while (aData.hasNext()) {
            AppItem item = aData.next();
            if (TextUtils.equals(appName, item.appName)) {
                aData.remove();
            }
        }
    }


    /**
     * 删除不存在的应用,并保持到 缓存文件中
     *
     * @param allApp  接口获取的全部应用
     * @param commons 本地的常用应用
     */
    private void delApp(List<AppItem> allApp, List<AppItem> commons) {
        boolean hasDel = false;
        Iterator<AppItem> common = commons.iterator();
        while (common.hasNext()) {
            AppItem item = common.next();
            if (!CommonData.contains(allApp, item)) {
                Mlog.d("清除了" + item.appName);
                common.remove();
                hasDel = true;
            }
        }
        if (hasDel) {
            //清除常用应用后  刷新 adapter并保存新的常用应用列表
            commonAdapter.notifyDataSetChanged();
            CommonData.saveCommon(commonKey, commons);
        }
    }

    private class BasePagerAdapter extends FragmentStatePagerAdapter {
        public BasePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            AppGridFragment appGridFragment = new AppGridFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", (Serializable) appModel.getCustomGrouping().get(position).vos);
            appGridFragment.setArguments(bundle);
            appGridFragment.setPlatformAppItemIF(platformAppItemIF);
            return appGridFragment;
        }

        @Override
        public int getCount() {
            return appModel.getCustomGrouping().size();
        }
    }

    /**
     * 栏目数据
     */
    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return appModel.getCustomGrouping().size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                ScaleTransitionPagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setMinScale(1f);
                simplePagerTitleView.setText(appModel.getCustomGrouping().get(index).name);
                int padding = UIUtil.dip2px(context, 10);
                simplePagerTitleView.setPadding(padding, 0, padding, 0);
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                simplePagerTitleView.setNormalColor(getContext().getResources().getColor(R.color.black3));
                simplePagerTitleView.setSelectedColor(getContext().getResources().getColor(R.color.blue_color));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vp_data.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.FOCUSABLES_TOUCH_MODE);
                indicator.setLineHeight(UIUtil.dip2px(context, 3));
                indicator.setLineWidth(UIUtil.dip2px(context, 12));
                indicator.setRoundRadius(UIUtil.dip2px(context, 1));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(getContext().getResources().getColor(R.color.blue_color));
                return indicator;
            }
        });
        indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(indicator, vp_data);
    }


    /**
     * 初始化常用列表
     */
    private void initCommon() {
        commonApps = CommonData.getCommon(commonKey);
        if (commonApps.size() > 0) {
            ll_common_add.setVisibility(View.GONE);
            rv_common.setVisibility(View.VISIBLE);
        } else {
            ll_common_add.setVisibility(View.VISIBLE);
            rv_common.setVisibility(View.GONE);
        }
        if (commonAdapter == null) {
            commonAdapter = new AppCommonGridRVAdapter(getContext(), commonApps, new JsxInterface.PlatFormCommonItemIF() {
                @Override
                public void onItem(AppItem appItem) {
                    if (platformAppItemIF != null)
                        platformAppItemIF.onItem(appItem);
                }

                @Override
                public void onMore() {
                    Intent intent = new Intent(getContext(), CommonAddActivity.class);
                    intent.putExtra("commons", (Serializable) commonApps);
                    intent.putExtra("appModle", appModel);
                    startActivity(intent);
                }
            });
            rv_common.setAdapter(commonAdapter);
        } else {
            commonAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 接收常用应用改变信息
     *
     * @param eb_common
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void commonChange(EB_Platform_Common eb_common) {
        if (eb_common.type == EB_Platform_Common.ADD) {
            for (AppItem item : commonApps) {
                //常用列表中已经有选中应用就不在添加
                if (item.appId.equals(eb_common.app.appId)) {
                    return;
                }
            }
            commonApps.add(eb_common.app);

            if (commonApps.size() > 0) {
                ll_common_add.setVisibility(View.GONE);
                rv_common.setVisibility(View.VISIBLE);
            }

            //同步常用应用数据
            synchronizationCommon(eb_common.app, true);
        } else if (eb_common.type == EB_Platform_Common.REMOVE) {
            Iterator<AppItem> commonApp = commonApps.iterator();
            while (commonApp.hasNext()) {
                AppItem item = commonApp.next();
                if (item.appName.equals(eb_common.app.appName)) {
                    commonApp.remove();
                }
            }

            if (commonApps.size() == 0) {
                ll_common_add.setVisibility(View.VISIBLE);
                rv_common.setVisibility(View.GONE);
            }

            //同步常用应用数据
            synchronizationCommon(eb_common.app, false);
        } else if (eb_common.type == EB_Platform_Common.SORT) {
            commonApps.clear();
            commonApps.addAll(eb_common.commons);

            //同步全部列表 标记常用标签
            for (AppItem item : appModel.getVos()) {
                if (CommonData.contains(commonApps, item)) {
                    item.isCommon = true;
                } else {
                    item.isCommon = false;
                }
            }
            //同步所有分类列表 标记常用标签
            for (AppModel.Custom custom : appModel.getCustomGrouping()) {
                for (AppItem item : custom.vos) {
                    if (CommonData.contains(commonApps, item)) {
                        item.isCommon = true;
                    } else {
                        item.isCommon = false;
                    }
                }
            }

        }
        CommonData.saveCommon(commonKey, commonApps);
        commonAdapter.notifyDataSetChanged();
    }


    /**
     * 同步常用应用数据
     *
     * @param app
     * @param isCommon
     */
    private void synchronizationCommon(AppItem app, boolean isCommon) {
        for (AppItem item : appModel.getVos()) {
            if (item.appName.equals(app.appName)) {
                item.isCommon = isCommon;
            }
        }

        for (AppModel.Custom custom : appModel.getCustomGrouping()) {
            for (AppItem item : custom.vos) {
                if (item.appName.equals(app.appName)) {
                    item.isCommon = isCommon;
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
























