package com.hollysmart.platformsdk.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.hollysmart.platformsdk.R;
import com.hollysmart.platformsdk.interfaces.JsxInterface;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private Context mContext;
    private List<SystemContacts> myContactsList;
    private JsxInterface.RVOnItemClickListener onItemClickListener;
    private String keyword;

    public ContactsAdapter(Context mContext, List<SystemContacts> myContactsList) {
        this.mContext = mContext;
        this.myContactsList = myContactsList;
    }

    public void setOnItemClickListener(JsxInterface.RVOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<SystemContacts> getMyContactsList() {
        return myContactsList;
    }

    public void setMyContactsList(List<SystemContacts> myContactsList, String keyword) {
        this.myContactsList = myContactsList;
        this.keyword = keyword;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_contacts, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        SystemContacts myContacts = myContactsList.get(position);
        holder.tv_name.setText(myContacts.name == null ? "": myContacts.name);
        holder.tv_phone.setText(myContacts.phone == null ? "": myContacts.phone);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(v, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myContactsList.size();
    }

    public static class ContactsViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name;
        private TextView tv_phone;
        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_phone = itemView.findViewById(R.id.tv_phone);
        }
    }
}
