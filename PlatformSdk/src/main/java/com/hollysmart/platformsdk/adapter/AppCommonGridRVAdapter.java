package com.hollysmart.platformsdk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.hollysmart.platformsdk.R;
import com.hollysmart.platformsdk.editmenu.FunctionItem;
import com.hollysmart.platformsdk.interfaces.JsxInterface;
import com.hollysmart.platformsdk.views.CenterCropRoundCornerTransform;

import java.util.List;


public class AppCommonGridRVAdapter extends RecyclerView.Adapter {

    private List<FunctionItem> data;
    private Context context;
    private JsxInterface.PlatFormCommonItemIF commonItemIF;

    public AppCommonGridRVAdapter(Context context, @NonNull List<FunctionItem> data, JsxInterface.PlatFormCommonItemIF commonItemIF) {
        this.context = context;
        this.data = data;
        this.commonItemIF = commonItemIF;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new AppViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_platform_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final AppViewHolder holder = (AppViewHolder) viewHolder;
        if (position == data.size()) {
            holder.iv.setImageResource(R.drawable.icon_add_platform);
            holder.text.setText("添加");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commonItemIF.onMore();
                }
            });
        } else {
            final FunctionItem fi = data.get(position);
            Glide.with(context)
                    .load(fi.logoUrl)
                    .centerCrop()
                    .transform(new CenterCrop(), new CenterCropRoundCornerTransform(SizeUtils.dp2px(15)))
                    .error(R.drawable.err_img)
                    .into(holder.iv);

            holder.text.setText(fi.appName);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commonItemIF.onItem(fi);
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView text;

        private LinearLayout ll_download;
        private ProgressBar pb_download;

        public AppViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            text = itemView.findViewById(R.id.text);
            ll_download = itemView.findViewById(R.id.ll_download);
            pb_download = itemView.findViewById(R.id.pb_download);
        }
    }

}
