package com.hollysmart.platformsdk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.hollysmart.platformsdk.adapter.CommonRvAdapter;
import com.hollysmart.platformsdk.data.AppItem;
import com.hollysmart.platformsdk.data.AppModel;
import com.hollysmart.platformsdk.eventbus.EB_Platform_Common;
import com.hollysmart.platformsdk.style.CaiActivity;
import com.hollysmart.platformsdk.views.indicator.ScaleTransitionPagerTitleView;

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

import butterknife.BindView;
import butterknife.OnClick;

public class CommonAddActivity extends CaiActivity {

    @BindView(R2.id.tv_num)
    TextView tv_num;
    @BindView(R2.id.rv_common)
    RecyclerView rv_common;
    @BindView(R2.id.indicator)
    MagicIndicator indicator;
    @BindView(R2.id.vp_data)
    ViewPager vp_data;

    @Override
    public int layoutResID() {
        return R.layout.activity_platform_common_add;
    }

    @Override
    public void findView() {
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("添加常用");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_common.setLayoutManager(linearLayoutManager);
    }

    private List<AppItem> commonData;
    private AppModel appModel;
    private BasePagerAdapter adapter;
    private CommonRvAdapter commonRvAdapter;

    @Override
    public void init() {
        EventBus.getDefault().register(this);
        commonData = (List<AppItem>) getIntent().getSerializableExtra("commons");
        commonRvAdapter = new CommonRvAdapter(this, commonData);
        rv_common.setAdapter(commonRvAdapter);
        tv_num.setText(String.format("已添加(%d)", commonData.size()));
        appModel = (AppModel) getIntent().getSerializableExtra("appModle");
        adapter = new BasePagerAdapter(getSupportFragmentManager());
        vp_data.setOffscreenPageLimit(appModel.getCustomGrouping().size());
        vp_data.setAdapter(adapter);
        initMagicIndicator();
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @OnClick(R2.id.iv_back)
    public void backOnClick(View view) {
        finish();
    }

    @OnClick(R2.id.ll_setting)
    public void settingOnClick(View view) {
        Intent intent = new Intent(this, CommonSettingActivity.class);
        intent.putExtra("commons", (Serializable) commonData);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void commonChange(EB_Platform_Common common) {
        if (common.type == EB_Platform_Common.ADD) {
            for (AppItem item : commonData) {
                //常用列表中已经有选中应用就不在添加
                if (item.appId.equals(common.app.appId)) {
                    return;
                }
            }
            commonData.add(common.app);
        } else if (common.type == EB_Platform_Common.REMOVE) {
            Iterator<AppItem> commonApp = commonData.iterator();
            while (commonApp.hasNext()) {
                AppItem item = commonApp.next();
                if (item.appName.equals(common.app.appName)) {
                    commonApp.remove();
                }
            }

        } else if (common.type == EB_Platform_Common.SORT) {
            commonData.clear();
            commonData.addAll(common.commons);
        }

        commonRvAdapter.notifyDataSetChanged();
        tv_num.setText(String.format("已添加(%d)", commonData.size()));
    }


    private class BasePagerAdapter extends FragmentStatePagerAdapter {
        public BasePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            AppListFragment appListFragment = new AppListFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", (Serializable) appModel.getCustomGrouping().get(position).vos);
            appListFragment.setArguments(bundle);
            return appListFragment;
        }

        @Override
        public int getCount() {
            return appModel.getCustomGrouping().size();
        }
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
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
                simplePagerTitleView.setTextSize(15);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.black3));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.blue_color));
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
                indicator.setColors(getResources().getColor(R.color.blue_color));
                return indicator;
            }
        });
        indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(indicator, vp_data);
    }

}


