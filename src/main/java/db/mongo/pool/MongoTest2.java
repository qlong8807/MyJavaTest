package db.mongo.pool;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class MongoTest2 {
	public static void main(String[] args) {
		// mongodb://<dbuser>:<dbpassword>@ds031942.mongolab.com:31942/zhcl
		MongoConfig.setHost("ds031942.mongolab.com");
		MongoConfig.setPort(31942);
		MongoConfig.setUserName("zhcl");
		MongoConfig.setPwd("zhcl");
		MongoConfig.setDbName("zhcl");
		MongoManager mongoManager = new MongoManager();
		DBCollection collection = mongoManager.getDBCollection("users");

		DBObject user = new BasicDBObject();
		user.put("name", "hoojo2");
		user.put("age", 241);
//		users.save(user);
		System.out.println("count: " + collection.count());
		int r = collection.insert(user, new BasicDBObject("aaa", "bbb")).getN();
		System.out.println(r);
//		DBObject findOne = collection.findOne();
	}
}
