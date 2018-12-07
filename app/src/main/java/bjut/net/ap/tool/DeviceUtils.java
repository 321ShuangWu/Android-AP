package bjut.net.ap.tool;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DeviceUtils {
    public String androidID;
    public String serialNumber;
    public String id;
    public String getUniqueId(Context context) {
        androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        serialNumber = Build.SERIAL ;
        id = androidID + serialNumber;
        try {
            return toMD5(id);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return id;
        }
    }

    private static String toMD5(String text) throws NoSuchAlgorithmException {
        //获取摘要器 MessageDigest
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        //通过摘要器对字符串的二进制字节数组进行hash计算
        byte[] digest = messageDigest.digest(text.getBytes());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            //循环每个字符 将计算结果转化为正整数;
            int digestInt = digest[i] & 0xff;
            //将10进制转化为较短的16进制
            String hexString = Integer.toHexString(digestInt);
            //转化结果如果是个位数会省略0,因此判断并补0
            if (hexString.length() < 2) {
                sb.append(0);
            }
            //将循环结果添加到缓冲区
            sb.append(hexString);
        }
        //返回整个结果
        return sb.toString();
    }

   /* public static Map getMeid(Context ctx) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Map<String, String> map = new HashMap<String, String>();
        TelephonyManager mTelephonyManager = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        Class<?> clazz = null;
        Method method = null;
        clazz = Class.forName("android.os.SystemProperties");
        method = clazz.getMethod("get", String.class, String.class);
        //String gsm = (String) method.invoke(null, "ril.gsm.imei", "");
        String meid = (String) method.invoke(null, "ril.cdma.meid", "");
        map.put("meid", meid);
        return map;
    }*/
}
