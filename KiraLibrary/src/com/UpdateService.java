package com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.tools.MResource;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.RemoteViews;

public class UpdateService extends Service {
	// 标题
	private String title;

	private File updateFile = null;
	// 下载状态
	private final static int DOWNLOAD_COMPLETE = 0;
	private final static int DOWNLOAD_FAIL = 1;
	// 通知栏
	private NotificationManager updateNotificationManager = null;
	private Notification updateNotification = null;
	// 通知栏跳转Intent
	// private Intent updateIntent = null;
	private PendingIntent updatePendingIntent = null;
	int downloadCount = 0;
	int currentSize = 0;
	long totalSize = 0;
	int updateTotalSize = 0;
	private int iconId;
	private String updateUrl;
	private String serviceName;
	private String packageName;
	private updateRunnable uRunnable;

	// 在onStartCommand()方法中准备相关的下载工作：
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		// 获取传值
		title = intent.getStringExtra("title");
		updateUrl = intent.getStringExtra("updateUrl");
		serviceName = intent.getStringExtra("serviceName");
		packageName = intent.getStringExtra("packageName");
		if (title == null) {
			title = "app更新";
		}
		iconId = intent.getIntExtra("iconId",
				MResource.getIdByName(this, "drawable", "ic_launcher"));

		updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		updateNotification = new Notification();

		createDirAndFile();
		startDownLoad();
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 创建文件夹和文件
	 */
	private void createDirAndFile() {
		// 创建文件
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
				.getExternalStorageState())) {
			File updateDir = new File(
					Environment.getExternalStorageDirectory(), "/apkDownload/");
			updateFile = new File(updateDir.getPath(), title + ".apk");
			if (!updateDir.exists()) {
				updateDir.mkdirs();
			}
			if (!updateFile.exists()) {
				try {
					updateFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 开启线程，下载
	 */
	private void startDownLoad() {
		uRunnable = new updateRunnable();
		updatePendingIntent = PendingIntent.getActivity(this, 0, new Intent(),
				0);
		// 设置通知栏显示内容
		updateNotification.icon = iconId;
		updateNotification.tickerText = "开始下载";
		updateNotification.setLatestEventInfo(this, title, "0%",
				updatePendingIntent);
		// 发出通知
		updateNotificationManager.notify(0, updateNotification);
		// 开启一个新的线程下载，如果使用Service同步下载，会导致ANR问题，Service本身也会阻塞
		new Thread(uRunnable).start();// 这个是下载的重点，是下载的过程
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 直接跳转到安装界面
	 * 
	 * @param appFile
	 */
	private void installApp(File appFile) {
		// 创建URI
		Uri uri = Uri.fromFile(appFile);
		// 创建Intent意图
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// 设置Uri和类型
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		// 执行意图进行安装
		startActivity(intent);
	}

	private Handler updateHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case DOWNLOAD_COMPLETE:
				// 点击安装PendingIntent
				Uri uri = Uri.fromFile(updateFile);
				Intent installIntent = new Intent(Intent.ACTION_VIEW);
				installIntent.setDataAndType(uri,
						"application/vnd.android.package-archive");

				updatePendingIntent = PendingIntent.getActivity(
						UpdateService.this, 0, installIntent, 0);

				updateNotification.defaults = Notification.DEFAULT_SOUND;//
				// 铃声提醒
				updateNotification.setLatestEventInfo(UpdateService.this,
						title, "下载完成,点击安装。", updatePendingIntent);
				updateNotificationManager.notify(0, updateNotification);

				// 直接到安装界面
				installApp(updateFile);
				// 停止服务
				stopService();
				break;
			case DOWNLOAD_FAIL:
				// 下载失败
				updateNotification.setLatestEventInfo(UpdateService.this,
						title, "下载失败，请重新下载", updatePendingIntent);
				// setButtonBroadCast();
				updateNotificationManager.notify(0, updateNotification);

				break;
			default:
				stopService();
			}
		}
	};

	// 注册按钮广播
	private void setButtonBroadCast() {
		final String STATUS_BAR_COVER_CLICK_ACTION = "download";
		updateNotification.contentView.setViewVisibility(
				MResource.getIdByName(this, "id", "notifi_layout"),
				View.VISIBLE);
		BroadcastReceiver onClickReceiver = new BroadcastReceiver() {
			private boolean flag = false;

			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(STATUS_BAR_COVER_CLICK_ACTION)) {
					// 在这里处理点击事件
					// Utils.showPrintMsg("响应点击", "响应点击");
					// 取消通知栏
				}
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction(STATUS_BAR_COVER_CLICK_ACTION);
		registerReceiver(onClickReceiver, filter);
		Intent buttonIntent = new Intent(STATUS_BAR_COVER_CLICK_ACTION);
		PendingIntent pendButtonIntent = PendingIntent.getBroadcast(this, 0,
				buttonIntent, 0);
		updateNotification.contentView.setOnClickPendingIntent(
				MResource.getIdByName(this, "id", "notifi_layout"),
				pendButtonIntent);
		// R.id.trackname为你要监听按钮的id
		// mRemoteViews.setOnClickPendingIntent(R.id.trackname,
		// pendButtonIntent);

	}

	private void stopService() {
		Intent intent = new Intent(serviceName);
		intent.setPackage(packageName);// 这里你需要设置你应用的包名
		stopService(intent);
	}

	public long downloadUpdateFile(String downloadUrl, File saveFile)
			throws Exception {

		HttpURLConnection httpConnection = null;
		InputStream is = null;
		FileOutputStream fos = null;

		try {
			URL url = new URL(downloadUrl);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection
					.setRequestProperty("User-Agent", "PacificHttpClient");
			if (currentSize > 0) {
				httpConnection.setRequestProperty("RANGE", "bytes="
						+ currentSize + "-");
			}
			httpConnection.setConnectTimeout(10000);
			httpConnection.setReadTimeout(20000);
			updateTotalSize = httpConnection.getContentLength();
			if (httpConnection.getResponseCode() == 404) {
				throw new Exception("fail!");
			}
			is = httpConnection.getInputStream();
			fos = new FileOutputStream(saveFile, false);
			byte buffer[] = new byte[4096];
			int readsize = 0;
			while ((readsize = is.read(buffer)) > 0) {
				fos.write(buffer, 0, readsize);
				totalSize += readsize;
				// 为了防止频繁的通知导致应用吃紧，百分比增加10才通知一次
				if ((downloadCount == 0)
						|| (int) (totalSize * 100 / updateTotalSize) - 10 > downloadCount) {
					downloadCount += 10;

					updateNotification.setLatestEventInfo(UpdateService.this,
							"正在下载", (int) totalSize * 100 / updateTotalSize
									+ "%", updatePendingIntent);

					/***
					 * 在这里我们用自定的view来显示Notification
					 */
					updateNotification.contentView = new RemoteViews(
							getPackageName(), MResource.getIdByName(this,
									"layout", "notification_item"));
					updateNotification.contentView.setTextViewText(MResource
							.getIdByName(this, "id", "notificationTitle"),
							"正在下载");
					updateNotification.contentView.setProgressBar(MResource
							.getIdByName(this, "id", "notificationProgress"),
							100, downloadCount, false);

					updateNotificationManager.notify(0, updateNotification);
				}
			}
		} finally {
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
			if (is != null) {
				is.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
		return totalSize;
	}

	private class updateRunnable implements Runnable {
		Message message = updateHandler.obtainMessage();

		public void run() {
			message.what = DOWNLOAD_COMPLETE;

			try {
				// 增加权限<USES-PERMISSION
				// android:name="android.permission.WRITE_EXTERNAL_STORAGE">;

				// 下载函数，以QQ为例子
				// 增加权限<USES-PERMISSION
				// android:name="android.permission.INTERNET">;
				long downloadSize = downloadUpdateFile(updateUrl, updateFile);
				if (downloadSize > 0) {
					// 下载成功
					updateHandler.sendMessage(message);
				}
				// updateHandler.sendMessage(message);
			} catch (Exception ex) {
				ex.printStackTrace();
				message.what = DOWNLOAD_FAIL;
				// 下载失败
				updateHandler.sendMessage(message);
			}
		}
	}
}