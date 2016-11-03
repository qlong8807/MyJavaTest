package db.mongo.pool;

public class MongoConfig {
	private static String userName;// 用户名
	private static String pwd;// 密码
	private static String host;// 主机地址
	private static int port;// 端口地址
	private static String dbName;// 数据库名
	private static int connectionsPerHost = 2;// 每台主机最大连接数
	private static int threadsAllowedToBlockForConnectionMultiplier = 2;// 线程队列数
	private static boolean authentication = false;// 是否需要身份验证

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		MongoConfig.userName = userName;
	}

	public static String getPwd() {
		return pwd;
	}

	public static void setPwd(String pwd) {
		MongoConfig.pwd = pwd;
	}

	public static String getHost() {
		return host;
	}

	public static void setHost(String host) {
		MongoConfig.host = host;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		MongoConfig.port = port;
	}

	public static String getDbName() {
		return dbName;
	}

	public static void setDbName(String dbName) {
		MongoConfig.dbName = dbName;
	}

	public static int getConnectionsPerHost() {
		return connectionsPerHost;
	}

	public static void setConnectionsPerHost(int connectionsPerHost) {
		MongoConfig.connectionsPerHost = connectionsPerHost;
	}

	public static int getThreadsAllowedToBlockForConnectionMultiplier() {
		return threadsAllowedToBlockForConnectionMultiplier;
	}

	public static void setThreadsAllowedToBlockForConnectionMultiplier(
			int threadsAllowedToBlockForConnectionMultiplier) {
		MongoConfig.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
	}

	public static boolean isAuthentication() {
		return authentication;
	}

	public static void setAuthentication(boolean authentication) {
		MongoConfig.authentication = authentication;
	}
}
