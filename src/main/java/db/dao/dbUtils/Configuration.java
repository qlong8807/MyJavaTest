package db.dao.dbUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configuration {
	private static final Logger log = LoggerFactory.getLogger(Configuration.class);
	private static Map<String, Object> config = new ConcurrentHashMap<String, Object>();
	static {
		Properties serverConfig = new Properties();
		try {
			serverConfig.load(new FileInputStream(System.getProperty("user.dir")+"//bin//ServerConfig.properties"));
			for (Entry<Object, Object> e : serverConfig.entrySet()) {
				Configuration.addConfig(String.valueOf(e.getKey()), String.valueOf(e.getValue()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("Load ServerConfig Finished.");
		
		Properties dbConfig = new Properties();
		try {
			dbConfig.load(new FileInputStream(System.getProperty("user.dir")+"//bin//DBConfig.properties"));
			for (Entry<Object, Object> e : dbConfig.entrySet()) {
				Configuration.addConfig(String.valueOf(e.getKey()), String.valueOf(e.getValue()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("Load DBConfig Finished.");
		
		Properties redisConfig = new Properties();
		try {
			redisConfig.load(new FileInputStream(System.getProperty("user.dir")+"//bin//RedisConfig.properties"));
			for (Entry<Object, Object> e : redisConfig.entrySet()) {
				Configuration.addConfig(String.valueOf(e.getKey()), String.valueOf(e.getValue()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("Load RedisConfig Finished.");
		
		Properties wsConfig = new Properties();
		try {
			wsConfig.load(new FileInputStream(System.getProperty("user.dir")+"//bin//WSConfig.properties"));
			for (Entry<Object, Object> e : wsConfig.entrySet()) {
				Configuration.addConfig(String.valueOf(e.getKey()), String.valueOf(e.getValue()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("Load WebServiceConfig Finished.");
		
		Properties rmiConfig = new Properties();
		try {
			rmiConfig.load(new FileInputStream(System.getProperty("user.dir")+"//bin//DARmiServiceConfig.properties"));
			for (Entry<Object, Object> e : rmiConfig.entrySet()) {
				Configuration.addConfig(String.valueOf(e.getKey()), String.valueOf(e.getValue()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("Load RMIServiceConfig Finished.");
	}
	
	public static void addConfig(String key, String value) {
		config.put(key, value);
	}

	public static void addConfig(String key, Integer value) {
		config.put(key, value);
	}

	public static Object getConfig(String key) {
		return config.get(key);
	}

	public static String getString(String key) {
		Object value = getConfig(key);

		if (value != null && !"".equals(value.toString())) {
			return value.toString();
		} else {
			throw new RuntimeException("config key :["+key+"] is not find.");
		}
	}

	public static int getInt(String key) {
		Object value = config.get(key);
		if (value != null && !"".equals(value.toString())) {
			return Integer.parseInt(value.toString());
		} else {
			throw new RuntimeException("config key ["+key+"] is not find.");
		}
	}
	public static boolean getBoolean(String key){
		Object value = config.get(key);
		if (value != null && !"".equals(value.toString())) {
			return Boolean.valueOf(value.toString());
		} else {
			throw new RuntimeException("config key is not find.");
		}
	}
}
