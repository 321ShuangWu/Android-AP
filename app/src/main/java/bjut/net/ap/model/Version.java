package bjut.net.ap.model;

/**
 * Android更新版本信息
 * Created by zhangvalue on 2018/1/24.
 */
     /*  android:versionCode——整数值，代表应用程序代码的相对版本，也就是版本更新过多少次。
        整数值有利于其它程序比较，检查是升级还是降级。你可以把这个值设定为任何想设的值，
        但是，你必须保证后续更新版的值要比这个大。系统不会强制要求这一行为，
        但是随着版本更新值也增加是正常的行为。
        一般来说，你发布的第一版程序的versionCode设定为1，然后每次发布都会相应增加，
        不管发布的内容是较大还是较小的。这意味着android:versionCode不像应用程序的发布版本
        （看下面的android:versionName）那样显示给用户。应用程序和发布的服务不应该显示这个版本值给用户。
        android:versionName——字符串值，代表应用程序的版本信息，需要显示给用户。
        与android:versionCode一样，系统不会为了任何内部的目的使用这个值，除了显示给用户外。
        发布的服务也需要提取这个值来显示给用户。*/
    public class Version {
    private int id;
    private int versioncode;//	更新多少次
    private String versionname;//版本名称
    private String updateinfo;//更新的具体信息
    public int getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(int versioncode) {
        this.versioncode = versioncode;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUpdateinfo() {
        return updateinfo;
    }

    public void setUpdateinfo(String updateinfo) {
        this.updateinfo = updateinfo;
    }

    @Override
    public String toString() {
        return "Version{" +
                "id=" + id +
                ", versioncode=" + versioncode +
                ", versionname='" + versionname + '\'' +
                ", updateinfo='" + updateinfo + '\'' +
                '}';
    }
}
