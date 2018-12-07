package bjut.net.ap.utils;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import bjut.net.ap.config.GlobalConfig;

/**
 * SharedPreferences工具类，主要用于读取和保存小型数据到SharedPreferences中；
 * @author zsf
 * @Time：2018-2-13 下午10:06:23
 * @version 1.0  
 */
public class SharedPreferencesUtil {
	
	Context context;
	SharedPreferences preferences;
	
	public SharedPreferencesUtil(Context context) {
		this.context  = context;
		preferences = context.getSharedPreferences("zhangvalue", Context.MODE_PRIVATE );
	}
	
	public  boolean readIsFirstUse(Context context){
		
		boolean isUse=preferences.getBoolean("IsFirst", false);
		return isUse;
	}
	
	public void writeIsFirstUse(Context context){
		Editor editor=preferences.edit();
		editor.putBoolean("IsFirst", true);
		editor.commit();
	}
	
	/**将数据保存到SharedPreferences中
	 * @param key
	 * @param value String类型
	 */
	public void writeData(String key, String value){
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
		
	}
	/**
	 * 获取SharedPreferences中数据
	 * @param key
	 * @param defValue String类型
	 * @return
	 */
	public String readData(String key, String defValue) {
		String dataString = preferences.getString(key, defValue);
		return dataString;
	}
	/**将数据保存到SharedPreferences中
	 * @param key
	 * @param value Boolean类型
	 */
	public void writeData(String key, boolean value){
		Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
		
	}
	/**将数据保存到SharedPreferences中
	 * @param key
	 * @param value int类型
	 */
	public void writeData(String key, int value){
		Editor editor = preferences.edit();
		editor.putInt(key, value);
		editor.commit();
		
	}


	public int readData(String key, int defValue) {
		int dataString = preferences.getInt(key, defValue);
		return dataString;
	}

	/**获取SharedPreferences中数据
	 * @param key
	 * @param defValue boolean类型
	 * @return
	 */
	public Boolean readData(String key, Boolean defValue) {
		Boolean data = preferences.getBoolean(key, defValue);
		return data;
	}
}


