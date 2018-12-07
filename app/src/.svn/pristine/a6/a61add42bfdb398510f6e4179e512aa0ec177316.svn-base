package bjut.net.ap.model;

import java.io.Serializable;

/**
 * @Description <p>消息返回类  </P>
 * @version 1.0  
 */
public class Tidings<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @Description <p>状态值：success 、error </P>
	 * @version 1.0  
	 */
	private String status;
	/**
	 * @Description <p>状态消息说明  </P>
	 * @version 1.0  
	 */
	private String msg;
	/**
	 * @Description <p>返回实体内容 </P>
	 * @version 1.0  
	 */
	private T t;

	/**
	 * @Description <p> 获取状态码值   </p>
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @Description <p> 设置状态码  </p>
	 * @param status 状态码
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @Description <p> 设置状态码说明  </p>
	 * @return
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @Description <p>设置状态码说明   </p>
	 * @param msg 状态码说明
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	/**
	 * @Description <p> 获取消息信息  </p>
	 * @return
	 */
	public T getT() {
		return t;
	}

	/**
	 * @Description <p> 设置消息信息  </p>
	 * @param t 消息信息
	 */
	public void setT(T t) {
		this.t = t;
	}

	@Override
	public String toString() {
		return "Tidings [status=" + status + ", msg=" + msg + ", t=" + t + "]";
	}

}
