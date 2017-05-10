/**
 * 
 */
package algorithm.maxNeighbor;

import java.util.Random;

import algorithm.CountingSort;

/**
 * @author zyl
 * @date 2016年12月12日
 * 有一个无序整型数组，如何求出这个数组排序后的任意两个相邻元素的最大差值？要求时间和空间复杂度尽可能低。（例如：无序数组 2,3,1,4,6，排序后是1,2,3,4,6，最大差值是6-4=2）
 */
public class SortAndMinus {
	public static void main(String[] args) {
		int[] randomIntArray = randomIntArray(0, 100000000, 100000);
		long t1 = System.currentTimeMillis();
		int[] countSort = CountingSort.countSort(randomIntArray);
//		print(countSort);
		long t2 = System.currentTimeMillis();
		sort1(randomIntArray);
		long t3 = System.currentTimeMillis();
		System.out.println(t2-t1 + "---"+ (t3-t2));
	}
	
	/**
	 * 直接比较排序
	 * @param a
	 * @return
	 */
	public static int[] sort1(int[] a) {
		for(int i=0;i<a.length;i++){
			for(int j=i+1;j<a.length;j++){
				if(a[i] > a[j]){
					int k = a[i];
					a[i] = a[j];
					a[j] = k;
				}
			}
		}
		return a;
	}
	/**
	 * 随机生成指定长度的int数组
	 * @param min
	 * @param max
	 * @param length
	 * @return
	 */
	public static int[] randomIntArray(int min,int max,int length){
		int l[] = new int[length];
		Random r = new Random();
		for(int i=0;i<length;i++){
			int v = r.nextInt(max-min)+min;
			l[i] = v;
		}
		return l;
	}
	public static void print(int[] b){
		for (int i : b) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
}
