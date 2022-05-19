package com.hollysmart.platformsdk.editmenu;

import android.content.Context;
import android.view.View;

import java.util.List;

public class ItemAddListener implements FunctionAdapter.OnItemAddListener {


    private Context mContext;
    private List<FunctionItem> selData;
    private List<FunctionItem> noSelData;
    private List<FunctionItem> allData;
    private FunctionBlockAdapter blockAdapter;
    private FunctionAdapter functionAdapter;
    private View view_shang;
    private View view_xia;

    public ItemAddListener(Context mContext, List<FunctionItem> selData, List<FunctionItem> noSelData,
                           List<FunctionItem> allData, FunctionBlockAdapter blockAdapter,
                           FunctionAdapter functionAdapter, View view_shang, View view_xia) {
        this.mContext = mContext;
        this.selData = selData;
        this.noSelData = noSelData;
        this.allData = allData;
        this.blockAdapter = blockAdapter;
        this.functionAdapter = functionAdapter;
        this.view_shang = view_shang;
        this.view_xia = view_xia;
    }

    @Override
    public boolean add(FunctionItem item) {
        if (selData != null) {   // 更新选择列表，所有列表已在内部进行更新
            try {
                if (item != null && item.appName != null) {
                    for (int i = 0; i < allData.size(); i++) {
                        FunctionItem data = allData.get( i );
                        if (data != null && data.appName != null) {
                            if (item.appName.equals( data.appName )) {
                                data.isSelect = true;
                                break;
                            }
                        }
                    }
                }

                //更新选中列表
                selData.add( item );
//                        resetEditHeight( rv_bangong_exist, bangong_SelData.size() );
                blockAdapter.notifyDataSetChanged();
                item.isSelect = true;

                //更新未选中列表
                noSelData.remove( item );
                functionAdapter.notifyDataSetChanged();

                if (selData.size() != 0) {
                    view_shang.setVisibility( View.VISIBLE );
                }

                if (noSelData.size() == 0) {
                    view_xia.setVisibility( View.GONE );
                }

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}