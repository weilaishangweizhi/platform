package com.hollysmart.platformsdk.editmenu;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class RvItemClick extends OnRecyclerItemClickListener {

    private Context mContext;
    private ItemTouchHelper itemTouchHelper;

    public RvItemClick(RecyclerView recyclerView, Context mContext, ItemTouchHelper itemTouchHelper) {
        super( recyclerView );
        this.mContext = mContext;
        this.itemTouchHelper = itemTouchHelper;
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder vh) {
//                Toast.makeText(mContext, datas.get(vh.getLayoutPosition()).getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(RecyclerView.ViewHolder vh) {
        //判断被拖拽的是否是前两个，如果不是则执行拖拽
        itemTouchHelper.startDrag( vh );
        //获取系统震动服务
        Vibrator vib = (Vibrator) mContext.getSystemService( Service.VIBRATOR_SERVICE );//震动70毫秒
        vib.vibrate( 70 );

    }
}