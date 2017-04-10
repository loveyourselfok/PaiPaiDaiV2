package com.tencent.paipaidai.pub;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GifUtil {
	public  ImageView imageView;
	private TimeTask task;
	private Timer timer;
	private  TimeHandler handler;
	private  ArrayList<Bitmap> list = new ArrayList<Bitmap>();
	private  int position;
	private  int size;

	public GifUtil(ImageView imageView) {
		super();
		this.imageView = imageView;
	}

	/**
	 * 设置多张bitmap图
	 * 
	 * @param bitmaps
	 */
	public void setBitmaps(Bitmap... bitmaps) {
		for (Bitmap bitmap : bitmaps) {
			list.add(bitmap);
		}
		size = list.size();
	}

	/**
	 * 开启动画
	 * 
	 * @param period
	 *            周期
	 */
	public void startAnimation(long period) {
		task = new TimeTask();
		timer = new Timer();
		handler = new TimeHandler();
		timer.schedule(task, 0, period);
	}

	private  class TimeTask extends TimerTask {
		@Override
		public void run() {
			handler.sendEmptyMessage(0);
		}
	}

	private  class TimeHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			Bitmap bitmap = list.get(position);
			try {
				imageView.setImageBitmap(bitmap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (position < size - 1) {
				position++;
			} else {
				position = 0;
			}
			super.handleMessage(msg);
		}
	}
	/**
	 * 当loadingDialog消失的时候就停止任务，并将imageView置空
	 */
	public void setImageView2Empty(){
		timer.cancel();
//		this.imageView=null;
	}
}
