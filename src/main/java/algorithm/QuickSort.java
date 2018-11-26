package algorithm;

import java.util.Random;

/**
 * @author zyl
 * @date 2018年11月26日
 * @desc 
 * 因为该算法是Divide-And-Conquer思想的一个实现，所以本文将以Divide-And-Conquer思想对其进行分析。首先，假设所要排序的数字存储在数组S中，则该算法的操作可以拆分为两部分：
    1. 在S中选出一个元素v；
    2. 将S数组分为三个子数组。其中v这个元素单独形成子数组1，比v小的元素形成子数组2，比v大的元素形成自数组3.
    3. 分别对子数组2和子数组3进行前两步操作，实现递归排序；
    4. 返回时，依次返回S1,V,S2；
    
    以下使用了两种方法，事实证明只用S1，S2比使用S1，V，S2快。
 */
public class QuickSort {
	
	/**
	 * 使用3个数组 S1,V,S2；
	 * @param arr
	 * @return 
	 */
	public static int[] quickSort(int[] arr) {
		if(null == arr || arr.length <= 1) {
			return arr;
		}
		int mid = arr[0];//中位数
		int arr1[] = new int[arr.length-1];
		int arr1Index = 0;
		int arr2[] = new int[arr.length-1];
		int arr2Index = 0;
		for(int i=1;i<arr.length;i++ ) {
			int value = arr[i];
			if(mid > value) {
				arr1[arr1Index++] = value;
			}else {
				arr2[arr2Index++] = value;
			}
		}
		int tem1[] = new int[arr1Index];
		System.arraycopy(arr1, 0, tem1, 0, arr1Index);
		int tem2[] = new int[arr2Index];
		System.arraycopy(arr2, 0, tem2, 0, arr2Index);
//		System.out.println("<"+toString(tem1)+"> "+ mid + " <"+toString(tem2)+">");
		int[] arrSort1 = quickSort(tem1);
		int[] arrSort2 = quickSort(tem2);
		int[] result = new int[arr.length];
		System.arraycopy(arrSort1, 0, result, 0, arr1Index);
		result[arr1Index] = mid;
		System.arraycopy(arrSort2, 0, result, arr1Index+1, arr2Index);
		return result;
	}
	/**
	 * 使用2个数组，把V放入S1中 S1,S2；
	 * @param arr
	 * @return 
	 */
	public static int[] quickSort2(int[] arr) {
		if(null == arr || arr.length <= 1) {
			return arr;
		}
		int mid = arr[0];//中位数
		int arr1[] = new int[arr.length-1];
		int arr1Index = 0;
		int arr2[] = new int[arr.length-1];
		int arr2Index = 0;
		for(int i=1;i<arr.length;i++ ) {
			int value = arr[i];
			if(mid > value) {
				arr1[arr1Index++] = value;
			}else {
				arr2[arr2Index++] = value;
			}
		}
		arr1[arr1Index++] = mid;
		int tem1[] = new int[arr1Index];
		System.arraycopy(arr1, 0, tem1, 0, arr1Index);
		int tem2[] = new int[arr2Index];
		System.arraycopy(arr2, 0, tem2, 0, arr2Index);
//		System.out.println("<"+toString(tem1)+"> " + " <"+toString(tem2)+">");
		int[] arrSort1 = quickSort(tem1);
		int[] arrSort2 = quickSort(tem2);
		int[] result = new int[arr.length];
		System.arraycopy(arrSort1, 0, result, 0, arr1Index);
		System.arraycopy(arrSort2, 0, result, arr1Index, arr2Index);
		return result;
	}
	private static String toString(int[] a) {
		StringBuilder sb = new StringBuilder();
		for(int i :a ) {
			sb.append(i).append(", ");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		int[] a = new int[100000];
		for(int i=0;i<100000;i++) {
			a[i] = new Random().nextInt(10000000);
		}
		long t1 = System.currentTimeMillis();
		int[] quickSort = quickSort(a);
//		int[] quickSort = quickSort2(a);
		long t2 = System.currentTimeMillis();
//		System.out.print("快速排序结果："+toString(quickSort));
		System.out.println("时间:"+(t2-t1));
	}
}
