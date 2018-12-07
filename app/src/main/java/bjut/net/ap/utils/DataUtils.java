package bjut.net.ap.utils;

import java.security.MessageDigest;

import bjut.net.ap.config.URLConfig;

/**
 * Created by 张胜凡 on 2018/1/7 20:15.
 */

public class DataUtils {

    /**
     * @Description <p> 计算生成校验码  </p>
     * @param longSign 签到的时间值
     * @return
     */
    public static String getRequestSign(String longSign) {
        if (longSign == null)
            return "";
        return string2MD5(longSign + URLConfig.COUPON_KEY);
    }
    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr) {
        String digest = null;
        StringBuffer buffer = new StringBuffer();
        try {
            MessageDigest digester = MessageDigest.getInstance("md5");
            byte[] digestArray = digester.digest(inStr.getBytes());
            for (int i = 0; i < digestArray.length; i++) {
                buffer.append(String.format("%02x", digestArray[i]));
            }
            digest = buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return digest;
      /*  MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();*/
    }
}
