package com.tools.http;

import com.loopj.android.http.RequestParams;

public class KiraParams extends RequestParams {

	@Override
	public void put(String key, String value) {
		if (value == null) {
			return;
		}
		super.put(key, value);
	}
}
