package com.hollysmart.platformsdk.style;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


/**
 * @author cai
 * @author 2014.08.12
 */

public abstract class CaiActivity extends AppCompatActivity implements View.OnClickListener {

	public Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(layoutResID());
		findView();
		init();
	}

	/**
	 * layout绑定
	 */
	public abstract int layoutResID();

	public abstract void findView();

	/**
	 * 逻辑操作
	 */
	public abstract void init();

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			finish();
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.hollysmart.ExitApp");
		this.registerReceiver(broadcastReceiver, filter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			this.unregisterReceiver(broadcastReceiver);
		}catch (Exception e){
		}
	}

	public void closeOther() {
		Intent intent = new Intent();
		intent.setAction("com.hollysmart.ExitApp");
		this.sendBroadcast(intent);
	}



}
