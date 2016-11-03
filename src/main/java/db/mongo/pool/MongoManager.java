package db.mongo.pool;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;

public class MongoManager {
	private static Mongo mongo;
	private DB db;

	static {
		init();
	}

	/**
	 * 使用配置参数实例化
	 */
	public MongoManager() {
		this(MongoConfig.getDbName(), MongoConfig.getUserName(), MongoConfig
				.getPwd());
	}

	/**
	 * @param dbName
	 * @param userName
	 * @param pwd
	 *            实例化dbName一个DB
	 */
	public MongoManager(String dbName, String userName, String pwd) {
		System.out.println("连接库："+dbName+" 用户名："+userName+" 密码："+pwd);
		if (dbName == null || "".equals(dbName)) {
			throw new NumberFormatException("dbName is null");
		}
		db = mongo.getDB(dbName);
		db.authenticate(userName, pwd.toCharArray());
	}

	/**
	 * @param tableName
	 * @return
	 * @Date:2014-3-19
	 * @Author:lulei
	 * @Description: 获取表tableName的链接DBCollection
	 */
	public DBCollection getDBCollection(String tableName) {
		return db.getCollection(tableName);
	}

	/**
	 * @Date:2014-3-19
	 * @Author:lulei
	 * @Description: mongo连接池初始化
	 */
	private static void init() {
		System.out.println("host:"+MongoConfig.getHost()+" port:"+MongoConfig.getPort());
		if (MongoConfig.getHost() == null || "".equals(MongoConfig.getHost())) {
			throw new NumberFormatException("host is null");
		}
		if (MongoConfig.getPort() == 0) {
			throw new NumberFormatException("port is null");
		}
		try {
			// 服务列表
			List<ServerAddress> replicaSetSeeds = new ArrayList<ServerAddress>();
				replicaSetSeeds.add(new ServerAddress(MongoConfig.getHost(),
						MongoConfig.getPort()));
			// 连接池参数设置
			MongoOptions options = new MongoOptions();
			options.connectionsPerHost = MongoConfig.getConnectionsPerHost();
			options.threadsAllowedToBlockForConnectionMultiplier = MongoConfig
					.getThreadsAllowedToBlockForConnectionMultiplier();
			mongo = new Mongo(replicaSetSeeds, options);
			// 从服务器可读
			mongo.setReadPreference(ReadPreference.SECONDARY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
