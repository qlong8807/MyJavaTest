package db.redis.pool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

public class JedisPoolUtil {
	public static JedisPool pool = null;
	private static JedisPoolUtil jedisUtil = null;

	private JedisPoolUtil(){}
	public static JedisPoolUtil getInstance(){
		if(null == jedisUtil){
			jedisUtil = new JedisPoolUtil();
		}
		return jedisUtil;
	}
	/**
	 * 构建redis连接池
	 */
	static {
		if (null == pool) {
			JedisPoolConfig config = new JedisPoolConfig();
			// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
			// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
			config.setMaxTotal(5);
			// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
			config.setMaxIdle(2);
			// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
			config.setMaxWaitMillis(1000 * 1);
			// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
			config.setTestOnBorrow(true);
			/*//是否启用pool的jmx管理功能, 默认true
			config.setJmxEnabled(true);
			//MBean ObjectName = new ObjectName("org.apache.commons.pool2:type=GenericObjectPool,name=" + "pool" + i); 默 认为"pool", JMX不熟,具体不知道是干啥的...默认就好.
			config.setJmxNamePrefix("pool");
			//是否启用后进先出, 默认true
			config.setLifo(true);
			//最大空闲连接数, 默认8个
			config.setMaxIdle(8);
			//最大连接数, 默认8个
			config.setMaxTotal(8);
			//获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
			config.setMaxWaitMillis(-1);
			//逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
			config.setMinEvictableIdleTimeMillis(1800000);
			//最小空闲连接数, 默认0
			config.setMinIdle(0);
			//每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
			config.setNumTestsPerEvictionRun(3);
			//对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)   
			config.setSoftMinEvictableIdleTimeMillis(1800000);
			//在获取连接的时候检查有效性, 默认false
			config.setTestOnBorrow(false);
			//在空闲时检查有效性, 默认false
			config.setTestWhileIdle(false);
			//逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
			config.setTimeBetweenEvictionRunsMillis(-1);*/
			pool = new JedisPool(config, "10.10.10.167", 6379, 100, null, 12);
//			pool = new JedisPool(config, "192.168.64.184", 6379, 100, null, 12);
		}
	}

	/**
	 * 获取jedis
	 * @return
	 */
	public Jedis getJedis() {
		Jedis jedis = null;
        try {
//        	System.err.println(pool.getNumActive()+" idle:"+pool.getNumIdle());
        	System.out.println("---");
            jedis = pool.getResource();
        } catch (JedisException e) {
            System.out.println("failed:jedisPool getResource.");
            e.printStackTrace();
            if(jedis!=null){
            	pool.returnBrokenResource(jedis);
            }
            throw e;
        }
        return jedis;
	}
	public void release(Jedis jedis, boolean isBroken) {
		if (jedis != null) {
            if (isBroken) {
            	pool.returnBrokenResource(jedis);
            } else {
            	pool.returnResource(jedis);
            }
        }
	}

	/**
	 * 返还到连接池
	 * 
	 * @param pool
	 * @param redis
	 */
	public static void returnResource(Jedis redis) {
		if (redis != null) {
			pool.returnResource(redis);
		}
	}
	public static void returnBrokenResource(Jedis redis) {
		if (redis != null) {
			pool.returnBrokenResource(redis);
		}
	}
}