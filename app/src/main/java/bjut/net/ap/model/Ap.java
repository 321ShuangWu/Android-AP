package bjut.net.ap.model;

/**AP信息
 * Created by zhangvalue on 2017/12/11.
 */
public class Ap {
   private String mac	;//	mac地址

    private  int parentid;

    private String  location;//	地址信息

    public int getParentid() {
        return parentid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }


    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Ap(String mac, String location) {
        this.mac = mac;
        this.location = location;
    }

    public Ap(String mac, int parentid, String location) {
        this.mac = mac;
        this.parentid = parentid;
        this.location = location;
    }
}
