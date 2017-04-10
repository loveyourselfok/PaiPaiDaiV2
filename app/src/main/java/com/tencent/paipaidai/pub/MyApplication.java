package com.tencent.paipaidai.pub;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tools.CrashLogFilesManager;
import com.tools.MD5Util;
import com.tools.UsualTools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.watcher.RefWatcher;

public class MyApplication extends  Application{

	private static final String TAG = "TAG";
	/**
	 * 更新标志，1为汇客通正式版，2为汇客通测试版
	 */
	public static int APP_MODE = 1;
	private static Context applicationContext;
	private String token = "F80A3439DE0FD8A40ACE1A9AAF715146";// error日志token
	private MD5Util md5Util = new MD5Util();
	private ArrayList<String> fileList;
	private CrashLogFilesManager manager;

	@Override
	public void onCreate() {
		applicationContext = this;
//		KiraHttp.setCookieMode(true);
		initHX();

		setAPPMode(2);
//		MobclickAgent.enableEncrypt(true);
//		MobclickAgent.setDebugMode(true);

		// 在自己的Application中添加如下代码
//		if (LeakCanary.isInAnalyzerProcess(this)) {
//			// This process is dedicated to LeakCanary for heap analysis.
//			// You should not init your app in this process.
//			return;
//		}
//		refWatcher = LeakCanary.install(this);
		Logger
				.init("LOGUTIL")                 // default PRETTYLOGGER or use just init()
				.methodCount(3)                 // default 2
//				.hideThreadInfo()               // default shown
				.logLevel(LogLevel.FULL)        // default LogLevel.FULL
				.methodOffset(2) ;               // default 0
//				.logAdapter(new AndroidLogAdapter()); //default AndroidLogAdapter
		super.onCreate();
	}

	private void initHX() {
		int pid = android.os.Process.myPid();
		String processAppName = getAppName(pid);
		// 如果APP启用了远程的service，此application:onCreate会被调用2次
		// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
		// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process
		// name就立即返回
		if (processAppName == null
				|| !processAppName.equalsIgnoreCase(applicationContext
						.getPackageName())) {
			Log.e(TAG, "enter the service process!");
			// 则此application::onCreate 是被service 调用的，直接返回
			return;
		}
//		EMChat.getInstance().init(this);
//		EMChat.getInstance().setDebugMode(false);// 在做打包混淆时，要关闭debug模式，避免消耗不必要的资源
	}

	private String getAppName(int pID) {
		String processName = null;
		ActivityManager am = (ActivityManager) this
				.getSystemService(ACTIVITY_SERVICE);
		List l = am.getRunningAppProcesses();
		Iterator i = l.iterator();
		PackageManager pm = this.getPackageManager();
		while (i.hasNext()) {
			ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i
					.next());
			try {
				if (info.pid == pID) {
					processName = info.processName;
					return processName;
				}
			} catch (Exception e) {
				// Log.d("Process", "Error>> :"+ e.toString());
			}
		}
		return processName;
	}

	/**
	 * 设置app模式,注意修改config文件和manifest的图标和环信key,并检查版本号
	 * 
	 * @param mode
	 *            1为正式版，2为测试版
	 */
	private void setAPPMode(int mode) {
		if (mode == 1) {
			APP_MODE = 1;
			UsualTools.isShowPrintMsg = false;
		} else {
			APP_MODE = 2;
			UsualTools.isShowPrintMsg = true;
		}
	}

	public static Context getContext() {
		return applicationContext;
	}

//	// 在自己的Application中添加如下代码
//	public static RefWatcher getRefWatcher(Context context) {
//		MyApplication application = (MyApplication) context
//				.getApplicationContext();
//		return application.refWatcher;
//	}
//
////	 在自己的Application中添加如下代码
//	private RefWatcher refWatcher;
}
