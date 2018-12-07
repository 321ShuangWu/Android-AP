package bjut.net.ap.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import bjut.net.ap.R;
import bjut.net.ap.config.GlobalConfig;
import bjut.net.ap.config.URLConfig;
import bjut.net.ap.model.Tidings;
import bjut.net.ap.model.Version;
import bjut.net.ap.utils.AppUtils;
import bjut.net.ap.utils.NetUtils;
import bjut.net.ap.utils.NetworkUtility;
import bjut.net.ap.utils.ScreenUtils;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;

import static bjut.net.ap.utils.FastJsonTools.createJsonBean;
/**
 * @version 1.0
 */
public class WelComeActivity extends Activity {
	private static final long DELAY_TIME =500L;
	LinearLayout layout;
	private ScreenUtils utils;
	//TODO 要换掉欢迎背景图片
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//全屏显示
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_welcome);
		layout = findViewById(R.id.welcome);
		utils = new ScreenUtils(this);
		AlphaAnimation Animation = new AlphaAnimation(0.1f, 1f);
		Animation.setDuration(1000);
		Animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(android.view.animation.Animation animation) {

				switch (NetworkUtility.getNetworkType(getBaseContext())) {
				case NetworkUtility.CONNECT_TYPE_NONE:
					Toast.makeText(getBaseContext(), "当前网络连接不可用！",
							Toast.LENGTH_SHORT).show();
					break;
				case NetworkUtility.CONNECT_TYPE_UNKNOWN:
					Toast.makeText(getBaseContext(), "当前网络连接未知",
							Toast.LENGTH_SHORT).show();
					break;
				case NetworkUtility.CONNECT_TYPE_BLUETOOTH:
					Toast.makeText(getBaseContext(), "当前处于蓝牙网络,正在加载...",
							Toast.LENGTH_SHORT).show();
					break;
				case NetworkUtility.CONNECT_TYPE_MOBILE:
					switch (NetworkUtility.getNetworkSubType(getBaseContext())) {
					case NetworkUtility.CONNECT_SUBTYPE_2G:
						Toast.makeText(getBaseContext(), "当前处于2G网络,正在加载...",
								Toast.LENGTH_SHORT).show();
						break;
					case NetworkUtility.CONNECT_SUBTYPE_3G:
						Toast.makeText(getBaseContext(), "当前处于3G网络,正在加载...",
								Toast.LENGTH_SHORT).show();
						break;
					}
					break;
				case NetworkUtility.CONNECT_TYPE_WIFI:
					Toast.makeText(getBaseContext(), "当前处于Wi-Fi网络,正在加载...",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}

			@Override
			public void onAnimationRepeat(android.view.animation.Animation animation) {
				// TODO 自动生成的方法存

			}

			@Override
			public void onAnimationEnd(android.view.animation.Animation animation) {
//				redirectByTime();

			}
		});
		layout.setAnimation(Animation);
		checkversion();
	}

	private void redirectByTime() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
					startActivity(new Intent(WelComeActivity.this,
							MainActivity.class));
					finish();
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);

			}
		}, DELAY_TIME);
	}

	@Override
	protected void onStart() {

		HiPermission.create(WelComeActivity.this).checkSinglePermission(Manifest.permission.READ_PHONE_STATE, new PermissionCallback() {
			@Override
			public void onClose() {

			}

			@Override
			public void onFinish() {

			}

			@Override
			public void onDeny(String permission, int position) {

			}

			@Override
			public void onGuarantee(String permission, int position) {

			}
		});
		super.onStart();
	}
	/**
	 * 检查版本更新信息
	 */
	private void checkversion() {
		RequestParams params = new RequestParams(URLConfig.BASE_URL + URLConfig.CHECKVERSION);
		params.setCharset("UTF-8");
		params.addBodyParameter("versioncode", String.valueOf(AppUtils.getAppVersionCode(this)));

		final ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage(getResources().getString(R.string.Is_the_search));
		pd.show();
		x.http().post(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String result) {
				if (result != null) {
					Log.i("zhangvalue", "传输回来的result:" + result);
					Tidings tidings = createJsonBean(result, Tidings.class);
					//返回的状态为success
					if (GlobalConfig.SUCCESS_FLAG.equals(tidings.getStatus())) {
						Version newData = createJsonBean(tidings.getT().toString(), Version.class);
						//获取数据成功后，判断是否为null
						if (newData != null) {
							new AlertDialog.Builder(WelComeActivity.this)
									.setTitle("发现新版本")
									.setMessage(tidings.getMsg())
									.setCancelable(false)//在弹框弹出来的时触发其他地方不让他消失
									.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											if (NetUtils.isWifi(WelComeActivity.this)) {
												//wifi下载
												Intent intent = new Intent(Intent.ACTION_VIEW);
												intent.setData(Uri.parse(URLConfig.DOAWNLOAD_URL));
												WelComeActivity.this.startActivity(intent);
												dialog.dismiss();

											} else {
												utils.createConfirmDialog(
														0,
														"提示：", "当前处于非wifi状态下是否继续下载？",
														"确定继续下载", "下次再说"
														, new DialogInterface.OnClickListener() {

															@Override
															public void onClick(DialogInterface dialog, int which) {
																//下载
																Intent intent = new Intent(Intent.ACTION_VIEW);
																intent.setData(Uri.parse(URLConfig.DOAWNLOAD_URL));
																WelComeActivity.this.startActivity(intent);
																dialog.dismiss();
																//当点击了下载的时候就可以允许继续跳转
																redirectByTime();
															}
														}, new DialogInterface.OnClickListener() {

															@Override
															public void onClick(DialogInterface dialog, int which) {
																dialog.dismiss();
																//当点击了下次再说的时候就可以允许继续跳转
																redirectByTime();
															}
														});

											}
										}
									})
									/*.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											//当点击了以后再说的时候就可以允许继续跳转
											redirectByTime();
										}
									})*/
									.show();

						}
					}else {
						utils.createConfirmDialog("提示：", "目前已经是最新版本\n",
								"好的", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										dialog.dismiss();
										//当目前已经是最新版本可以允许继续跳转
										redirectByTime();
									}
								});
					}
				}
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				Log.e("zhangvalue", ex.getMessage());
				utils.createConfirmDialog("提示：", "1,请检查网络链接\n2,服务器又开小差了>_<", "好的",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								//当请检查网络链接服务器又开小差可以允许继续跳转
								redirectByTime();
							}
						});
			}

			@Override
			public void onCancelled(CancelledException cex) {
			}

			@Override
			public void onFinished() {
				pd.dismiss();//取消加载
			}
		});
	}
}
