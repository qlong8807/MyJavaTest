/**
 * 
 */
package db.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author zyl
 * @date 2016年7月14日
 * 
 */
public class TestJedis {
	public static void main(String[] args) {
//		JedisPool pool = new JedisPool("192.168.125.128", 6380);
		JedisPool pool = new JedisPool("192.168.125.128", 6379);
//		JedisPool pool = new JedisPool("192.168.125.129", 6379);
//		JedisPool pool = new JedisPool("192.168.125.130", 6379);
		Jedis jedis = pool.getResource();
//		jedis.auth("pwdisadmin");
		String s1 = "test1";
		jedis.set(s1, "valur2");
		System.out.println(jedis.get(s1));
		jedis.del(s1);
		
		/**测试redis设置内存大小后，存储的数据超过内存大小，会怎样。 设置maxmemory和maxmemory-policy*********************/
		/*for(int i=0;i<100000;i++){
			jedis.set("key"+i, i+"这是redistribution的value，这是redistribution的value，这是redistribution的value，这是redistribution的value，这是redistribution的value，"+i);
		}
		for(int i=0;i<100;i++){
			System.out.println(jedis.get("key"+i*1000));
		}*/
		/**测试redis设置内存大小后，存储的数据超过内存大小，redis会通过删除策略删除部分数据，注意是删掉不是持久化。删除后查不到了。*********************/
		jedis.close();
		pool.close();
	}
}
