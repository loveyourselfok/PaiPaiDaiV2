package com.tencent.paipaidai.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.tencent.paipaidai.R;
import com.tencent.paipaidai.dialog.LoadingDialog;
import com.tencent.paipaidai.pub.MyApplication;
import com.tools.UsualTools;
import com.tools.ViewTools;

import net.tsz.afinal.FinalBitmap;

import org.json.JSONException;
import org.json.JSONObject;


//import com.squareup.leakcanary.watcher.RefWatcher;

public abstract class MyFragment extends Fragment {
	public FinalBitmap fb;
	private InitListener listener;
	private LoadingDialog loadingDialog;
	private boolean isVisiable = true;
	private boolean isInit;
	private View view;
	public boolean isSupportReload = true;
	public Context mApplicationContext= MyApplication.getContext();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		
		fb = FinalBitmap.create(mApplicationContext);
		fb.configLoadingImage(R.drawable.loading);
		fb.configLoadfailImage(R.drawable.loading);
		onCreate();
		
		loadingDialog = new LoadingDialog(getActivity());
		loadingDialog.initProgressDialog();
	}

	public void initDialog(RelativeLayout relativeLayout) {
		loadingDialog.initMyStyleDialog(relativeLayout);
	}

	public void setDialogBelow(int id) {
		loadingDialog.setBelow(id);
	}

	@Override
	public void onResume() {
		super.onResume();
//		MobclickAgent.onPageStart("MyFragment"); // 统计页面，"MainScreen"为页面名称，可自定义
	}

	@Override
	public void onPause() {
		super.onPause();
//		MobclickAgent.onPageEnd("MyFragment");
	}

	
//	public Button setRightButton(View view,int drawId,OnClickListener onClickListener) {
//		Button button = (Button) view.findViewById(R.id.title_right_btn);
//		button.setOnClickListener(onClickListener);
//		ImageView imageView = (ImageView) view.findViewById(R.id.title_right_img);
//		Bitmap bitmap = PictureUtil.readBitMap(mApplicationContext, drawId);
//		imageView.setImageBitmap(bitmap);
//		return button;
//	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		isVisiable = isVisibleToUser;
		super.setUserVisibleHint(isVisibleToUser);
	}

	public void setDialogMessage(String message) {
		loadingDialog.setMessage(message);
	}

	public boolean isFragmentVisible() {
		return isVisiable;
	}

	public void showShortToast(String message) {
		if (isVisiable) {
			UsualTools.showShortToast(getActivity(), message);
		}
	}

	public void showDataErrorToast() {
		if (isVisiable) {
			UsualTools.showDataErrorToast(mApplicationContext);
		}
	}

	public void showNetErrorToast() {
		if (isVisiable) {
			UsualTools.showNetErrorToast(mApplicationContext);
		}
	}

	/**
	 * 展示的dialog类型
	 * 
	 * @param type
	 *            1为动画dialog，2为原生文字提示dialog
	 */
	public void showDialog(int type) {
		if (isVisiable == true && loadingDialog != null) {
			loadingDialog.show(type);
		}/*else {
			loadingDialog = new LoadingDialog(getActivity());
			loadingDialog.initProgressDialog();
			loadingDialog.show(type);
		}*/
	}

	public void dismissDialog() {
		if (loadingDialog != null) {
			loadingDialog.dismiss();
//			loadingDialog=null;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = onCreateView(inflater);
		if (view != null && isSupportReload == true) {
//			ViewTools.setButtonListener(view, R.id.converge_reload_btn,
//					new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							onReLoadClick();
//						}
//					});
		}
		return view;
	}

	@Override
	public void onStart() {
		if (getActivity() == null) {
			return;
		}
		if (listener != null && isInit == false) {
			listener.onFragmentInitSuccess();
			isInit = true;
		}
		if (startListener != null) {
			startListener.onStart();
		}
		super.onStart();
	}

	public abstract void onCreate();

	public abstract View onCreateView(LayoutInflater inflater);

	/**
	 * 重新加载数据
	 */
	public abstract void onReLoadClick();

	public void setInitCallback(InitListener listener) {
		this.listener = listener;
	}

	public interface InitListener {
		void onFragmentInitSuccess();
	}

	public interface OnStartListener {
		void onStart();
	}

	private OnStartListener startListener;
	private LoadingDialog noDataDialog;

	public void setOnStartListener(OnStartListener startListener) {
		this.startListener = startListener;
	}

	public void sPM(String msg) {
		UsualTools.showPrintMsg(msg);
	}

	public void showMsg(JSONObject object) {
		try {
			UsualTools.showShortToast(mApplicationContext, object.getString("msg"));
		} catch (JSONException e) {
			UsualTools.showDataErrorToast(mApplicationContext);
		}
	}

	public void addHeadView(ListView view) {
		View headView = LayoutInflater.from(mApplicationContext).inflate(
				R.layout.view_head, null);
		view.addHeaderView(headView);
	}

	public void showNoDataLayout() {
//		if (view != null) {
//			ViewTools.setVisible(view, R.id.converge_no_data_layout);
			showNoDataLayout(0);
//		}
	}
	/**
	 * 用type区分出具体是那种类型没有数据，从而更换不同的图片
	 * @param type
	 */
	public void showNoDataLayout(int type) {
		if (view != null) {
			ViewTools.setVisible(view, R.id.converge_no_data_layout);
		}
//		ImageView imgNoData = (ImageView) view.findViewById(R.id.img_no_data);
//		switch (type) {
//		case constants.Constants.NO_DATA_HOUSE_SOURCE_BANK_GOT_MONEY://房源银行已获得奖金
//			imgNoData.setImageResource(R.drawable.no_data_house_source_bank_got_money);
//			break;
//		case constants.Constants.NO_DATA_HOUSE_SOURCE_BANK_PREDICT_MONETY://房源银行预计奖金总额
//			imgNoData.setImageResource(R.drawable.no_data_house_souce_bank_predict_money);
//			break;
//		case constants.Constants.NO_DATA_CLIENT_SOURCE_BANK_GOT_MONEY://客源银行已获得奖金
//			imgNoData.setImageResource(R.drawable.no_data_client_source_bank_got_money);
//			break;
//		case constants.Constants.NO_DATA_CLIENT_SOURCE_BANK_PREDICT_MONETY://客源银行预计奖金总额
//			imgNoData.setImageResource(R.drawable.no_data_client_source_bank_predict_money);
//			break;
//		case constants.Constants.NO_DATA_MY_MICRO_SHOP://我的微店
//			imgNoData.setImageResource(R.drawable.no_data_my_micro_shop);
//			break;
//		case constants.Constants.NO_DATA_MY_INVITATION://我的邀请
//			imgNoData.setImageResource(R.drawable.no_data_my_invitation);
//			break;
//		case constants.Constants.NO_DATA_REPORT_CLIENT_LIST://报备客户列表
//			imgNoData.setImageResource(R.drawable.no_data_report_client_list);
//			break;
//		default:
//			imgNoData.setImageResource(R.drawable.converge_loading_nodata);
//			break;
//		}
	}

	public void hideNoDataLayout() {
		if (view != null) {
			ViewTools.setGone(view, R.id.converge_no_data_layout);
		}
	}

	/**
	 * 展示当没有内容时的动画
	 * 
	 * @param animationType
	 *            动画类型，有1,2,3种动画
	 * @param content
	 *            展示内容
	 * @param belowId
	 *            是否居于某个动画下方，-1为不传值
	 * @param index
	 *            叠放次序，-1为不传值（会在顶层），0为最底层
	 */
	/*public LoadingDialog showNoDataDialog(int animationType, String content,
			int belowId, int index) {
		if (getActivity() == null) {
			return null;
		}
		if (noDataDialog == null) {
			noDataDialog = new LoadingDialog(getActivity());
			noDataDialog.initMyStyleDialog(
					(RelativeLayout) view.findViewById(R.id.fragment_layout),
					animationType, content, index);
			if (belowId != -1) {
				noDataDialog.setBelow(belowId);
			}
			noDataDialog.show(1);
		} else {
			noDataDialog.show(1);
		}
		return noDataDialog;
	}*/

	/**
	 * 展示当没有内容时的动画
	 * 
	 * @param animationType
	 *            动画类型，有1,2,3种动画
	 * @param content
	 *            展示内容
	 * @param index
	 *            叠放次序，-1为不传值（会在顶层），0为最底层
	 */
	// public LoadingDialog showNoDataDialog(int animationType, String content,
	// int index) {
	// return showNoDataDialog(animationType, content, -1, index);
	// }

	/**
	 * 展示当没有内容时的动画
	 * 
	 * @param animationType
	 *            动画类型，有1,2,3种动画
	 * @param content
	 *            展示内容
	 * @return
	 */
	public LoadingDialog showNoDataDialog(int animationType, String content) {
//		return showNoDataDialog(animationType, content, -1, -1);
		return null;
	}

	public void dismissNoDataDialog() {
		if (noDataDialog != null) {
			noDataDialog.dismiss();
//			noDataDialog=null;
		}
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		dismissDialog();
		dismissNoDataDialog();
//		RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
//		refWatcher.watch(this);
	}

}
