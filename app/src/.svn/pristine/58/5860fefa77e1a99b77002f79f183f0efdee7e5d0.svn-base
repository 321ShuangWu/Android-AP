package bjut.net.ap.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import bjut.net.ap.receiver.NetReceiver;


/**
 * @author 郑明亮
 * @version 1.0
 *          Function: TODO 跟网络相关的工具类
 * @Time：2016-7-17 下午12:25:15
 */
public class NetUtils {
    private NetUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        boolean flag = false;
        int type = getNetworkType(context);
        if (type == CONNECT_TYPE_WIFI) {
            NetReceiver.isWifiState = true;
            flag = true;
        } else {
            NetReceiver.isWifiState = false;
            flag = false;
        }
        return flag;
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
    public static int getNetworkType(Context context) {
        // 获取应用上下文
        if (context == null) {
            return 0;
        }
        // 获取系统的连接服务
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取网络的连接情况
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

        if (mNetworkInfo == null) {
            // 无网络连接
            return CONNECT_TYPE_NONE;
        }
        switch (mNetworkInfo.getType()) {
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
     * 判断是否是wifi连接
     */
    public static boolean isWifi2(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() == null)
            return false;
        else
            return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }


    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

}
