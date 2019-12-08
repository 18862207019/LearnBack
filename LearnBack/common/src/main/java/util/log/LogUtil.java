package util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志打印工具类
 * demo:
 * ******************************************************************
 *         		   LogUtil.info("用户{}在{}被踢出登录状态,api:{}",
 *                 Log.userid(user.getUserId()),
 *                 new Date(),
 *                 Log.api(request.getRequestURI()));
 * &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
 */
public class LogUtil {
	
	public static final Logger logger = LoggerFactory.getLogger(LogUtil.class);  
	
	
	public static void error(Throwable e){
		logger.error("ErrorMsg  :  :", e) ;
	}
	
	public static void error(String msg , Throwable e){
		logger.error(msg, e) ;
	}
	
	public static void info(Throwable e){
		logger.error("ErrorMsg  :  :", e) ;
	}
	
	public static void info(String e){
		logger.info("infoMsg  :  :"+ e) ;
	}
	
	public static void info(String msg , String e){
		logger.info(msg + e) ;
	}

	/**
	 * 接受可变参数
	 * @param var1
	 * @param var2
	 */
	public static void error(String var1, Object... var2){
		logger.error(var1, var2) ;
	}

	/**
	 * 接受可变参数
	 * @param var1
	 * @param var2
	 */
	public static void info(String var1, Object... var2){
		logger.info(var1, var2) ;
	}

	/**
	 * 接受可变参数
	 * @param var1
	 * @param var2
	 */
	public static void debug(String var1, Object... var2){
		logger.debug(var1, var2) ;
	}
	
}
