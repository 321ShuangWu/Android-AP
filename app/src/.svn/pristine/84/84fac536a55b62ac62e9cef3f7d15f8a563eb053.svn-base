package bjut.net.ap.utils;

import android.support.annotation.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {
	 // 使用genymotion模拟器的是10.0.3.2其他模拟器是 10.0.2.2
		// public static final String BASEURL = "http://10.0.3.2:8080/iBeacon/";
	// 阿里云服务器端网址
    // public static final String BASEURL = "http://139.129.44.63:8080/iBeacon/";
	public static final String BASEURL = "http://10.18.17.94:8880/";
	public static final String LOGIN = "user/addSuser";
	public static boolean isEmpty(@Nullable CharSequence str) {
		if (str == null || str.length() == 0)
			return true;
		else
			return false;
	}

	/**
	 * 校验邮箱是否合法
	 *
	 * @param s 传入的邮箱字符串
	 * @return
	 */
	public static boolean isEmail1(String s) {
		Pattern pattern = Pattern
				.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(s);
		return matcher.matches();
	}
	/**
	 * 验证手机号是否合法
	 *
	 * @param mobiles 手机号码
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

		Matcher m = p.matcher(mobiles);

		System.out.println(m.matches() + "---");
		return m.matches();


	}
}
