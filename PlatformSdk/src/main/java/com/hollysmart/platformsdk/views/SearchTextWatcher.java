package com.hollysmart.platformsdk.views;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;


/**
 * Cai 2020-10-21 监听Edittext，防止快速输入的情况下重复调用，加一个延时
 */
public class SearchTextWatcher implements TextWatcher {

    private String keyword;
    private final int MSG_SEARCH = 10;
    private final long DELAY_AUTO_COMPLETE = 500;

    private SearchIF searchIF;
    public SearchTextWatcher(SearchIF searchIF) {
        this.searchIF = searchIF;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            searchIF.searchTextWatcher(keyword);
        }
    };

    public interface SearchIF{
        void searchTextWatcher(String keyword);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        keyword = s.toString();
        //为了防止快速输入的情况下重复调用，加一个延时
        mHandler.removeMessages(MSG_SEARCH);
        mHandler.sendEmptyMessageDelayed(MSG_SEARCH, DELAY_AUTO_COMPLETE);
    }

}
