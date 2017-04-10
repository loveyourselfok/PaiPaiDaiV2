package com.tools;

import android.util.Log;

public class Log_Util {
	private static boolean LOG_CONTROL = true;
	private static int defaultShowCount = 4000;

	/**
	 * 分段打印出较长log文本,并可输入每条日志的打印长度
	 * 
	 * @param log
	 *            原log文本
	 * @param showCount
	 *            规定每段显示的长度（最好不要超过eclipse限制长度）
	 */
	public static void showLogCompletion(String log, int showCount) {
		if (LOG_CONTROL) {
			if (log.length() > showCount) {
				String show = log.substring(0, showCount);
				Log.i("LOGUTIL", show + "");
				if ((log.length() - showCount) > showCount) {// 剩下的文本还是大于规定长度
					String partLog = log.substring(showCount, log.length());
					showLogCompletion(partLog, showCount);
				} else {
					String surplusLog = log.substring(showCount, log.length());
					Log.i("LOGUTIL", surplusLog + "");
				}
			} else {
				Log.i("LOGUTIL", log + "");
			}
		}
	}

	/**
	 * 使用默认的长度打印出日志
	 * 
	 * @param log
	 */
	public static void showLogCompletion(String log) {
		if (LOG_CONTROL) {
			if (log.length() > defaultShowCount) {
				String show = log.substring(0, defaultShowCount);
				Log.i("LOGUTIL", show + "");
				if ((log.length() - defaultShowCount) > defaultShowCount) {// 剩下的文本还是大于规定长度
					String partLog = log.substring(defaultShowCount,
							log.length());
					showLogCompletion(partLog);
				} else {
					String surplusLog = log.substring(defaultShowCount,
							log.length());
					Log.i("LOGUTIL", surplusLog + "");
				}
			} else {
				Log.i("LOGUTIL", log + "");
			}
		}
	}
	
	/**
	 * 使用标签TAG打印默认长度的日志
	 * @param log
	 * @param TAG
	 */
	public static void showLogCompletion(String log,String TAG) {
		if (LOG_CONTROL) {
			if (log.length() > defaultShowCount) {
				String show = log.substring(0, defaultShowCount);
				Log.i(TAG, show + "");
				if ((log.length() - defaultShowCount) > defaultShowCount) {// 剩下的文本还是大于规定长度
					String partLog = log.substring(defaultShowCount,
							log.length());
					showLogCompletion(partLog,TAG);
				} else {
					String surplusLog = log.substring(defaultShowCount,
							log.length());
					Log.i(TAG, surplusLog + "");
				}
			} else {
				Log.i(TAG, log + "");
			}
		}
	}
}
