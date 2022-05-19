package com.hollysmart.platformsdk.editmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hollysmart.platformsdk.R;

import java.util.ArrayList;
import java.util.List;


public class FunctionAdapter extends RecyclerView.Adapter {
    private List<FunctionItem> data = new ArrayList<>();

    private LayoutInflater inflater;
    private Context context;

    public FunctionAdapter(Context context, @NonNull List<FunctionItem> data) {
        this.context = context;
        if (data != null) {
            this.data = data;
        }
        inflater = LayoutInflater.from( context );
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        return new FunctionViewHolder( inflater.inflate( R.layout.layout_edit_memu_item, parent, false ) );
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final int index = position;
        FunctionViewHolder holder = (FunctionViewHolder) viewHolder;
        FunctionItem fi = data.get(position);
//        setImage( fi.logoUrl, holder.iv );

        Glide.with(context)
                .load(fi.logoUrl)
                .apply(new RequestOptions().error(R.drawable.err_img))
                .into(holder.iv);

        holder.text.setText(fi.appName);
        holder.btn.setImageResource(fi.isSelect ? R.drawable.ic_block_selected : R.drawable.ic_block_add);

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FunctionItem f = data.get(index);
                if (!f.isSelect) {
                    if (listener != null) {
                        if (listener.add( f )) {
                            f.isSelect = true;
                        }
                    }
                }
            }
        } );
    }

    public void setImage(String url, ImageView iv) {
        try {
            int rid = context.getResources().getIdentifier( url, "drawable", context.getPackageName() );
            iv.setImageResource( rid );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class FunctionViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv, btn;
        private TextView text;

        public FunctionViewHolder(View itemView) {
            super( itemView );
            iv = (ImageView) itemView.findViewById( R.id.iv );
            text = (TextView) itemView.findViewById( R.id.text );
            btn = (ImageView) itemView.findViewById( R.id.btn );
        }
    }

    public interface OnItemAddListener {
        boolean add(FunctionItem item);
    }

    private OnItemAddListener listener;

    public void setOnItemAddListener(OnItemAddListener listener) {
        this.listener = listener;
    }


}
