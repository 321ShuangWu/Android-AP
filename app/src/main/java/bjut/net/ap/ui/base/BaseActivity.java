package bjut.net.ap.ui.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import bjut.net.ap.R;
import bjut.net.ap.receiver.NetReceiver;
import bjut.net.ap.utils.AppManager;
import bjut.net.ap.utils.NetUtils;

/**
 * @version 1.0 应用程序Activity的基类
 * @Time：2016-7-18 下午5:49:13
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String VIEW_CLICK = "bjut.ui.base.BaseActivity";

    protected int mScreenWidth;
    protected int mScreenHeight;
    private Dialog progressDialog;

    public void showProgressDialog() {
        progressDialog.show();
    }
    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    // 是否允许全屏
    private boolean mAllowFullScreen = true;
    private boolean isWifiState = false;

    /**
     * 视图控件的初始化 ，一定要将setContentView 放到这个方法中，否则会空指针
     */
//	public abstract void initWidget();
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isWifiState = NetUtils.isWifi2(this);
        //获得当前屏幕的宽和高
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;

        if (isWifiState) {//更改receiver中的wifi状态
            NetReceiver.isWifiState = true;
//        ScreenUtils.showToast("wifi状态");
        } else {
            NetReceiver.isWifiState = false;
//            ScreenUtils.showToast("非wifi状态");
        }

//        if (savedInstanceState != null) {
//            time = savedInstanceState.getLong("time");
//            gaps = savedInstanceState.getLong("gaps");
//        }
        // 竖屏锁定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (mAllowFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE); // 取消标题
        }
        AppManager.getAppManager().addActivity(this);
    }



    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            LocalBroadcastManager broadcastManager = LocalBroadcastManager
                    .getInstance(this);
            Intent intent = new Intent(VIEW_CLICK);
            intent.putExtra(VIEW_CLICK, e);
            broadcastManager.sendBroadcast(intent);
        }
        return super.dispatchTouchEvent(e);
    }

    /**
     * 在没有数据显示的时候，要展示的布局
     * @param tips 要覆盖显示的布局上的文字
     *@param visibility 改布局是否可见
     */
    protected void setNothingCover(String tips, int visibility){
        FrameLayout nothing_cover = findViewById(R.id.nothing_cover);
        nothing_cover.setVisibility(visibility);
        TextView textView =  findViewById(R.id.nothing_tips);
        if (!TextUtils.isEmpty(tips)){
            textView.setText(tips);
        }


}
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /*
     * 临时保存日志信息，以避免线程被系统回收
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        outState.putString("time", System.currentTimeMillis() + "");
//        outState.putString("gaps", System.currentTimeMillis() - time + "");
        super.onSaveInstanceState(outState);
//        AppLog.d( "---------onSaveInstanceState ") 不做过多日志记录;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }





    /**
     * 隐藏软键盘
     * hideSoftInputView
     */
    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * TODO 设置带返回按钮的标题栏
     *
     * @param titleString 标题
     */
    protected void initTitle(String titleString) {
        TextView title =  findViewById(R.id.tv_title);
        TextView back =  findViewById(R.id.tv_back);
        title.setText(titleString+"");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    /**
     * TODO 设置标题栏(不带返回按钮)
     *
     * @param titleString 标题
     */
    protected void initTitleNoBack(String titleString) {
        TextView title =  findViewById(R.id.tv_title);
        title.setText(titleString);
    }


    /**
     * 三方应用程序的过滤器
     *
     * @param info 应用信息
     * @return true 三方应用 false 系统应用
     */

    public boolean filterApp(ApplicationInfo info) {
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            // 代表的是系统的应用,但是被用户升级了. 用户应用
            return true;
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            // 代表的用户的应用
            return true;
        }
        return false;
    }

}