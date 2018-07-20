package algorithm.repeat;

import java.util.HashSet;
import java.util.Random;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @author zyl
 * @date 2018年7月20日
 * @desc 
 *  简介：布隆过滤器实际上是一个很长的二进制向量和一系列随机映射函数。布隆过滤器可以用于检索一个元素是否在一个集合中。它的优点是空间效率和查询时间都远远超过一般的算法，缺点是有一定的误识别率和删除困难。
    原理：当一个元素被加入集合时，通过K个散列函数将这个元素映射成一个位数组中的K个点，把它们置为1。检索时，我们只要看看这些点是不是都是1就（大约）知道集合中有没有它了：如果这些点有任何一个0，则被检元素一定不在；如果都是1，则被检元素很可能在。
    优点：相比于其它的数据结构，布隆过滤器在空间和时间方面都有巨大的优势。布隆过滤器存储空间和插入/查询时间都是常数（O(k)）。而且它不存储元素本身，在某些对保密要求非常严格的场合有优势。
    缺点：一定的误识别率和删除困难。
    结合以上几点及去重需求（容忍误判，会误判在，在则丢，无妨），决定使用BlomFilter。
	参考：{@link a https://blog.csdn.net/qq_18495465/article/details/78500472}，{@link b https://blog.csdn.net/zdxiq000/article/details/57626464}
 */
public class BloomFilterTest {

    static int sizeOfNumberSet = Integer.MAX_VALUE >> 8;
    static Random generator = new Random();

    public static void main(String[] args) {
    		long start = System.currentTimeMillis();
        int error = 0;
        HashSet<Integer> hashSet = new HashSet<Integer>();
        BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), sizeOfNumberSet);
        //参数：funnel,数量，容错率
//        BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), sizeOfNumberSet,0.001);

        for(int i = 0; i < sizeOfNumberSet; i++) {
            int number = generator.nextInt();
            if(filter.mightContain(number) != hashSet.contains(number)) {
                error++;
            }
            filter.put(number);
            hashSet.add(number);
        }

        long end = System.currentTimeMillis();
        System.err.println("耗时:"+(end-start));
        System.out.println("Error count: " + error + ", error rate = " + String.format("%f", (float)error/(float)sizeOfNumberSet));
    }
}
