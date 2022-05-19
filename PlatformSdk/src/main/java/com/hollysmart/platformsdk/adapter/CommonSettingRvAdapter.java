package com.hollysmart.platformsdk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.hollysmart.platformsdk.R;
import com.hollysmart.platformsdk.editmenu.FunctionItem;
import com.hollysmart.platformsdk.views.CenterCropRoundCornerTransform;

import java.util.List;


public class CommonSettingRvAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<FunctionItem> data;
    private LayoutInflater inflater;

    public CommonSettingRvAdapter(Context context, @NonNull List<FunctionItem> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new AppViewHolder(inflater.inflate(R.layout.item_platform_common_setting, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final AppViewHolder holder = (AppViewHolder) viewHolder;
        final FunctionItem fi = data.get(position);
        Glide
                .with(context)
                .load(fi.logoUrl)
                .centerCrop()
                .override(SizeUtils.dp2px( 35), SizeUtils.dp2px( 35))
                .transform(new CenterCrop(), new CenterCropRoundCornerTransform(10))
                .error(R.drawable.err_img)
                .into(holder.iv);

        holder.tv_title.setText(fi.appName);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_remove;
        private ImageView iv;
        private TextView tv_title;

        public AppViewHolder(View itemView) {
            super(itemView);
            iv_remove = itemView.findViewById(R.id.iv_remove);
            iv = itemView.findViewById(R.id.iv);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }

}
