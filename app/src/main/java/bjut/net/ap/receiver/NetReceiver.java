package bjut.net.ap.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import bjut.net.ap.utils.ScreenUtils;


public class NetReceiver extends BroadcastReceiver {
    public NetReceiver() {
    }

    private int previousNetType = -1; //变化之前的网络状态
    public static boolean isNetOnLine;
    public static boolean isWifiState = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            NetworkInfo otherNetworkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);
            //String reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);  //网络状态改变的原因
            boolean isFailover = intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false); //wifi和3g是否在切换
            boolean isNoConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false); //网络是否全部断开
            if (isNoConnectivity) {
                previousNetType = -1;
                isNetOnLine = false;
                isWifiState = false;
                ScreenUtils.showToast("网络已断开");
            } else {
                isNetOnLine = true;

                if (isFailover || otherNetworkInfo == null) {
                    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        if (previousNetType != ConnectivityManager.TYPE_WIFI && networkInfo.isConnected()) {
                            previousNetType = ConnectivityManager.TYPE_WIFI;
                            isWifiState = true;//当前状态是wifi连接
                          //TODO 原来这里是写日志到文件中，现在注释掉
//                            AppLog.writed("");//wifi状态下将日志传到服务器端
                            ScreenUtils.showToast("已连接到wifi");
                        }
                    } else {
                        if (previousNetType == ConnectivityManager.TYPE_WIFI || previousNetType == -1) {
                            if (networkInfo.isConnected()) {
                                previousNetType = ConnectivityManager.TYPE_MOBILE;
                                ScreenUtils.showToast( "已连接到移动网络");
                            }
                        }
                    }
                }
            }
        }
    }
}
