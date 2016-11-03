/**
 * 
 */
package db.redis;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

/**
 * @author zyl
 * @date 2016年7月14日
 * redis哨兵简单使用
 */
public class TestJedisSentinelMaster {
	public static void main(String[] args) {
		Set<String> sentinels = new HashSet<String>();
        sentinels.add(new HostAndPort("192.168.125.128", 26379).toString());
        sentinels.add(new HostAndPort("192.168.125.129", 26379).toString());
        sentinels.add(new HostAndPort("192.168.125.130", 26379).toString());
        JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster", sentinels);
        System.out.println("Current master: " + sentinelPool.getCurrentHostMaster().toString());
        Jedis master = sentinelPool.getResource();
        master.auth("pwdisadmin");
        master.set("username","cczz");
        Jedis master2 = sentinelPool.getResource();
        master2.auth("pwdisadmin");
        String value = master2.get("username");
        System.out.println("username: " + value);
        master2.close();
        sentinelPool.close();
        sentinelPool.destroy();
	}
}
