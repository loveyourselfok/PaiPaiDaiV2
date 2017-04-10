package com.boryou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PullToRefreshActivity extends Activity implements OnClickListener {
	private Button scrollBtn;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		scrollBtn = (Button) findViewById(R.id.sample_scrollview);
		scrollBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent jump = new Intent();
		switch (v.getId()) {
		case R.id.sample_scrollview:
			jump.setClass(this, PullToRefreshScrollViewActivity.class);
			break;
		default:
			break;
		}
		startActivity(jump);
	}
}