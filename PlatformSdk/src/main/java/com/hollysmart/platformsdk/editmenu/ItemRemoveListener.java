package com.hollysmart.platformsdk.editmenu;

import android.content.Context;
import android.view.View;

import java.util.List;

public class ItemRemoveListener implements FunctionBlockAdapter.OnItemRemoveListener {

    private Context mContext;
    private List<FunctionItem> selData;
    private List<FunctionItem> noSelData;
    private List<FunctionItem> allData;
    private FunctionAdapter functionAdapter;
    private View view_shang;
    private View view_xia;


    public ItemRemoveListener(Context mContext, List<FunctionItem> selData, List<FunctionItem> noSelData,
                              List<FunctionItem> allData, FunctionAdapter functionAdapter, View view_shang, View view_xia) {
        this.mContext = mContext;
        this.selData = selData;
        this.noSelData = noSelData;
        this.allData = allData;
        this.functionAdapter = functionAdapter;
        this.view_shang = view_shang;
        this.view_xia = view_xia;
    }

    @Override
    public void remove(FunctionItem item) {
        // 更新所有列表，选择列表已在内部进行更新
        try {
            if (item != null && item.appName != null) {
                for (int i = 0; i < allData.size(); i++) {
                    FunctionItem data = allData.get( i );
                    if (data != null && data.appName != null) {
                        if (item.appName.equals( data.appName )) {
                            data.isSelect = false;
                            item.isSelect = false;
                            break;
                        }
                    }
                }
            }

            noSelData.add( item );
            functionAdapter.notifyDataSetChanged();

            if (selData.size() == 0) {
                view_shang.setVisibility( View.GONE );
            }

            if (noSelData.size() != 0) {
                view_xia.setVisibility( View.VISIBLE );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}