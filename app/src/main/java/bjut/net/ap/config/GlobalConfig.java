package bjut.net.ap.config;

import android.text.TextUtils;
import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import bjut.net.ap.MyApplication;
import bjut.net.ap.utils.SharedPreferencesUtil;
import bjut.net.ap.utils.SharedPrefsStrListUtil;

import static android.os.Build.ID;
import static bjut.net.ap.utils.FastJsonTools.createJsonToListString;
import static java.security.AccessController.getContext;

/**
 * @version 1.0
 * TODO 全局常量类
 */
public class GlobalConfig {

    private   static SharedPreferencesUtil spUtils = new SharedPreferencesUtil(MyApplication.mContext);
    /**
     * 用户名
     */
    public static String USERNAME = "";
    /**
     * 用户sno
     */
    public static String USERNO = "";
    /**
     * 用户IMEI
     */
    public static String UIMEI = "";

    /**
     * 用户AndroidId
     */
    public static String UANDROIDID = "";

    /**
     * 用户序列号
     */
    public static String USERIALNUMBER= "";

    /**
     * 用户安装号
     */
    public static String UINSID = "";

    /**
     * 用户唯一识别码
     */
    public static String UDEVICEID = "";
    /**
     * WIFI的名称
     */
    public static String WIFINAME = "";
    /**
     * MAC的名称
     */
    public static String MACNAME = "";
    public static final List<String> APMACGROUP = new ArrayList<String>();
    /**
     * 用来标记签到按钮是否触发，默认为""没有触发
     * 保证的一种互斥性(在上门课程没有结束的时候只能签到一门，时间过了将会自动置为没有触发状态）
     */
    public static  String ISFLAG="";
    /**
     * 用来判断此课程是否成功签到
     */
    public static String IFFLAG = "";
    /**
     * 签到成功课程返回的课程名
     */
    public  static  String SUCCOURSE="";
    /**
     * 签到成功课程返回的教室名
     */
    public  static  String SUCLOCATION="";
    /**
     * 用来标记最近一次签到的课程的签到时间，以便于签到记录撤回和进行flag释放
     */
    public  static  String USIGNTIME="";
    /**
     * 用来标记最近一次签到的课程的结束时间，以便于进行flag释放
     */
    public  static  String UENDTIME="";
    /**
     * 标记最近一次刷新出课程的时间
     */
    public static  String UOLDTIME="";
    /*************************确保以下常量一定有值********************************************/
    public static String getUSERNAME(){
        if (TextUtils.isEmpty(USERNAME)){

            USERNAME = spUtils.readData(UNAME,"");
        }
        return USERNAME;
    }

    public static String getUIMEI(){
        if (TextUtils.isEmpty(UIMEI)){
            UIMEI = spUtils.readData(IMEI,"");
        }

        return UIMEI;
    }

    public static String getUANDROIDID(){
        if (TextUtils.isEmpty(UANDROIDID)){
            UANDROIDID = spUtils.readData(ANDROIDID,"");
        }
        return UANDROIDID;
    }

    public static String getUSERIALNUMBER(){
        if (TextUtils.isEmpty(USERIALNUMBER)){
            USERIALNUMBER = spUtils.readData(SERIALNUMBER,"");
        }
        return USERIALNUMBER;
    }

    public static String getUINSID(){
        if (TextUtils.isEmpty(UINSID)){
            UINSID = spUtils.readData(INSID,"");
        }
        return UINSID;
    }

    public static String getUDEVICEID(){
        if (TextUtils.isEmpty(UDEVICEID)){
            UDEVICEID = spUtils.readData(DEVICEID,"");
        }
        return UDEVICEID;
    }

    public static String getUSERNO(){
        if (TextUtils.isEmpty(USERNO)){
            USERNO = spUtils.readData(USNO,"");
        }

        return USERNO;
    }
    public static String getSUCCOURSE(){
        if (TextUtils.isEmpty(SUCCOURSE)){

            SUCCOURSE = spUtils.readData(SUCC,"");
        }
        return SUCCOURSE;
    }
    public static String getSUCLOCATION(){
        if (TextUtils.isEmpty(SUCLOCATION)){

            SUCLOCATION = spUtils.readData(SUCL,"");
        }
        return SUCLOCATION;
    }
    public static String getIFFLAG(){
        if (TextUtils.isEmpty(IFFLAG)){
            IFFLAG = spUtils.readData(IFLAG,"");
        }
        return IFFLAG;
    }
    public static String getMAC(){
        if (TextUtils.isEmpty(MACNAME)){
            MACNAME = spUtils.readData(MAC,"");
        }
        return MACNAME;
    }
   /* public static List<String> getApgroup(){
        for(int x=0;x<APMACGROUP.size();x++) {
            if (TextUtils.isEmpty(APMACGROUP.get(x))) {
                APMACGROUP.add(spUtils.readData(APGROUP.get(x), "")) ;
            }
        }
        return APMACGROUP;
    }*/
    public static String getWIFINAME(){
        if (TextUtils.isEmpty(WIFINAME)){
            WIFINAME = spUtils.readData(WIFI,"");
        }
        return WIFINAME;
    }
    public static String getFLAG() {
        if (TextUtils.isEmpty(ISFLAG)){
            ISFLAG = spUtils.readData(FLAG,"");
        }
        return ISFLAG;
    }
    public static String gerSIGNTIME() {
        if (TextUtils.isEmpty(USIGNTIME)){
            USIGNTIME = spUtils.readData(SIGNTIME,"");
        }
        return USIGNTIME;
    }

    public static String gerENDTIME() {
        if (TextUtils.isEmpty(UENDTIME)){
            UENDTIME = spUtils.readData(ENDTIME,"");
        }
        return UENDTIME;
    }

    /**
     * 拿到最近一次刷新出课程的时间
     * @return
     */
    public static String gerOLDTIME() {
        if (TextUtils.isEmpty(UOLDTIME)){
            UOLDTIME = spUtils.readData(OLDTIME,"");
        }
        return UOLDTIME;
    }
    /*************************确保以上常量一定有值********************************************/
    /***************** 保存到SharedPreference中的一些key值常量*****************************/
    public static final String APGROUP = "apgroup";
    public static final String USNO = "usno:";
    public static final String UNAME = "uname:";
    public static final String IFLAG="iflag:";
    public static final String SUCC="succ";//课程名
    public static final String SUCL="sucl";//教室
    public static final String IMEI = "imei:";

    public static final String ANDROIDID = "androidid:";
    public static final String SERIALNUMBER = "serialnumber:";
    public static final String DEVICEID = "deviceid:";
    public static final String INSID = "insid:";

    public static final String MAC = "mac:";
    public static final String WIFI = "wifiname:";
    public static final String FLAG="IsFlag:";
    public static final String SIGNTIME="signtime:";
    public static final String COURSEID="courseid:";
    public static final String ENDTIME="endtime:";
    public static final String OLDTIME="oldtime:";

    /**
     * 返回成功信息中包含的标识符
     */
    public static final String SUCCESS_FLAG = "success";
    public static final String ERROR_FLAG = "error";
    public static final String FAILURE_FLAG = "failure";
    /**
     * @Description <p>用户账户被锁定，不可用，可能原因：①账号未激活②账户被锁定 </p>
     */
    public static final String LOCKED = "locked";
    /**
     * f返回失败信息中包含的标识符
     */
    public static final String FAIL_FLAG = "error";
    public static final String NOT_EXISTS_THE_USER = "不存在#!@";
    /**
     * 保存到手机本地后的路径
     */
    public static final String USER_ICON = "userIcon:";
    /**
     *为避免保存到本地的图片被删除，再保存图片的一个网络路径
     */
    public static final String USER_ICON_HTTP = "userIcon:";
    public static final String USER_SEX = "userSex:";
    public static final String USER_BITTHDAY = "userBitrhday:";
    public static final String USER_TEL = "userTel:";
    public static final String USER_EMAIL = "userEmail:";
    public static final String USER_SIGNATURE = "userSignature:";
    public static final String USER_JOB = "userJob:";

    public static final String TOKEN = "token";
    /***************** 保存到SharedPreference中的一些key值常量*****************************/


    /***************** startActivityForResult所用到的key_code*****************************/
    public static final int LOGIN_CODE = 0x123;
    public static final int REGISTER_CODE = 0x124;
    public static final int MAINACTIVITY_CODE =  0x125;

    /***************** startActivityForResult所用到的key_code*****************************/
    /**
     * 拍照回调
     */
    public static final int REQUESTCODE_UPLOADAVATAR_CAMERA = 1;//拍照修改头像
    public static final int REQUESTCODE_UPLOADAVATAR_LOCATION = 2;//本地相册修改头像
    public static final int REQUESTCODE_UPLOADAVATAR_CROP = 3;//系统裁剪头像

    public static final int REQUESTCODE_TAKE_CAMERA = 0x000001;//拍照
    public static final int REQUESTCODE_TAKE_LOCAL = 0x000002;//本地图片
    public static final int REQUESTCODE_TAKE_LOCATION = 0x000003;//位置
    /***************** startActivityForResult所用到的key_code*****************************/
}
