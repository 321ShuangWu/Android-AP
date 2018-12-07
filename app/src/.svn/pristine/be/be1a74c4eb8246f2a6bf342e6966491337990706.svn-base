package bjut.net.ap.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;

import java.util.LinkedList;
import java.util.List;

public class Exit extends Application {
	private List<Activity> activityList = new LinkedList<Activity>();
	private static Exit instance;

	private Exit() {
		exit();
	}
	private Exit(int i){
		exit2();
	}

	/**出当前程序（没有提示框）
	 * @return
	 */
	public static Exit getInstance1() {
		if (instance == null) {
			instance = new Exit();
		}
		return instance;
	}
	
	/**出当前程序（有提示框
	 * @return
	 */
	public static Exit getInstance2() {
		if (instance == null) {
			instance = new Exit();
		}
		return instance;
	}

	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// 遍历有Activity并finish()
	/**
	 * 不带弹出框的出当前应用的方法
	 */
	public void exit() {
		
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);
	}
	/**
	 * 不带弹出框的出当前应用的方法
	 */
	public void exit1() {
		 Intent intent = new Intent(Intent.ACTION_MAIN);
         intent.addCategory(Intent.CATEGORY_HOME);
         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         startActivity(intent);  
     android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	/**
	 * 带弹出框提示的?出当前应用的方法
	 */
	public void exit2() {
		AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).setTitle("提醒")
                .setMessage("是否出程")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
 
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);  
                    android.os.Process.killProcess(android.os.Process.myPid());
                    }
 
                }).setNegativeButton("取消",
 
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                }).create(); // 创建对话
        alertDialog.show(); // 显示对话
	}
}
