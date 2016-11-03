/**
 * 
 */
package db.redis;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * @author zyl
 * @date 2016年9月21日
 * redis主从测试
 * 方法一：把RedisTemplate注入到RedisMSServiceImpl，然后使用。详见下面test方法
 * 方法二：在当前类中直接通过getBean使用。详见下面main方法
 */
public class TestSpringRedisMS {

	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext(
				"classpath:redis/spring-redis-sentinel.xml");
		RedisTemplate<String, String> template = (RedisTemplate<String, String>) context.getBean("redisTemplate");
		template.opsForValue().set("aaa", "aaabbb");
		System.err.println(template.opsForValue().get("aaa"));
	}
}