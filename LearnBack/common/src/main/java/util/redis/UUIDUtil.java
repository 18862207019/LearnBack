package util.redis;

import java.util.UUID;

public class UUIDUtil {
	/**
	 * 获取UUID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}