package bjut.net.ap.model;

/**
 * Created by zhangvalue on 2018/8/9.
 * 会议签到实体类
 */
public class MeetingSign {
    private int id;
    private String sno;//	学号
    private String sname;//	学生名
    private String imei;//	imei标记每个设备
    private String signtime;//	签到时间
    private String meetingname;//会议名
    private String teachername;//授课教师名
    private int meetingid;//会议id
    private String checkcode;//校验标识参数用来防止非法请求
    private String begintime;//会议开始时间
    private String endtime;//会议结束时间

    public void setMeetingname(String meetingname) {
        this.meetingname = meetingname;
    }


    private String courselocation;//会议地点
    private String location;//会议的具体教学楼


    public MeetingSign() {

    }
    public MeetingSign( String sno,  String imei,String location, String signtime,String checkcode, int meetingid) {
        this.sno = sno;
        this.imei = imei;
        this.signtime = signtime;
        this.checkcode = checkcode;
        this.meetingid = meetingid;
        this.location = location;
    }
    public MeetingSign( String sno, String sname, String imei, String signtime, String meetingname, String teachername, String checkcode, int meetingid, String begintime,String endtime,String courselocation,String location) {
        this.sno = sno;
        this.sname = sname;
        this.imei = imei;
        this.signtime = signtime;
        this.meetingname = meetingname;
        this.teachername = teachername;
        this.checkcode = checkcode;
        this.meetingid = meetingid;
        this.begintime = begintime;
        this.endtime = endtime;
        this.courselocation = courselocation;
        this.location = location;
    }

    public String getSno() {
        return sno;
    }

    public int getMeetingid() {
        return meetingid;
    }

    public void setMeetingid(int meetingid) {
        this.meetingid = meetingid;
    }

    @Override
    public String toString() {
        return "MeetingSign{" +
                "sno='" + sno + '\'' +
                ", sname='" + sname + '\'' +
                ", imei='" + imei + '\'' +
                ", signtime='" + signtime + '\'' +
                ", meetingname='" + meetingname + '\'' +
                ", teachername='" + teachername + '\'' +
                ", meetingid=" + meetingid +
                ", begintime='" + begintime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", courselocation='" + courselocation + '\'' +
                ", location='" + location + '\'' +
                '}';
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

    public String getMeetingname() {
        return meetingname;
    }


    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }



    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    public String getBegintime() {
        return begintime;
    }

    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getCourselocation() {
        return courselocation;
    }

    public void setCourselocation(String courselocation) {
        this.courselocation = courselocation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
