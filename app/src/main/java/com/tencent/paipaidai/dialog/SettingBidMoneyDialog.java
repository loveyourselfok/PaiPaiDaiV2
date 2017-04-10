package com.tencent.paipaidai.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.tencent.paipaidai.R;
import com.tools.UsualTools;

import java.nio.ByteBuffer;

public class SettingBidMoneyDialog extends Dialog implements OnClickListener {
	private final Activity activity;
	private Dialog dialog;
	private EditText etBidMoney;
	//	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setting_bid_money);
//		view = LayoutInflater.from(activity).inflate(R.layout.layout_setting_bid_money,null);
		initModule();
	}

	public SettingBidMoneyDialog(Activity activity) {
		super(activity,R.style.OperateTipStyleTheme);
		this.activity =activity;
	}

	private void initModule() {
		TextView tvTitle= (TextView) findViewById(R.id.title);
		tvTitle.setText("设置金额");
		findViewById(R.id.post_btn).setOnClickListener(this);
		etBidMoney = (EditText) findViewById(R.id.et_bid_money);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.post_btn:
			String money = etBidMoney.getText().toString().trim();
			if ("".equals(money)||money==null){
				UsualTools.showShortToast(activity,"请输入投标金额！");
				return;
			}
			if (listener!=null){
				listener.sendBidMoney(money);
			}
			break;
		default:
			break;
		}
	}

	public interface OnBidMoneySureListener{
		void sendBidMoney(String money);
	}
	private OnBidMoneySureListener listener;

	public void setListener(OnBidMoneySureListener listener) {
		this.listener = listener;
	}

	public static byte[] toByteArray(double val) {
		byte[] bytes = new byte[8];
		ByteBuffer.wrap(bytes).putDouble(val);
		return bytes;
	}
}
