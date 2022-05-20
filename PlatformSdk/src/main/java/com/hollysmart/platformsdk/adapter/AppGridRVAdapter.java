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
import com.hollysmart.platformsdk.data.AppItem;
import com.hollysmart.platformsdk.interfaces.JsxInterface;
import com.hollysmart.platformsdk.views.CenterCropRoundCornerTransform;

import java.util.ArrayList;
import java.util.List;

public class AppGridRVAdapter extends RecyclerView.Adapter {

    private List<AppItem> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private JsxInterface.PlatformAppItemIF platformAppItemIF;

    public AppGridRVAdapter(Context context, @NonNull List<AppItem> data) {
        this.context = context;
        if (data != null) {
            this.data = data;
        }
        inflater = LayoutInflater.from(context);
    }

    public void setPlatformAppItemIF(JsxInterface.PlatformAppItemIF platformAppItemIF) {
        this.platformAppItemIF = platformAppItemIF;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new AppViewHolder(inflater.inflate(R.layout.layout_platform_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final AppViewHolder holder = (AppViewHolder) viewHolder;
        final AppItem fi = data.get(position);

        if (fi.functionType == -1) {
            setImage(fi.logoUrl, holder.iv);
        } else {
            Glide.with(context)
                    .load(fi.logoUrl)
                    .centerCrop()
                    .override(SizeUtils.dp2px(45), SizeUtils.dp2px(45))
                    .transform(new CenterCrop(), new CenterCropRoundCornerTransform(15))
                    .error(R.drawable.err_img)
                    .into(holder.iv);
        }

        holder.text.setText(fi.appName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (platformAppItemIF != null)
                    platformAppItemIF.onItem(fi);
            }
        });
    }

    public void setImage(String url, ImageView iv) {
        try {
            int rid = context.getResources().getIdentifier(url, "mipmap", context.getPackageName());
            iv.setImageResource(rid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
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
