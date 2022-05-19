package com.hollysmart.platformsdk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hollysmart.platformsdk.adapter.AppListRvAdapter;
import com.hollysmart.platformsdk.data.CommonData;
import com.hollysmart.platformsdk.editmenu.FunctionItem;
import com.hollysmart.platformsdk.eventbus.EB_Platform_Common;
import com.hollysmart.platformsdk.interfaces.JsxInterface;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class AppListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.layout_only_rv, container, false);
        findView(root);
        init();
        return root;
    }

    private RecyclerView rv_app;

    private void findView(View root) {
        rv_app = root.findViewById(R.id.rv_app);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_app.setLayoutManager(linearLayoutManager);
    }

    private List<FunctionItem> appList;
    private AppListRvAdapter adapter;

    private void init() {
        EventBus.getDefault().register(this);
        appList = (List<FunctionItem>) getArguments().getSerializable("data");
        adapter = new AppListRvAdapter(getContext(), appList, new JsxInterface.PlatformAddOrRemove() {
            @Override
            public void onAdd(FunctionItem functionItem) {
                EventBus.getDefault().post(new EB_Platform_Common(EB_Platform_Common.ADD, functionItem));
            }

            @Override
            public void onRemove(FunctionItem functionItem) {
                EventBus.getDefault().post(new EB_Platform_Common(EB_Platform_Common.REMOVE, functionItem));
            }
        });
        rv_app.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void commonChange(EB_Platform_Common eb_common) {
        if (eb_common.type == EB_Platform_Common.ADD) {
            for (FunctionItem item : appList) {
                if (item.appId.equals(eb_common.app.appId)) {
                    item.isCommon = true;
                    adapter.notifyDataSetChanged();
                }
            }

        } else if (eb_common.type == EB_Platform_Common.REMOVE) {
            for (FunctionItem item : appList) {
                if (item.appId.equals(eb_common.app.appId)) {
                    item.isCommon = false;
                    adapter.notifyDataSetChanged();
                }
            }
        } else if (eb_common.type == EB_Platform_Common.SORT) {
            for (FunctionItem item : appList) {
                if (CommonData.contains(eb_common.commons, item)) {
                    item.isCommon = true;
                } else {
                    item.isCommon = false;
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
