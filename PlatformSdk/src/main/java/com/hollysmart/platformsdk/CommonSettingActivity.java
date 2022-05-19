package com.hollysmart.platformsdk;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.hollysmart.platformsdk.adapter.CommonSettingRvAdapter;
import com.hollysmart.platformsdk.editmenu.FunctionItem;
import com.hollysmart.platformsdk.eventbus.EB_Platform_Common;
import com.hollysmart.platformsdk.style.CaiActivity;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.List;


public class CommonSettingActivity extends CaiActivity {

    private SwipeRecyclerView rv_common;
    @Override
    public int layoutResID() {
        return R.layout.activity_platform_common_setting;
    }

    @Override
    public void findView() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("工作台设置");
        rv_common = findViewById(R.id.rv_common);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_common.setLayoutManager(linearLayoutManager);
    }

    private List<FunctionItem> commonData;
    private CommonSettingRvAdapter adapter;
    @Override
    public void init() {
        commonData = (List<FunctionItem>) getIntent().getSerializableExtra("commons");
        adapter = new CommonSettingRvAdapter(this, commonData);
        rv_common.setSwipeMenuCreator(menuCreator);
        rv_common.setOnItemMenuClickListener(mItemMenuClickListener);
        rv_common.setLongPressDragEnabled(true);
        rv_common.setOnItemMoveListener(mItemMoveListener);
        rv_common.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int adapterPosition) {
                rv_common.smoothCloseMenu();
                rv_common.smoothOpenRightMenu(adapterPosition);
            }
        });
        rv_common.setAdapter(adapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back){
            finish();
        }else if (id == R.id.tv_right){
            EventBus.getDefault().post(new EB_Platform_Common(EB_Platform_Common.SORT, commonData));
            finish();
        }
    }

    SwipeMenuCreator menuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
            int width = SizeUtils.dp2px(80);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(mContext); // 各种文字和图标属性设置。
            deleteItem.setText("移除");
            deleteItem.setBackground(R.color.red8);
            deleteItem.setTextColorResource(R.color.white);
            deleteItem.setWidth(width);
            deleteItem.setHeight(height);
            rightMenu.addMenuItem(deleteItem); // 在Item右侧添加一个菜单。
        }
    };

    OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            // 左侧还是右侧菜单：
            int direction = menuBridge.getDirection();
            // 菜单在Item中的Position：
            int menuPosition = menuBridge.getPosition();


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //更新列表
                    commonData.remove(position);
                    adapter.notifyDataSetChanged();
                }
            }, 300);
        }
    };

    OnItemMoveListener mItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
            // 此方法在Item拖拽交换位置时被调用。
            // 第一个参数是要交换为之的Item，第二个是目标位置的Item。

            // 交换数据，并更新adapter。
            int fromPosition = srcHolder.getAbsoluteAdapterPosition();
            int toPosition = targetHolder.getAbsoluteAdapterPosition();
            Collections.swap(commonData, fromPosition, toPosition);
            adapter.notifyItemMoved(fromPosition, toPosition);
            // 返回true，表示数据交换成功，ItemView可以交换位置。
            return true;
        }

        @Override
        public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
        }
    };

}


