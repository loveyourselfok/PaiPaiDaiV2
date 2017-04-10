package com.tools;

import android.os.Handler;

public class TimeCounter {
	private int settingTime;// 设置时间
	private int time;// 倒计时的整个时间
	private Handler mHandler = new Handler();// 全局handler

	private Thread thread;
	private boolean isRun;

	private boolean isStop;

	private int countingTime;

	public TimeCounter(int settingTime) {
		super();
		this.settingTime = settingTime;
		time = settingTime;
	}

	public void setCountDown() {
		UsualTools.showPrintMsg("setCountDown");
		if (isRun) {
			return;
		}
		isRun = true;
		thread = new Thread(new ClassCut());
		thread.start();
	}

	public void stopCountDown() {
		UsualTools.showPrintMsg("stopCountDown");
		isStop = true;
		isRun = false;
		countingTime = 0;
		time = settingTime;// 修改倒计时剩余时间变量为设置时间
		thread = null;
	}

	public void reset() {
		UsualTools.showPrintMsg("reset");
		isRun = false;
		time = settingTime;// 修改倒计时剩余时间变量为设置时间
		countingTime = 0;
		thread = null;
		if (finishListener != null) {
			finishListener.onCountDownFinish();
		}
	}

	public void updateTime(int updateTime) {
		settingTime = updateTime;
		if (time > updateTime) {
			time = updateTime;
		}
	}

	public interface CountDownFinishListener {
		void onCountDownFinish();
	}

	private CountDownFinishListener finishListener;

	public void setCountDownFinishListener(
			CountDownFinishListener finishListener) {
		this.finishListener = finishListener;
	}

	public interface ResetListener {
		void reset();
	}

	private ResetListener rListener;

	class ClassCut implements Runnable {

		@Override
		public void run() {
			UsualTools.showPrintMsg("Start Count");
			while (time > 0 && isRun) {// 整个倒计时执行的循环

				time--;

				try {

					Thread.sleep(1000);// 线程休眠1秒钟 这个就是倒计时的间隔时间
					countingTime++;
					if (countingListener != null) {
						countingListener.onCounting(countingTime);
					}
				} catch (InterruptedException e) {

					e.printStackTrace();

				}

			}

			// 下面是倒计时结束，处理的逻辑

			if (isStop) {
				isStop = false;
				return;
			}

			mHandler.post(new Runnable() {

				@Override
				public void run() {
					reset();
				}

			});

		}

	}

	public interface OnCountingListener {
		void onCounting(int countingTime);
	}

	private OnCountingListener countingListener;

	public void setOnCountingListener(OnCountingListener countingListener) {
		this.countingListener = countingListener;
	}
}
