package bjut.net.ap.model;


/**
 * 会议信息
 * Created by zhangvalue on 2018/8/4.
 */
public class Meeting {
    private int id;//	会议id
    private String meetingname;//会议名
    private String begintime;//		 会议开始时间
    private String endtime;//	会议结束时间
    private String location;//	 会议的教学楼
    private String courselocation;//会议的具体教室
    private String teachername;//	 教师姓名

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    private String teacherid;//教师id号，用来区别哪些课程属于哪个老师
    private String createtime;//课程创建的时间

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCourselocation() {
        return courselocation;
    }

    public void setCourselocation(String courselocation) {
        this.courselocation = courselocation;
    }

    public String getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMeetingname() {
        return meetingname;
    }

    public void setMeetingname(String meetingname) {
        this.meetingname = meetingname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getBegintime() {
        return begintime;
    }

    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }


    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "meetingname='" + meetingname + '\'' +
                ", begintime='" + begintime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", location='" + location + '\'' +
                ", courselocation='" + courselocation + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }
}
