package bjut.net.ap.model;

/** 课程信息
 * Created by zhangvalue on 2017/12/10.
 */
public class Course {
    private int id;//	课程id
    private String coursename;//		 课程名
    private String begintime;//		 上课时间
    private String location;//	 教室名
    private String teachername;//	 教师姓名
    private String beginweek;    //	起始周
    private String endweek;//	结束周
    private String flag;    //	表示是0全周，1单周，2双周
    private String week;    //	表示是第几周的课程

    public String getBeginweek() {
        return beginweek;
    }

    public void setBeginweek(String beginweek) {
        this.beginweek = beginweek;
    }

    public String getEndweek() {
        return endweek;
    }

    public void setEndweek(String endweek) {
        this.endweek = endweek;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
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
}
