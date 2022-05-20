package com.hollysmart.platformsdk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.hollysmart.platformsdk.R;
import com.hollysmart.platformsdk.data.AppItem;
import com.hollysmart.platformsdk.views.CenterCropRoundCornerTransform;

import java.util.ArrayList;
import java.util.List;


public class CommonRvAdapter extends RecyclerView.Adapter {

    private List<AppItem> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public CommonRvAdapter(Context context, @NonNull List<AppItem> data) {
        this.context = context;
        if (data != null) {
            this.data = data;
        }
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new AppViewHolder(inflater.inflate(R.layout.item_platform_common_image, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final AppViewHolder holder = (AppViewHolder) viewHolder;
        final AppItem fi = data.get(position);
        Glide.with(context)
                .load(fi.logoUrl)
                .centerCrop()
                .transform(new CenterCrop(), new CenterCropRoundCornerTransform(8))
                .error(R.drawable.err_img)
                .into(holder.iv);

    }

    @Override
    public int getItemCount() {
        return data.size() > 7 ? 7 : data.size();
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;

        public AppViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
        }
    }

}
