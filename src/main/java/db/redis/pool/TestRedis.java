package db.redis.pool;

import redis.clients.jedis.Jedis;

public class TestRedis {
	public static void main(String[] args) {
		Jedis jedis = JedisPoolUtil.getInstance().getJedis();
		String ping = jedis.ping();
		System.out.println(ping);
	}
}
