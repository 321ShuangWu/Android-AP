package bjut.net.ap.model;


public class SUser {
	private String usno	;	//用户学号
	private String uname;	//	用户姓名
	private String utel	;//		用户手机号
	private int   utag	;//	用户标记（预留）
	private String utime ;	//	用户注册时间
	private String imei	;//	唯一标识一个用户

	public SUser(String usno, String uname, String utel, int utag, String utime, String imei) {
		this.usno = usno;
		this.uname = uname;
		this.utel = utel;
		this.utag = utag;
		this.utime = utime;
		this.imei = imei;
	}

	public String getUsno() {
		return usno;
	}

	public void setUsno(String usno) {
		this.usno = usno;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUtel() {
		return utel;
	}

	public void setUtel(String utel) {
		this.utel = utel;
	}

	public int getUtag() {
		return utag;
	}

	public void setUtag(int utag) {
		this.utag = utag;
	}

	public String getUtime() {
		return utime;
	}

	public void setUtime(String utime) {
		this.utime = utime;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

}