package com.boryou.pubClass;

import com.boryou.pubClass.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;

public class PullToRefreshScrollViewActivity extends Activity {
	PullToRefreshScrollView mPullRefreshScrollView;
	ScrollView mScrollView;
	LayoutInflater inflater;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pull_scrollview);
		inflater = LayoutInflater.from(this);
		mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
		mScrollView = mPullRefreshScrollView.getRefreshableView();
		//获取显示的内容
		View view = inflater.inflate(R.layout.text, null);
		mScrollView.addView(view);
		//添加点击事件
		mScrollView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
		
	}
	

}
