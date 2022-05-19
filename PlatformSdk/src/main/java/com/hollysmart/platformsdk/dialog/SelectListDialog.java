package com.hollysmart.platformsdk.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hollysmart.platformsdk.R;
import com.hollysmart.platformsdk.data.CorpBean;
import com.hollysmart.platformsdk.views.linearlayoutforlistview.LinearLayoutBaseAdapter;
import com.hollysmart.platformsdk.views.linearlayoutforlistview.MyLinearLayoutForListView;

import java.util.List;

public class SelectListDialog {

    private Context mContext;
    private SelectAdapter popupAdapter;
    private CaiPopupWindow mPopupWindow;
    private PopupIF popupIF;

    public SelectListDialog(Context mContext, PopupIF popupIF) {
        this.mContext = mContext;
        initPopupWindow();
        this.popupIF = popupIF;
    }

    private MyLinearLayoutForListView ll_select;

    private void initPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_select_list, null);
        //适配7.0版本
        mPopupWindow = new CaiPopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setContentView(contentView);
        ll_select = contentView.findViewById(R.id.ll_select);

        //解决5.0以下版本点击外部不消失问题
        mPopupWindow.setOutsideTouchable(true);
        Drawable win_bg = mContext.getResources().getDrawable(R.color.white);
        mPopupWindow.setBackgroundDrawable(win_bg);
        mPopupWindow.setFocusable(true);

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissWindow();
            }
        });

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (popupIF != null)
                    popupIF.onDismissListener();
            }
        });
    }

    public void setPopupData(final List<CorpBean> popupInfoList) {
        if (popupAdapter == null) {
            popupAdapter = new SelectAdapter(mContext, popupInfoList);
            ll_select.setAdapter(popupAdapter);
        } else {
            popupAdapter.notifyDataSetChanged();
        }
        ll_select.setOnItemClickListener(new MyLinearLayoutForListView.OnItemClickListener() {
            @Override
            public void onItemClicked(View v, Object obj, int position) {
                if (popupIF != null) {
                    CorpBean tagBean = popupInfoList.get(position);
                    if (!tagBean.isSelect()) {
                        for (CorpBean bean : popupInfoList) {
                            if (bean.isSelect())
                                bean.setSelect(false);
                        }
                        tagBean.setSelect(true);
                        popupIF.item(popupInfoList.get(position), position);
                        dismissWindow();
                        popupAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    public void showPopuWindow(final View view) {
        mPopupWindow.showAsDropDown(view);
    }

    public void dismissWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    private class SelectAdapter extends LinearLayoutBaseAdapter {
        public SelectAdapter(Context context, List<?> list) {
            super(context, list);
        }

        @Override
        public View getView(int position) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_select_textview, null);
            ImageView iv_select_tag = itemView.findViewById(R.id.iv_select_tag);
            TextView tv_select = itemView.findViewById(R.id.tv_select);
            CorpBean tagBean = (CorpBean) getItem(position);
            if (tagBean.isSelect()) {
                iv_select_tag.setVisibility(View.VISIBLE);
                tv_select.setTextColor(mContext.getResources().getColor(R.color.blue_color));
            } else {
                iv_select_tag.setVisibility(View.INVISIBLE);
                tv_select.setTextColor(mContext.getResources().getColor(R.color.black));
            }
            tv_select.setText(tagBean.getCorpName());
            return itemView;
        }
    }

    public interface PopupIF {
        void onDismissListener();

        void item(CorpBean bean, int position);
    }


}













