package sort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zyl
 * @date 2017年11月2日
 * @desc 从代码中可以看出，桶排序是上面其他的比较排序的一个优化算法。但是桶的数量要根据原数组的取值范围去提前计算好，因为桶的数量越多，原数组的值越能平均分配到每个桶中去，相应的快速排序参与的部分就越少，时间复杂度就越低，但是空间复杂度就会越高，这是一个空间换时间的权衡。

桶的数量和每个桶的区间最好是能够隔离开原数组出现频率非常高的元素们，最大个数不要超过原数组最大元素的值，因为超过了将会有很多空桶，我们追求每个桶内元素尽量少的同时，又要追求整个桶集合中空桶数量尽量少。

所以使用桶排序要把握好桶个数和f(n)映射函数，将会大大提高效率。这很纠结，I know.<(▰˘◡˘▰)>

时间复杂度，最优情况就是每个桶都最多有一个元素，那么就完全不需要比较排序了，时间复杂度为O(n)。最坏情况就是只有一个桶拥有了所有原数组的元素，然后这个桶要完全使用比较排序去做，那么再赶上原数组的数值情况在那个比较排序算法里也是最坏情况，那时间复杂度可以达到O(n^2)

空间复杂度就不要说了，桶排序就是一个牺牲空间复杂度的算法。
 */
public class BucketSort extends Sort {
    private List<List<Integer>> buckets = new ArrayList<List<Integer>>();

    private int optimizeDivisor = 0;

    private int bucketNum = 100;// 这个桶个数要提前定义
    private int divisor = 1;

    private int f(int n) {
        return n / divisor;
    }

    @Override
    protected int[] sort(int[] array) {
        bucketSort(array);
        System.out.println("divisor=" + divisor + ", 桶排序优化程度：" + optimizeDivisor);
        return array;
    }

    private void bucketSort(int[] arr) {
        divisor = max(arr) / bucketNum + 1;
        for (int i = 0; i < bucketNum; i++) {
            buckets.add(new LinkedList<Integer>());
        }

        for (int a : arr) {
            buckets.get(f(a)).add(a);
        }
        int k = arr.length - 1;
        for (int i = 0; i < bucketNum; i++) {
            if (!buckets.get(i).isEmpty()) {
                optimizeDivisor++;
                List<Integer> list = buckets.get(i);
                int[] bucket = new int[list.size()];
                for (int j = 0; j < list.size(); j++) {
                    bucket[j] = list.get(j);
                }
                Sort quickSort = new QuickSort();
                bucket = quickSort.sort(bucket);// 如果是从小到大排序，那就正序插入，反之从大到小则倒序插入
                for (int j = bucket.length - 1; j >= 0; j--) {
                    arr[k--] = bucket[j];
                }
            }
        }
    }

}