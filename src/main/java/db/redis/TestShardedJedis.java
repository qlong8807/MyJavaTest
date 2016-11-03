/**
 * 
 */
package db.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @author zyl
 * @date 2016年7月14日
 * ShardedJedis 可以连几个独立的redis，把数据通过hash算法存入某一个redis中。
 * 连接的redis不需要进行任何特殊设置。
 * 这里客户端一致性hash是通过shards这个list的add顺序计算的。所以如果扩容就会导致取不到以前的数据。
 * 可以通过如下思路解决：
 * 前期布置两台机器，每台部署3个redis，后期扩容的时候只需要把6个redis中的某一个移到新机器就行。
 */
public class TestShardedJedis {
	public static void main(String[] args) {
		JedisPoolConfig config = new JedisPoolConfig();// Jedis池配置
		config.setMaxIdle(500);// 最大活动的对象个数
		config.setMaxIdle(1000 * 60);// 对象最大空闲时间
		config.setMaxWaitMillis(1000 * 10);// 获取对象时最大等待时间
		config.setTestOnBorrow(true);
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		JedisShardInfo shardInfo1 = new JedisShardInfo("192.168.125.128", 6379);
		shardInfo1.setPassword("pwdisadmin");
		shards.add(shardInfo1);
		shards.add(new JedisShardInfo("192.168.125.128", 6380));
		ShardedJedisPool pool = new ShardedJedisPool(config, shards);
		ShardedJedis jedis = pool.getResource();
		for(int i=0;i<100;i++){
			jedis.set("shard"+i, "shardvalue"+i);
		}
		pool.close();
	}
}
