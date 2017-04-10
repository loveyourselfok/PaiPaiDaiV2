package com.util;

import com.kira.specialviewlibrary.R;
import com.util.AudioManager.AudioStageListener;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class AudioRecordButtonUtil implements AudioStageListener,
		OnTouchListener {

	private static final int STATE_NORMAL = 1;
	private static final int STATE_RECORDING = 2;
	private static final int STATE_WANT_TO_CANCEL = 3;

	private static final int DISTANCE_Y_CANCEL = 50;

	private int mCurrentState = STATE_NORMAL;
	// 已经开始录音
	private boolean isRecording = false;

	private DialogManager mDialogManager;

	private AudioManager mAudioManager;

	private float mTime = 0;
	// 是否触发了onlongclick，准备好了
	private boolean mReady;

	private Button button;

	/**
	 * 先实现两个参数的构造方法，布局会默认引用这个构造方法， 用一个 构造参数的构造方法来引用这个方法 * @param context
	 */

	public AudioRecordButtonUtil(Context context, Button button) {
		this.button = button;
		mDialogManager = new DialogManager(context);
		// 这里没有判断储存卡是否存在，有空要判断
		String dir = Environment.getExternalStorageDirectory()
				+ "/nickming_recorder_audios";
		mAudioManager = AudioManager.getInstance(dir);
		mAudioManager.setOnAudioStageListener(this);
		button.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				mReady = true;
				mAudioManager.prepareAudio();
				return false;
			}
		});
		button.setOnTouchListener(this);
	}

	public void setAudioFinishRecorderListener(
			AudioFinishRecorderListener listener) {
		mListener = listener;
	}

	/**
	 * 录音完成后的回调，回调给activiy，可以获得mtime和文件的路径
	 * 
	 * @author nickming
	 * 
	 */
	public interface AudioFinishRecorderListener {
		void onFinished(float seconds, String filePath);
	}

	private AudioFinishRecorderListener mListener;

	// 获取音量大小的runnable
	private Runnable mGetVoiceLevelRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (isRecording) {
				try {
					Thread.sleep(100);
					mTime += 0.1f;
					mhandler.sendEmptyMessage(MSG_VOICE_CHANGE);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};

	// 准备三个常量
	private static final int MSG_AUDIO_PREPARED = 0X110;
	private static final int MSG_VOICE_CHANGE = 0X111;
	private static final int MSG_DIALOG_DIMISS = 0X112;

	private Handler mhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_AUDIO_PREPARED:
				// 显示应该是在audio end prepare之后回调
				mDialogManager.showRecordingDialog();
				isRecording = true;
				new Thread(mGetVoiceLevelRunnable).start();

				// 需要开启一个线程来变换音量
				break;
			case MSG_VOICE_CHANGE:
				mDialogManager.updateVoiceLevel(mAudioManager.getVoiceLevel(7));
				break;
			case MSG_DIALOG_DIMISS:

				break;

			}
		};
	};

	// 在这里面发送一个handler的消息
	@Override
	public void wellPrepared() {
		// TODO Auto-generated method stub
		mhandler.sendEmptyMessage(MSG_AUDIO_PREPARED);
	}

	/**
	 * 回复标志位以及状态
	 */
	private void reset() {
		// TODO Auto-generated method stub
		isRecording = false;
		changeState(STATE_NORMAL);
		mReady = false;
		mTime = 0;
	}

	private boolean wantToCancel(int x, int y) {
		// TODO Auto-generated method stub

		if (x < 0 || x > button.getWidth()) {// 判断是否在左边，右边，上边，下边
			return true;
		}
		if (y < -DISTANCE_Y_CANCEL
				|| y > button.getHeight() + DISTANCE_Y_CANCEL) {
			return true;
		}

		return false;
	}

	private void changeState(int state) {
		if (mCurrentState != state) {
			mCurrentState = state;
			switch (mCurrentState) {
			case STATE_NORMAL:
				button.setBackgroundResource(R.drawable.button_recordnormal);
				button.setText("按住 说话");
				break;
			case STATE_RECORDING:
				button.setBackgroundResource(R.drawable.button_recording);
				button.setText("松开 结束");
				if (isRecording) {
					mDialogManager.recording();
					// 复写dialog.recording();
				}
				break;
			case STATE_WANT_TO_CANCEL:
				button.setBackgroundResource(R.drawable.button_recording);
				button.setText("松开手指，取消发送");
				// dialog want to cancel
				mDialogManager.wantToCancel();
				break;

			}
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			changeState(STATE_RECORDING);
			break;
		case MotionEvent.ACTION_MOVE:

			if (isRecording) {

				// 根据x，y来判断用户是否想要取消
				if (wantToCancel(x, y)) {
					changeState(STATE_WANT_TO_CANCEL);
				} else {
					changeState(STATE_RECORDING);
				}

			}

			break;
		case MotionEvent.ACTION_UP:
			// 首先判断是否有触发onlongclick事件，没有的话直接返回reset
			if (!mReady) {
				reset();
				return false;
			}
			// 如果按的时间太短，还没准备好或者时间录制太短，就离开了，则显示这个dialog
			if (!isRecording || mTime < 0.6f) {
				mDialogManager.tooShort();
				mAudioManager.cancel();
				mhandler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS, 1300);// 持续1.3s
			} else if (mCurrentState == STATE_RECORDING) {// 正常录制结束

				mDialogManager.dimissDialog();

				mAudioManager.release();// release释放一个mediarecorder

				if (mListener != null) {// 并且callbackActivity，保存录音
					mListener.onFinished(mTime,
							mAudioManager.getCurrentFilePath());
				}
			} else if (mCurrentState == STATE_WANT_TO_CANCEL) {
				// cancel
				mAudioManager.cancel();
				mDialogManager.dimissDialog();
			}
			reset();// 恢复标志位
			break;
		}
		return false;
	}

}
