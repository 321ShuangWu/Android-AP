package bjut.net.ap.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bjut.net.ap.model.SUser;


public class GsonTools {

	/**
	 * @param jsonString
	 * @param type
	 * @return
	 */
	public static <T> List<T> getDatalist(String jsonString, Class<T> type) {
		List<T> list = new ArrayList<T>();
		Gson gson = new Gson();
		list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
		}.getType());

		return list;

	}
	
	
	
	public static <T> List<T> StringTolist(String jsonString, Class<T[]> type) {
		
		Gson gson = new Gson();
		T[] list  = gson.fromJson(jsonString, type);

		return Arrays.asList(list);

	}

	public static String createJsonString(Object object) {
		Gson gson = new Gson();
		String jsonString = gson.toJson(object);
		return jsonString;

	}

	public static SUser getUser(String jsonString, Class<SUser> type){
		SUser user = null;
		Gson gson=new Gson();
		user = gson.fromJson(jsonString, type);
		
		return user;
		
	}
	
	public static <T> T getObjectData(String jsonString, Class<T> type) {
		  
	    T t = null;  
	    try {  
	        Gson gson = new Gson();  
	        t = gson.fromJson(jsonString, type);  
	    } catch (JsonSyntaxException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    }  
	    return t;  
	}  
}
