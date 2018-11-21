package utils;

import org.jctools.maps.NonBlockingHashMap;

/**
 * @author zyl
 * @date 2018年9月17日
 * @desc JCTools是一款对jdk并发数据结构进行增强的并发工具，主要提供了map以及queue的增强数据结构。
 * 原来netty还是自己写的MpscLinkedQueueNode，后来新版本就换成使用JCTools的并发队列了。
 * 增强map
    ConcurrentAutoTable(后面几个map/set结构的基础)
    NonBlockingHashMap
    NonBlockingHashMapLong
    NonBlockingHashSet
    NonBlockingIdentityHashMap
    NonBlockingSetInt
   增强队列
	SPSC/MPSC/SPMC/MPMC 数据变量的并发队列：
    SPSC：单个生产者对单个消费者（无等待、有界和无界都有实现）
    MPSC：多个生产者对单个消费者（无锁、有界和无界都有实现）
    SPMC：单生产者对多个消费者（无锁 有界）
    MPMC：多生产者对多个消费者（无锁、有界）

 */
public class JcToolsUtil {

	public static void main(String[] args) {
		NonBlockingHashMap nonBlockingHashMap = new NonBlockingHashMap();
	}
}
