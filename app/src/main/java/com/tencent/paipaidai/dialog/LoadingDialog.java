package com.tencent.paipaidai.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.style.Mdialog;
import com.tencent.paipaidai.R;
import com.tencent.paipaidai.pub.GifUtil;
import com.tencent.paipaidai.pub.MyApplication;
import com.tools.PictureUtil;
import com.tools.ViewTools;


public class LoadingDialog implements OnClickListener {
	private Activity activity;

	private Context mApplicationContext= MyApplication.getContext();
	private View view;
	private Dialog progressDialog;

	private RelativeLayout loadingLayout;

	private  View progressView;

	private  Bitmap loading1;

	private  Bitmap loading2;

	private  Bitmap loading21;

	private  Bitmap loading22;

	private  Bitmap loading23;

	private  Bitmap loading24;

	private  Bitmap loading31;

	private  Bitmap loading32;

	private GifUtil util;

	private boolean isInit;//标识loadingDialog调用了GifUtil

	private RelativeLayout relativeLayout;

	private int animationType;

	private int index;

	private ImageView aniImg;

	public LoadingDialog(Activity activity) {
		super();
		this.activity = activity;
	}

	public void initProgressDialog() {
		progressView = LayoutInflater.from(mApplicationContext).inflate(
				R.layout.dialog_progress, null);
		progressDialog = Mdialog.createMyViewAndStyleDialog(activity,
				R.style.my_style_dialog, progressView);
	}
	//将带有文字的也加进来
	public void initMyStyleDialog(RelativeLayout relativeLayout,
				int animationType, String content, int index) {
			initMyStyleDialog(relativeLayout, animationType, index);
			ViewTools.setStringToTextView(view, R.id.loading_text, content);
		}
	//1.
	public void initMyStyleDialog(RelativeLayout relativeLayout) {
		initMyStyleDialog(relativeLayout, 1);
	}
	//2.
	public void initMyStyleDialog(RelativeLayout relativeLayout,
			int animationType) {
		initMyStyleDialog(relativeLayout, animationType, -1);
	}
	//3.
	public void initMyStyleDialog(RelativeLayout relativeLayout,
			int animationType, int index) {
		this.relativeLayout=relativeLayout;
		this.animationType=animationType;
		this.index=index;
		
		if (activity == null) {
			return;
		}
		if (animationType == 1) {
			view = LayoutInflater.from(/*activity*/mApplicationContext).inflate(
					R.layout.dialog_loading1, null);
		} else if (animationType == 2) {
			view = LayoutInflater.from(/*activity*/mApplicationContext).inflate(
					R.layout.dialog_loading2, null);
		} else if (animationType == 3) {
			view = LayoutInflater.from(/*activity*/mApplicationContext).inflate(
					R.layout.dialog_loading3, null);
		}
		loadingLayout = ViewTools.setRelativeLayoutListener(view,R.id.loading_layout, this);
		aniImg = (ImageView) view.findViewById(R.id.gif_view);
		util = new GifUtil(aniImg);
		isInit = true;
		if (animationType == 1) {
			if (loading1==null) {
				loading1 = getBitmap(loading1, R.drawable.loading1);
				loading2 = getBitmap(loading2, R.drawable.loading2);
			}
			util.setBitmaps(loading1, loading2);
		} else if (animationType == 2) {
			if (loading21==null) {
				loading21 = getBitmap(loading21, R.drawable.loading21);
				loading22 = getBitmap(loading22, R.drawable.loading22);
				loading23 = getBitmap(loading23, R.drawable.loading23);
				loading24 = getBitmap(loading24, R.drawable.loading24);
			}
			util.setBitmaps(loading21, loading22, loading23, loading24);
		} else if (animationType == 3) {
			if (loading31==null) {
				loading31 = getBitmap(loading31, R.drawable.loading31);
				loading32 = getBitmap(loading32, R.drawable.loading32);
			}
			util.setBitmaps(loading31, loading32);
		}
		util.startAnimation(300);
		if (index == -1) {
			relativeLayout.addView(view);
		} else {
			relativeLayout.addView(view, index);
		}
	}

	private Bitmap getBitmap(Bitmap bitmap, int resId) {
		if (bitmap == null) {
			bitmap = PictureUtil.readBitMap(mApplicationContext, resId);
		}
		return bitmap;
	}

	public void setBelow(int id) {
		LayoutParams params = (LayoutParams) view
				.getLayoutParams();
		params.addRule(RelativeLayout.BELOW, id);
		view.setLayoutParams(params);
	}

	public void setMessage(String message) {
		if (progressDialog != null) {
			ViewTools.setStringToTextView(progressView, R.id.progress_text,
					message);
		}/*else {
			progressDialog.show();
		}*/
	}

	/**
	 * 展示的dialog类型
	 * 
	 * @param type
	 *            1为动画dialog，2为原生文字提示dialog
	 */
	public void show(int type) {
		if (type == 1) {
			if (loadingLayout != null) {
				loadingLayout.setVisibility(View.VISIBLE);
			} else {
				if (progressDialog != null) {
					if (util!=null&&util.imageView==null) {
						util.imageView=(ImageView) view.findViewById(R.id.gif_view);
					}
					progressDialog.show();
				}
			}
		} else {
			if (progressDialog != null) {
				progressDialog.show();
			}
		}

	}
	/*public void show(int type) {
		if (type == 1) {
			if (loadingLayout != null) {
				loadingLayout.setVisibility(View.VISIBLE);
			} else {
				if (isInit&&progressDialog==null) {
					progressDialog = Mdialog.createMyViewAndStyleDialog(activity,
							R.style.my_style_dialog, progressView);
				}
				if (progressDialog != null) {
					progressDialog.show();
				}
			}
		} else {
			if (isInit&&progressDialog==null) {
				progressDialog = Mdialog.createMyViewAndStyleDialog(activity,
						R.style.my_style_dialog, progressView);
			}
			if (progressDialog != null) {
				progressDialog.show();
			}else {
				initProgressDialog();
				progressDialog.show();
			}
		}

	}*/

	/*public void dismiss() {
		if (loadingLayout != null) {
			loadingLayout.setVisibility(View.GONE);
		}
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog=null;
		}
//		util.setImageView2Empty();
	}*/
	public void dismiss() {
		if (loadingLayout != null) {
			loadingLayout.setVisibility(View.GONE);
		}
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
		if (isInit) {
			util.setImageView2Empty();//取消任务
			util.imageView=null;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loading_layout:

			break;

		default:
			break;
		}

	}

}
