package com.tools;

import java.io.File;
import java.io.FileInputStream;

import com.UpdateService;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Window;
import android.view.WindowManager;

public class SystemTools {
	public static final int TAKE_PICTURE_CODE = 1001;
	public static final int FROM_PHOTO_CODE = 1002;
	public static final int CROP_IMAGE_CODE = 1003;
	public static final int NOTIFICATION_CODE = 1004;
	public static String PICTURE_PATH;
	public static String HEAD_PATH;
	public static boolean TAKE_PICTURE = false;
	public static Uri imageUri;

	/**
	 * 拍照
	 * 
	 * @param activity
	 * @param picName
	 *            图片名字
	 */
	public static void takePicture(Activity activity, String picName) {
		File DatalDir = Environment.getExternalStorageDirectory();
		File myDir = new File(DatalDir + "/DCIM/Camera");
		if (!myDir.exists()) {
			myDir.mkdirs();
		}
		String SDPATH = DatalDir.toString() + "/DCIM/Camera";
		File f = new File(SDPATH, picName + ".jpeg");
		if (f.exists()) {
			f.delete();
		}
		PICTURE_PATH = SDPATH + "/" + picName + ".jpeg";
		imageUri = Uri.fromFile(f);
		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		cameraIntent.putExtra(picName, f);
		activity.startActivityForResult(cameraIntent, TAKE_PICTURE_CODE);

	}

	/**
	 * 从手机获取图片的url
	 * 
	 * @param activity
	 */
	public static void getImageUrlFromPhone(Activity activity) {
		Intent picture = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		activity.startActivityForResult(picture, FROM_PHOTO_CODE);
	}

	/**
	 * 裁切图片
	 * 
	 * @param activity
	 * @param uri
	 *            手机的uri
	 * @param outputX
	 *            裁切的初始宽度
	 * @param outputY
	 *            裁切的初始高度
	 */
	public static void cropImageUri(Activity activity, Uri uri, int cropX,
			int cropY, String picName) {
		File DatalDir = Environment.getExternalStorageDirectory();
		File myDir = new File(DatalDir + "/DCIM/Camera");
		if (!myDir.exists()) {
			myDir.mkdirs();
		}
		String SDPATH = DatalDir.toString() + "/DCIM/Camera";
		File f = new File(SDPATH, picName);
		if (f.exists()) {
			f.delete();
		}
		HEAD_PATH = SDPATH + "/" + picName;
		imageUri = Uri.fromFile(f);
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", cropX);
		intent.putExtra("aspectY", cropY);
		intent.putExtra("outputX", cropX);
		intent.putExtra("outputY", cropY);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		activity.startActivityForResult(intent, CROP_IMAGE_CODE);
	}

	/**
	 * 发送通知到标题栏
	 * 
	 * @param context
	 * @param intent
	 * @param icon
	 *            图标rid
	 * @param ticket
	 *            提示
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @param isSound
	 *            是否发出声音
	 * @param isVibrate
	 *            是否震动
	 */
	public static void sendNotification(Context context, Intent intent,
			int icon, String ticket, String title, String content,
			boolean isSound, boolean isVibrate) {
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification n = new Notification();
		n.icon = icon;
		n.tickerText = ticket;
		n.when = System.currentTimeMillis();
		if (isSound) {
			n.defaults |= Notification.DEFAULT_SOUND;
		}
		if (isVibrate) {
			n.defaults |= Notification.DEFAULT_VIBRATE;
		}
		// n.flags=Notification.FLAG_ONGOING_EVENT;
		PendingIntent pi = PendingIntent.getActivity(context,
				(int) (Math.random() * 1000) + 1, intent,
				Intent.FLAG_ACTIVITY_CLEAR_TOP);
		n.setLatestEventInfo(context, title, content, pi);
		clearNotification(context);
		nm.notify(NOTIFICATION_CODE, n);
	}

	/**
	 * 发送通知到标题栏
	 * 
	 * @param context
	 * @param intent
	 * @param icon
	 *            图标rid
	 * @param ticket
	 *            提示
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @param isSound
	 *            是否发出声音
	 * @param isVibrate
	 *            是否震动
	 * @param notiCode
	 *            通知code
	 */
	public static void sendNotification(Context context, Intent intent,
			int icon, String ticket, String title, String content,
			boolean isSound, boolean isVibrate, int notiCode) {
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification n = new Notification();
		n.icon = icon;
		n.tickerText = ticket;
		n.when = System.currentTimeMillis();
		if (isSound) {
			n.defaults |= Notification.DEFAULT_SOUND;
		}
		if (isVibrate) {
			n.defaults |= Notification.DEFAULT_VIBRATE;
		}
		// n.flags=Notification.FLAG_ONGOING_EVENT;
		PendingIntent pi = PendingIntent.getActivity(context,
				(int) (Math.random() * 1000) + 1, intent,
				Intent.FLAG_ACTIVITY_CLEAR_TOP);
		n.setLatestEventInfo(context, title, content, pi);
		clearNotification(context, notiCode);
		nm.notify(notiCode, n);
	}

	/**
	 * 删除通知
	 * 
	 * @param context
	 */
	public static void clearNotification(Context context) {
		// 启动后删除之前我们定义的通知
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(NOTIFICATION_CODE);
	}

	/**
	 * 删除指定通知
	 * 
	 * @param context
	 * @param notiCode
	 *            通知code
	 */
	public static void clearNotification(Context context, int notiCode) {
		// 启动后删除之前我们定义的通知
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(notiCode);
	}

	/**
	 * 删除所有通知
	 * 
	 * @param context
	 */
	public static void clearAllNotification(Context context) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancelAll();
	}

	/**
	 * 更新app
	 * 
	 * @param url
	 *            网址
	 */
	public static void updateApp(Context context, String url) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse(url);
		intent.setData(content_url);
		context.startActivity(intent);// 打开浏览器更新
	}

	/**
	 * 后台更新
	 * 
	 * @param context
	 * @param title
	 *            标题
	 * @param updateUrl
	 *            域名
	 * @param iconId
	 *            图片id
	 * @param packageName
	 *            包名
	 */
	public static void updateInBackground(Context context, String title,
			String updateUrl, int iconId, String packageName) {
		Intent updateIntent = new Intent(context, UpdateService.class);
		updateIntent.putExtra("title", title);
		updateIntent.putExtra("updateUrl", updateUrl);
		updateIntent.putExtra("iconId", iconId);
		updateIntent.putExtra("serviceName", "com.UpdateService");
		updateIntent.putExtra("packageName", packageName);
		context.startService(updateIntent);
	}

	/**
	 * 设置顶部栏的颜色
	 * 
	 * @param activity
	 */
	public static void initSystemBar(Activity activity, int color,
			int headViewId) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(activity, true);
			ViewTools.setVisible(activity, headViewId);
		}

		SystemBarTintManager tintManager = new SystemBarTintManager(activity);
		tintManager.setStatusBarTintEnabled(true);
		// 使用颜色资源
		tintManager.setStatusBarTintResource(color);
	}

	private static void setTranslucentStatus(Activity activity, boolean on) {
		Window win = activity.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	/**
	 * 获取手机序列号
	 * 
	 * @param context
	 * @return
	 */
	public static String getPhoneId() {
		String id = android.os.Build.SERIAL;
		return id;
	}

	/**
	 * 把文件转为64位字符串
	 * 
	 * @param path
	 *            文件路径
	 * @return 64位字符串
	 */
	public static String encodeBase64File(String path) {
		try {
			File file = new File(path);
			UsualTools.showPrintMsg("file=" + file.length());
			FileInputStream inputFile = new FileInputStream(file);
			byte[] buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			inputFile.close();
			return android.util.Base64.encodeToString(buffer, Base64.DEFAULT);
		} catch (Exception e) {
			UsualTools.showPrintMsg(e.toString());
			return "";
		}
	}
}