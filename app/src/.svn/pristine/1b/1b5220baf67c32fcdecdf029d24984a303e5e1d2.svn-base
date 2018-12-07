package bjut.net.ap.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.UUID;

/**
 * Description:
 * 读取网络信息的类
 * 主要方法:
 * 获取当前网络连接的类型
 * 获取当前网络连接的子类型(如果是 ConnectivityManager.TYPE_MOBILE:)
 * 获取当前电信运营商的名称
 *
 */
public class NetworkUtility
{
	public static final boolean isConnWebservise = true;
	public  static String imei;//用户的手机imei
	public static ScreenUtils utils;
	public static boolean isNetworkConnected(Context context)
	{
		// 获取应用上下文
		if (context == null)
		{
			return false;
		}
		// 获取系统的连接服务
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获取网络的连接情况
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (mNetworkInfo == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 * 网络连接的类型
	 */
	public static final int CONNECT_TYPE_NONE = -1;
	public static final int CONNECT_TYPE_UNKNOWN = 0;
	public static final int CONNECT_TYPE_WIFI = 1;
	public static final int CONNECT_TYPE_BLUETOOTH = 2;
	public static final int CONNECT_TYPE_MOBILE = 3;

	/**
	 * 判断网络连接的类型
	 */
	public static int getNetworkType(Context context)
	{
		// 获取应用上下文
		if (context == null)
		{
			return 0;
		}
		// 获取系统的连接服务
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获取网络的连接情况
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

		if (mNetworkInfo == null)
		{
			// 无网络连接
			return CONNECT_TYPE_NONE;
		}
		switch (mNetworkInfo.getType())
		{
			case ConnectivityManager.TYPE_WIFI:
				return CONNECT_TYPE_WIFI;
			case ConnectivityManager.TYPE_BLUETOOTH:
				return CONNECT_TYPE_BLUETOOTH;
			case ConnectivityManager.TYPE_MOBILE:
				return CONNECT_TYPE_MOBILE;
			default:
				return CONNECT_TYPE_UNKNOWN;
		}
	}

	/**
	 * 网络连接的子类型:
	 * 2G or 3G
	 */
	public static final int CONNECT_SUBTYPE_UNKNOWN = 0;
	public static final int CONNECT_SUBTYPE_2G = 1;
	public static final int CONNECT_SUBTYPE_3G = 2;
	public static final int CONNECT_SUBTYPE_4G = 3;

	public static int getNetworkSubType(Context context)
	{
		// 获取系统的连接服务
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获取网络的连接情况
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (mNetworkInfo == null)
		{
			// 无网络连接
			return -1;
		}
		if (mNetworkInfo.getType() != ConnectivityManager.TYPE_MOBILE)
		{
			return -1;
		}
		/**
		 * 联通的3G为UMTS HSDPA
		 * 电信的3G为EVDO
		 * 移动和联通的2G为GPRS EDGE
		 * 电信的2G为CDMA
		 */
		switch (mNetworkInfo.getSubtype())
		{
			case TelephonyManager.NETWORK_TYPE_UMTS:
			case TelephonyManager.NETWORK_TYPE_HSDPA:
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
			case TelephonyManager.NETWORK_TYPE_EVDO_B:
				return CONNECT_SUBTYPE_3G;

			case TelephonyManager.NETWORK_TYPE_GPRS:
			case TelephonyManager.NETWORK_TYPE_EDGE:
			case TelephonyManager.NETWORK_TYPE_CDMA:
				return CONNECT_SUBTYPE_2G;

			default:
				// 其他网络
				return CONNECT_SUBTYPE_UNKNOWN;
		}
	}

	/**
	 * 获取运营商名字
	 *
	 * @param context
	 * @return
	 *         "中国移动";
	 *         "中国联通";
	 *         "中国电信";
	 */
	public static String getTelecomOperatorByNetwork(Context context)
	{
		// 获取系统的连接服务
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获取网络的连接情况
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (mNetworkInfo == null)
		{
			// 无网络连接
			return "无网络连接";
		}
		if (mNetworkInfo.getType() != ConnectivityManager.TYPE_MOBILE)
		{
			return "mNetworkInfo.getType() Isn't TYPE_MOBILE";
		}

		// 通过联网方式判断
		Log.i("获取运营商名字", "getDetailedState=" + mNetworkInfo.getDetailedState() + "\n" + "getReason=" + mNetworkInfo.getReason()
				+ "\n" + "getSubtype=" + mNetworkInfo.getSubtype() + "\n" + "getSubtypeName=" + mNetworkInfo.getSubtypeName()
				+ "\n" + "getExtraInfo=" + mNetworkInfo.getExtraInfo() + "\n" + "getTypeName=" + mNetworkInfo.getTypeName()
				+ "\n" + "getType=" + mNetworkInfo.getType());
		/**
		 * 联通的3G为UMTS HSDPA
		 * 电信的3G为EVDO
		 * 移动和联通的2G为GPRS EDGE
		 * 电信的2G为CDMA
		 */
		switch (mNetworkInfo.getSubtype())
		{
			case TelephonyManager.NETWORK_TYPE_UMTS:
			case TelephonyManager.NETWORK_TYPE_HSDPA:
				return "中国联通";

			case TelephonyManager.NETWORK_TYPE_CDMA:
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
			case TelephonyManager.NETWORK_TYPE_EVDO_B:
				return "中国电信";

			case TelephonyManager.NETWORK_TYPE_GPRS:
				return "中国联通或者中国移动";
			case TelephonyManager.NETWORK_TYPE_EDGE:
				return "中国移动";
			default:
				return "未知";
		}

	}

	public static String getTelecomOperatorByMNC(Context context)
	{
		// 通过MNC判断
		/**
		 * 获取SIM卡的IMSI码
		 * SIM卡唯一标识：IMSI (International Mobile Subscriber Identification Number)国际移动用户识别码
		 * 是区别移动用户的标志.储存在SIM卡中，可用于区别移动用户的有效信息。
		 * IMSI由MCC、MNC、MSIN组成:
		 * 1.MCC为移动国家号码，由3位数字组成.
		 * 唯一地识别移动客户所属的国家.我国为460
		 * 2.MNC为网络id，由2位数字组成.
		 * 用于识别移动客户所归属的移动网络
		 * 中国移动为00，中国联通为01,中国电信为03
		 * 3.MSIN为移动客户识别码，采用等长11位数字构成。
		 * 唯一地识别国内GSM移动通信网中移动客户。
		 * 所以要区分是移动还是联通，只需取得SIM卡中的MNC字段即可
		 */
		TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		@SuppressLint("MissingPermission") String imsi = telManager.getSubscriberId();
		// 直接获取移动运营商名称
		Log.i("获取运营商名字", "getNetworkOperatorName=" + telManager.getNetworkOperatorName());

		if (imsi != null)
		{
			if (imsi.startsWith("46000") || imsi.startsWith("46002") || imsi.startsWith("46007"))
			{
				// 因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
				return "中国移动";
			}
			else if (imsi.startsWith("46001"))
			{
				return "中国联通";
			}
			else if (imsi.startsWith("46003"))
			{
				return "中国电信";
			}
		}
		return "未知";
	}

	/**
	 * 打开wifi
	 * @return
*/
	public static String GetWifiName(final Context context)
	{
		WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiinfo = wm.getConnectionInfo();
		String wifiname = wifiinfo.getSSID(); //获取被连接网络wifi的名称;
		/**
		 * 开启wifi
		 */
		//判断当前wifi状态是否为开启状态
		if (!wm.isWifiEnabled())
			// 打开wifi 有些设备需要授权
		{	wm.setWifiEnabled(true);}
		/*WifiInfo wi = wm.getConnectionInfo();
		// 获取32位整型IP地址
		int ipAdd = wi.getIpAddress();
		// 把整型地址转换成“*.*.*.*”地址
		String ip = intToIp(ipAdd);
		return ip;*/
		return  wifiname;
	}
	/**
	 * 查看wifi链接状态
	 * @return
	 */
	public static boolean WifiState(final Context context)
	{
		WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//		WifiInfo wifiinfo = wm.getConnectionInfo();
//		String wifiname = wifiinfo.getSSID(); //获取被连接网络wifi的名称;
		//判断当前wifi状态是否为开启状态
		if (!wm.isWifiEnabled())
		{
			// 打开wifi 有些设备需要授权
            //wm.setWifiEnabled(true);
			return  false;
		}
		return  true;
	}
	/**
	 * 查看wifi链接状态
	 * @return
	 */
	public static void OpenWifi(final Context context)
	{
		WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		//判断当前wifi状态是否为开启状态
		if (!wm.isWifiEnabled())
		{
			// 打开wifi 有些设备需要授权
			wm.setWifiEnabled(true);
		}
	}
	private static String intToIp(int i)
	{
		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
	}
	/**
	 * 获取手机IMEI号
	 */
	public static String getIMEI(final Context context) {
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		/*WifiManager mWifiManager=(WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		*//**
		 * 开启wifi
		 *//*
		// 判断当前wifi状态是否为开启状态
		if (!mWifiManager.isWifiEnabled()) {
			// 打开wifi 有些设备需要授权
			mWifiManager.setWifiEnabled(true);
		}*/
		imei= telephonyManager.getDeviceId();
		final String tmDevice, tmSerial, androidId,imsi;
		final String uniqueId;
		imsi=telephonyManager.getSubscriberId();
		tmDevice = "" + telephonyManager.getDeviceId();
		tmSerial = "" + telephonyManager.getSimSerialNumber();
		androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
		UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
		uniqueId = deviceUuid.toString();
		if(imei.equals("000000000000000")){
//			return  uniqueId;
			return  imsi;
		}else
		return imei;
//		return  uniqueId;
	}
}
