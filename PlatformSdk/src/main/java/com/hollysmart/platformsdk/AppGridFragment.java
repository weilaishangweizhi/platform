package com.hollysmart.platformsdk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.hollysmart.platformsdk.adapter.AppGridRVAdapter;
import com.hollysmart.platformsdk.data.AppItem;
import com.hollysmart.platformsdk.interfaces.JsxInterface;
import java.util.List;

public class AppGridFragment extends Fragment {
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 5);
        rv_app.setLayoutManager(gridLayoutManager);
    }

    private List<AppItem> appList;
    private AppGridRVAdapter adapter;
    private JsxInterface.PlatformAppItemIF platformAppItemIF;

    private void init() {
        appList = (List<AppItem>) getArguments().getSerializable("data");
        adapter = new AppGridRVAdapter(getContext(), appList);
        adapter.setPlatformAppItemIF(platformAppItemIF);
        rv_app.setAdapter(adapter);
    }

    public void setPlatformAppItemIF(JsxInterface.PlatformAppItemIF platformAppItemIF) {
        this.platformAppItemIF = platformAppItemIF;
    }
}
