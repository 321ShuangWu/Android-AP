package bjut.net.ap.model;

/**
 * Created by zhangvalue on 2017/12/20.
 */

/**
 * 签到实体类
 */
public class Sign {
    private String sno;//学号
    private String sname;//学生名
    private String imei;//imei标记每个设备
    private String signtime;//签到时间
    private String coursename;//课程名
    private String teachername;//教师名
    private String coursetime;//这门课程的上课时间
    private String courselocation;//上课地点
    public String getCoursetime() {
        return coursetime;
    }

    public void setCoursetime(String coursetime) {
        this.coursetime = coursetime;
    }

    public Sign(String imei, String signtime, int courseid) {
        this.imei = imei;
        this.signtime = signtime;
        this.courseid = courseid;
    }

    public Sign(String sno, String sname, String imei, String signtime, String coursename,int courseid) {
        this.sno = sno;
        this.sname = sname;
        this.imei = imei;
        this.signtime = signtime;
        this.coursename = coursename;
        this.courseid = courseid;
    }

    private  int courseid;//课程id
    private  String checkcode;//校验标识参数用来防止非法请求

    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }

    public Sign() {
    }

    public Sign(String sno, int courseid) {
        this.sno = sno;
        this.courseid = courseid;
    }


    public Sign(int courseid, String sno, String sname, String imei, String signtime, String coursename, String teachername, String coursetime, String checkcode, String courselocation) {
        this.sno = sno;
        this.sname = sname;
        this.imei = imei;
        this.signtime = signtime;
        this.coursename = coursename;
        this.courselocation = courselocation;
        this.teachername = teachername;
        this.coursetime = coursetime;
        this.courseid = courseid;
        this.checkcode = checkcode;
    }



    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSigntime() {
        return signtime;
    }

    public void setSigntime(String signtime) {
        this.signtime = signtime;
    }

    public String getCoursename() {
        return coursename;
    }

    public String getCourselocation() {
        return courselocation;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }
}
