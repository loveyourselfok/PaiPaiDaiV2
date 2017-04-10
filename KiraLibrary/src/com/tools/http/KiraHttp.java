package com.tools.http;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.tools.UsualTools;

public class KiraHttp {
	private static boolean isSetCookie = false;

	/**
	 * 设置是否开启cookie模式
	 * 
	 * @param isUse
	 *            true为开启，false为关闭
	 */
	public static void setCookieMode(boolean isUse) {
		isSetCookie = isUse;
	}

	public static void connect(final Context context, String url,
			KiraParams params, final int requestCode,
			final HttpCallback callback) {
		AsyncHttpClient client = new AsyncHttpClient();
		if (params == null) {
			params = new KiraParams();
		}
		if (isSetCookie) {
			setHeader(context, client);
		}
		client.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] response) {
				String result = new String(response);
				callback.success(requestCode, headers, result);
				// clearCookie(context);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] response, Throwable e) {
				UsualTools.showPrintMsg("statusCode", statusCode + "");
				String result = "";
				if (response != null) {
					result = new String(response);
				}
				callback.fail(requestCode, statusCode, e, result);
			}
		});

	}

	public interface HttpCallback {
		void success(int requestCode, Header[] headers, String result);

		void fail(int requestCode, int statusCode, Throwable throwable,
				String errorResponse);
	}

	public static void setHeader(Context context, AsyncHttpClient client) {
		if (context == null) {
			return;
		}
		PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
		client.setCookieStore(myCookieStore);
		List<Cookie> cookies = myCookieStore.getCookies();
		for (int i = 0; i < cookies.size(); i++) {
			Cookie cookie = cookies.get(i);
			client.addHeader(cookie.getName(), cookie.getValue());
			// UsualTools.showPrintMsg("cookie.getName()=" + cookie.getName());
			// Utils.showPrintMsg("cookie.getValue()", cookie.getValue());
		}
		client.addHeader("android_user_ver", UsualTools.getAppVerson(context));
	}

	public static void clearCookie(Context context) {
		PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
		myCookieStore.clear();
	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 *            域名
	 * @param handler
	 *            处理
	 */
	public static void downLoadFile(String url, AsyncHttpResponseHandler handler) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, handler);
	}
}
