package bjut.net.ap.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import bjut.net.ap.MyApplication;
import bjut.net.ap.R;
import bjut.net.ap.config.URLConfig;


/**
 * 屏幕工具
 * @version 1.0
 */
public class ScreenUtils {
	private ProgressDialog progresdialog;
	private Context context;
	private TextView tv_tittle;

	public ScreenUtils(Context context) {
		this.context = context;
	}


	/**
	 * 显示界面提示信息
	 *
	 * @param text
	 */
	public void ToastInfo(String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();

	}
	private static Toast toast;

	/**
	 * 显示界面提示信息
	 *
	 * @param text
	 */
	public static void showToast(String text) {
		if (toast == null) {
			toast = Toast.makeText(MyApplication.mContext, text, Toast.LENGTH_LONG);
		} else {
			//如果toast不为空，则直接更改当前toast的文本
			toast.setText(text);
		}
		toast.show();

	}
	/**
	 * 显示等待提示
	 */
	public void showLoading() {
		progresdialog = new ProgressDialog(context);
		progresdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progresdialog.setMessage("加载中，请稍后…");
		progresdialog.show();

	}

	/**
	 * 取消等待提示
	 */
	public void cancleLoading() {

		if (progresdialog.isShowing()) {
			progresdialog.cancel();

		}
	}

	/**
	 * 创建提示弹框Dialog
	 *
	 * @param iconId
	 * @param title
	 * @param message
	 * @param positiveBtnName
	 * @param negativeBtnName
	 * @param positiveBtnListener
	 * @param negativeBtnListener
	 * @return
	 */
	public void createConfirmDialog(
			int iconId,
			String title,
			String message,
			String positiveBtnName,
			String negativeBtnName,
			android.content.DialogInterface.OnClickListener positiveBtnListener,
			android.content.DialogInterface.OnClickListener negativeBtnListener) {
		// Dialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(iconId);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(positiveBtnName, positiveBtnListener);
		builder.setNegativeButton(negativeBtnName, negativeBtnListener);
		builder.create().show();

	}
	public void createConfirmDialog(
			int iconId,
			String title,
			String message,
			String positiveBtnName,
			String negativeBtnName,
			String neutralname,
			android.content.DialogInterface.OnClickListener positiveBtnListener,
			android.content.DialogInterface.OnClickListener negativeBtnListener,
			android.content.DialogInterface.OnClickListener neutralBtnListener
	) {
		// Dialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(iconId);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(positiveBtnName, positiveBtnListener);
		builder.setNegativeButton(negativeBtnName, negativeBtnListener);
		builder.setNeutralButton(neutralname, neutralBtnListener);
		builder.setCancelable(false);
		builder.create().show();

	}

	/**
	 * 创建提示弹框
	 *
	 * @param title
	 * @param message
	 * @param positiveBtnName
	 * @param positiveBtnListener
	 * @return
	 */
	public void createConfirmDialog(String title, String message,
                                    String positiveBtnName,
                                    android.content.DialogInterface.OnClickListener positiveBtnListener) {
		Dialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(positiveBtnName, positiveBtnListener);
		// dialog = builder.create();
		builder.create().show();
		// return dialog;
	}
	/**提示信息并跳转到某Activity
	 * @param toast
	 * @param cls
	 * @param time 多长时间进行跳转
	 */
	public void ToastAndIntent(String toast,
                               @SuppressWarnings("rawtypes") final Class cls, long time) {

		ToastInfo(toast);
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				Intent intent = new Intent(context, cls);
				context.startActivity(intent);
				((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}
		};
		timer.schedule(task, time);

	}

	/**判断没有登录，提示登录，并跳转到登录界面
	 */
	public void toLogin() {

		ToastInfo("请先登录哦亲 ^-^");
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
//					Intent intent = new Intent(context, .class);
//					context.startActivity(intent);

			}
		};
		timer.schedule(task, 1600);

	}
	/**
	 *aboutAuthor:(关于作者的相关信息).
	 */
	public  void aboutAuthor() {
		createConfirmDialog(
				0,
				"作者：北京工业大学",
				"学校：北京工业大学\n学院：软件学院412\nCSDN博客：http://blog.csdn.net/zhangvalue",
				"看我博客", "加入技术交流群^-^","下次吧", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse(URLConfig.ABOUT_APP));
						context.startActivity(intent);
					}
				}, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse(URLConfig.ABOUT_APP));
						context.startActivity(intent);
					}
				},
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

					}
				});
	}
	/**
	 * @param time 在time时间内关闭当前界面
	 */
	public void finishCurrentViewInTimes(long time) {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				((Activity) context).finish();
			}
		};
		timer.schedule(task, time);
	}
}
	
	
	
