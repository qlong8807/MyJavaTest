package db.dao.dbUtils.common;

public class ServiceMarkFactory {
	/* 计数器，�?�?��，当达到�?��值后，重新置1 */
	public static int counter = 1;

	/**
	 * 获取服务标识(当前时间毫秒数低位截�?2位与计数器求�?
	 * 
	 * @return String
	 */
	public static long getServiceMark() {
		long id = System.currentTimeMillis() / 10 + counter;

		if (counter == Integer.MAX_VALUE) {
			counter = 1;
		} else {
			counter++;
		}
		return id;
	}
}
