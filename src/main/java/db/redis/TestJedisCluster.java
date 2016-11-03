/**
 * 
 */
package db.redis;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * @author zyl
 * @date 2016年7月14日
 * 集群一般至少使用6个节点。3主3从
 */
public class TestJedisCluster {
	public static void main(String[] args) {

		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.125.128", 6381));
		nodes.add(new HostAndPort("192.168.125.128", 6382));
		nodes.add(new HostAndPort("192.168.125.128", 6383));
		nodes.add(new HostAndPort("192.168.125.128", 6384));
		nodes.add(new HostAndPort("192.168.125.128", 6385));
		nodes.add(new HostAndPort("192.168.125.128", 6386));
		JedisCluster jc = new JedisCluster(nodes);
		int i = 0;
		while (true) {
			try {
				i++;
				long start = System.currentTimeMillis();
				jc.set("k:" + i, "v" + i);
//				System.out.print("set " + i + "th value in "
//						+ (System.currentTimeMillis() - start) + " ms");
				start = System.currentTimeMillis();
				jc.get("k:" + i);
//				System.out.println(", get " + i + "th value in "
//						+ (System.currentTimeMillis() - start) + " ms");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/*String key = "test_oper_during_failover";
		jc.del(key);
		long failureTime = 0;
		long recoveryTime = 0;
		while (true) {
			try {
				String result = jc.get(key);
				if (failureTime != 0 && recoveryTime == 0) {
					recoveryTime = System.currentTimeMillis();
					System.out.println("Cluster is recovered! Downtime lasted "
							+ (recoveryTime - failureTime) + " ms");
				}
				// System.out.println(result);
				jc.set(key, System.currentTimeMillis() + "");
			} catch (Exception e) {
				if (failureTime == 0)
					failureTime = System.currentTimeMillis();
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
	}
}
