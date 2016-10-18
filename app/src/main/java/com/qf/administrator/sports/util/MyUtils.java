package com.qf.administrator.sports.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用的一些方法工具类
 * @author Administrator
 *
 */
public class MyUtils {

	/**
	 * 设置TextView分段显示颜色 </br>
	 * 
	 * 用法:</br> SpannableStringBuilder style =Tools.setTextDifferent(context,new
	 * String[] { replayModle.getName(),"回复", replayModle.getReturnUserName(),
	 * ": " + replayModle.getContent() },
	 * 
	 * new int[] { R.color.find_name, R.color.dark, R.color.find_name,
	 * R.color.dark });
	 * 
	 * tv.setText(style);
	 * 
	 * @param context
	 * @param strs
	 *            字符串数组
	 * @param colors
	 *            字符串对应颜色Id数组
	 * @return SpannableStringBuilder
	 */
	public static SpannableStringBuilder setTextDifferent(Context context,
			String[] strs, int[] colors) {
		StringBuffer stringBuffer = new StringBuffer();
		for (String str : strs) {
			if (str != null) {
				stringBuffer.append(str);
			}
		}
		SpannableStringBuilder style = new SpannableStringBuilder(new String(
				stringBuffer));
		int len = strs.length;
		int startIndex = 0;
		int endIndex = 0;
		for (int i = 0; i < len; i++) {
			endIndex += strs[i].length();
			style.setSpan(new ForegroundColorSpan(context.getResources()
					.getColor(colors[i])), startIndex, endIndex,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			startIndex = endIndex;

		}
		return style;
	}

	/** dip转换px */
	public static int dip2px(int dip, Context context) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}

	/** pxz转换dip */
	public static int px2dip(int px, Context context) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	/**
	 * 展示土司
	 * 
	 * @param text
	 *            文本内容
	 * @param isLong
	 *            1 为长时间 0 为短时间
	 */
	public static void showToast(String text, int isLong, Context context) {
		if (isLong == 1) {
			Toast.makeText(context, text, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 验证手否是手机号?
	 * 
	 * @param phoneNum
	 *            手机号码
	 * @return
	 */
	public static boolean isPhoneNumber(String phoneNum) {
		// 验证手机的正则表达式
		String str = "^[1][3,4,5,7,8][0-9]{9}$";
		Pattern pattern = Pattern.compile(str);
		Matcher matcher = pattern.matcher(phoneNum);
		return matcher.matches();
	}

	/**
	 * 判断是否是6-16位的数字或字母
	 * 
	 * @param passward
	 * @return
	 */
	public static boolean isPassword(String passward) {
		String str = "^[0-9a-zA-Z]{6,16}$";
		Pattern pattern = Pattern.compile(str);
		Matcher matcher = pattern.matcher(passward);
		return matcher.matches();
	}

	/**
	 * 验证是否是合法用户名
	 * 
	 * @param userName
	 *            :字母+数字
	 * @return
	 */
	public static boolean isUserName(String userName) {
		String str = "(^[A-Za-z0-9]{2,16}$)|(^[\u4E00-\u9FA5]{2,8}$)";
		Pattern pattern = Pattern.compile(str);
		Matcher matcher = pattern.matcher(userName);
		return matcher.matches();
	}

	// 判断是否是邮件地址
	public static boolean isEmail(String email) {
		String check = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern pattern = Pattern.compile(check);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	/**
	 * 判断是否联网的方法
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

}
