package com.hollysmart.platformsdk.contacts;

import android.Manifest;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.PermissionUtils;
import com.hollysmart.platformsdk.R;
import com.hollysmart.platformsdk.interfaces.JsxInterface;
import com.hollysmart.platformsdk.style.CaiActivity;
import com.hollysmart.platformsdk.utils.Mlog;
import com.hollysmart.platformsdk.views.MyItemDecoration;
import com.hollysmart.platformsdk.views.SearchTextWatcher;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;

import static com.hollysmart.platformsdk.views.MyItemDecoration.VERTICAL_LIST;

public class SystemContactsActivity extends CaiActivity {

    private EditText ed_search;
    private RecyclerView rv_my_contacts;

    private ContactsAdapter adapter;
    @Override
    public int layoutResID() {
        return R.layout.activity_my_contacts;
    }


    @Override
    public void findView() {
        ed_search = findViewById(R.id.ed_search);
        rv_my_contacts = findViewById(R.id.rv_my_contacts);
    }

    private List<SystemContacts> myContactsList;
    private List<SystemContacts> searchContactsList;
    @Override
    public void init() {
        rv_my_contacts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_my_contacts.addItemDecoration(new MyItemDecoration(this, VERTICAL_LIST));

        myContactsList = new ArrayList<>();
        searchContactsList = new ArrayList<>();
        adapter = new ContactsAdapter(this, myContactsList);
        rv_my_contacts.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);

        initSearchView();

        PermissionUtils.permission(Manifest.permission.READ_CONTACTS)
                .rationale((activity, shouldRequest) -> {
                    Mlog.d("rationale");
                }).callback(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {
                myContactsList.addAll(ContactUtils.getAllContacts(SystemContactsActivity.this));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onDenied() {

            }
        }).request();
    }

    JsxInterface.RVOnItemClickListener onItemClickListener = (v, position) -> {
        EventBus.getDefault().post(adapter.getMyContactsList().get(position));
        finish();
    };

    private void initSearchView() {
        ed_search.setFocusable(true);
        ed_search.setFocusableInTouchMode(true);
        ed_search.requestFocus();
        ed_search.addTextChangedListener(new SearchTextWatcher(new SearchTextWatcher.SearchIF() {
            @Override
            public void searchTextWatcher(String keyword) {
                search(keyword);
            }
        }));
    }

    private void search(String keyword) {
        searchContactsList.clear();
        if (TextUtils.isEmpty(keyword)) {
            adapter.setMyContactsList(myContactsList, "");
        } else {
            for (SystemContacts item : myContactsList) {
                if (item.name != null && item.name.contains(keyword)) {
                    searchContactsList.add(item);
                } else {
                    if (item.phone != null && item.phone.contains(keyword)) {
                        searchContactsList.add(item);
                    }
                }
            }
            adapter.setMyContactsList(searchContactsList, keyword);
        }
    }

    @Override
    public void onClick(View view) {

    }
}
