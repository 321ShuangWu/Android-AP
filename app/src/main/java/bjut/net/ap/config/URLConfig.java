package bjut.net.ap.config;
/**
 * @version 1.0
 * TODO 用于管理URL的相关常量
 */
public class URLConfig {
    public static final  String  COUPON_KEY="anonymous_29@SWp~0l,awpmx%Fa@a`!";
    /**
     * 服务器的ip地址，不包含端口号,不含http前缀
     */
     public static final String SERVER_IP_SIMPLE = "39.106.53.215";//阿里云服务器
//      public static final String SERVER_IP_SIMPLE = "172.21.60.90";
    // public static final String SERVER_IP_SIMPLE = "10.18.17.144";//实验室bjut_wifi
    //public static final String SERVER_IP_SIMPLE = "101.201.233.134";
    /**
     * 服务器端口号
     */
     public static final String SERVER_PORT = "8080";//阿里云端口
    //public static final String SERVER_PORT = "8880";//本机端口
    //public static final String SERVER_PORT = "80";
    /**
     * 服务器地址以及项目名称
     */
      public static final String BASE_URL = "http://"+ SERVER_IP_SIMPLE +":" + SERVER_PORT + "/ap/";
    //  public static final String BASE_URL = "http://"+ SERVER_IP_SIMPLE +":" + SERVER_PORT + "/";
    /**
     * 用户登录，要求必须是post请求
     */
    public static final String LOGIN = "SysUserController/login";
    /**
     * 用户注册要求必须是post请求
     */
    public static final String REGISTER = "user/addSUser";
    /*上传mac地址*/
    public static final String UPLOADMAC = "ap/uploadMac";
  /*上传mac地址*/
  public static final String UPLOADMACHELP = "ap/addApHelp";
    /*获取当前mac地址下面的课程信息*/
    public static final String GETCOURSE = "ap/getCourse";
    /*获取当前mac地址下面的课程信息*/
    public static final String GETMEETING= "meeting/getMeeting";

    /*获取Apmac组地址*/
    public static final String GETAPGROUP = "ap/getLocation";
    /*进行签到的*/
    public static final String SIGNON = "sign/signOn";


    /*会议进行签到的*/
    public static final String MSIGNON = "meeting/signOn";
    /*取消签到的*/
    public static final String SIGNOFF= "sign/signOff";    /*获取所有的历史签到信息*/

    public static final String MSIGNOFF= "meeting/signOff";

    // 活动签到
    public static final String GETMSIGN = "meeting/getMeeting";
    public static final String GETSIGN = "sign/getSign";



    /*获取单门课程的的历史签到信息*/
    public static final String GETSINGLESIGN = "sign/getSingleSign";
    /*获取单门课程的的历史签到信息*/
   public static final String FEED_BACK = "ap/addFeedBack";

  /*获取盖imei最近一次的签到信息*/
  public static final String GETLATESTSIGN = "sign/getLatestSign";

  /*Android端检查app的版本信息*/
  public static final String CHECKVERSION = "down/updateApp";

    /**
     * app下载地址
     */
    public static final String DOAWNLOAD_APP = "down/downloadApp";

    /**
     * 关于应用的介绍
     */
    public static final java.lang.String ABOUT_APP = "http://39.106.53.215/webchat/user/login";

//    public static final java.lang.String ABOUT_APP = "http://39.106.53.215:8080/ap/assets/about.html";
    /**
     * app下载二维码
     */
    public static final String DOAWNLOAD_URL = "http://39.106.53.215:8080/ap/down/downloadApp";
    /**
     * AP组的接口返回
     */

    public static final String APGROUP_URL = "http://39.106.53.215:8080/ap/ap/getLocation";

}
