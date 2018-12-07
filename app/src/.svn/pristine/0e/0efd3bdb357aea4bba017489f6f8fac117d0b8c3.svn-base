package bjut.net.ap.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import bjut.net.ap.R;
import bjut.net.ap.ui.base.BaseActivity;
import bjut.net.ap.utils.ScreenUtils;
import bjut.net.ap.utils.SharedPreferencesUtil;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {
//    @BindView(R.id.webView)
//    WebView webView;
    ScreenUtils utils;
    SharedPreferencesUtil spUtils;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //绑定该布局
        ButterKnife.bind(this);
        initTitle("关于AP" );
//        initTitle("Web聊天");
        //实例化工具类
     /*   utils = new ScreenUtils(this);
        spUtils = new SharedPreferencesUtil(this);
        webView.getSettings().setJavaScriptEnabled(true);//允许运行js脚本
//        webView.loadUrl("file:///android_asset/about/about.html");
        webView.loadUrl(URLConfig.ABOUT_APP);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                utils.showLoading();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                utils.cancleLoading();
            }
        });*/
    }


    public static void newInstance(Context context) {
        Intent intent = new Intent(context,AboutActivity.class);
         context.startActivity(intent);
         ((Activity)context).overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}
