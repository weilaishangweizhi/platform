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
import com.hollysmart.platformsdk.interfaces.JsxInterface;
import com.hollysmart.platformsdk.views.CenterCropRoundCornerTransform;

import java.util.ArrayList;
import java.util.List;


public class AppListRvAdapter extends RecyclerView.Adapter {

    private List<FunctionItem> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private JsxInterface.PlatformAddOrRemove addOrRemove;

    public AppListRvAdapter(Context context, @NonNull List<FunctionItem> data, JsxInterface.PlatformAddOrRemove addOrRemove) {
        this.context = context;
        if (data != null) {
            this.data = data;
        }
        inflater = LayoutInflater.from(context);
        this.addOrRemove = addOrRemove;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new AppViewHolder(inflater.inflate(R.layout.item_platform_list, parent, false));
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
                .override(SizeUtils.dp2px(45), SizeUtils.dp2px(45))
                .transform(new CenterCrop(), new CenterCropRoundCornerTransform(15))
                .error(R.drawable.err_img)
                .into(holder.iv);

        holder.tv_title.setText(fi.appName);
        holder.tv_value.setText(fi.appName);

        if (fi.isCommon){
            holder.tv_add.setVisibility(View.GONE);
            holder.tv_remove.setVisibility(View.VISIBLE);
        }else {
            holder.tv_add.setVisibility(View.VISIBLE);
            holder.tv_remove.setVisibility(View.GONE);
        }

        holder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrRemove.onAdd(fi);
            }
        });

        holder.tv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrRemove.onRemove(fi);
            }
        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView tv_title;
        private TextView tv_value;
        private TextView tv_add;
        private TextView tv_remove;


        public AppViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_value = itemView.findViewById(R.id.tv_value);
            tv_add = itemView.findViewById(R.id.tv_add);
            tv_remove = itemView.findViewById(R.id.tv_remove);
        }
    }

}
