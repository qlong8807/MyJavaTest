package algorithm.binarySearch;

import java.util.ArrayList;
import java.util.List;

/**
 * 二分查找
 *
 */
public class BinarySearch {
	private int rCount = 0;
	private int lCount = 0;

	/**
	 * 获取递归的次数
	 * 
	 * @return
	 */
	public int getrCount() {
		return rCount;
	}

	/**
	 * 获取循环的次数
	 * 
	 * @return
	 */
	public int getlCount() {
		return lCount;
	}

	/**
	 * 执行递归二分查找，返回第一次出现该值的位置
	 * 
	 * @param sortedData
	 *            已排序的数组
	 * @param start
	 *            开始位置
	 * @param end
	 *            结束位置
	 * @param findValue
	 *            需要找的值
	 * @return 值在数组中的位置，从0开始。找不到返回-1
	 */
	public int searchRecursive(int[] sortedData, int start, int end,
			int findValue) {
		rCount++;
		if (start <= end) {
			// 中间位置
			int middle = (start + end) >> 1; // 相当于(start+end)/2
			// 中值
			int middleValue = sortedData[middle];

			if (findValue == middleValue) {
				// 等于中值直接返回
				return middle;
			} else if (findValue < middleValue) {
				// 小于中值时在中值前面找
				return searchRecursive(sortedData, start, middle - 1, findValue);
			} else {
				// 大于中值在中值后面找
				return searchRecursive(sortedData, middle + 1, end, findValue);
			}
		} else {
			// 找不到
			return -1;
		}
	}

	/**
	 * 循环二分查找，返回第一次出现该值的位置
	 * 
	 * @param sortedData
	 *            已排序的数组
	 * @param findValue
	 *            需要找的值
	 * @return 值在数组中的位置，从0开始。找不到返回-1
	 */
	public int searchLoop(int[] sortedData, int findValue) {
		int start = 0;
		int end = sortedData.length - 1;

		while (start <= end) {
			lCount++;
			// 中间位置
			int middle = (start + end) >> 1; // 相当于(start+end)/2
			// 中值
			int middleValue = sortedData[middle];

			if (findValue == middleValue) {
				// 等于中值直接返回
				return middle;
			} else if (findValue < middleValue) {
				// 小于中值时在中值前面找
				end = middle - 1;
			} else {
				// 大于中值在中值后面找
				start = middle + 1;
			}
		}
		// 找不到
		return -1;
	}
	public List<Integer> printMiddleValue(int[] source,int start,int end,int deep){
		List<Integer> result = new ArrayList<Integer>(end);
		int middle = (start + end) >> 1;
		int midValue = source[middle];
		deep = deep-1;
		if(deep > 0){
			List<Integer> r1 = printMiddleValue(source, start, middle, deep);
			result.addAll(r1);
			List<Integer> r2 = printMiddleValue(source, middle,end, deep);
			result.addAll(r2);
		}
		result.add(midValue);
		return result;
	}
	public List<Integer> printMiddleValueByDeep(int[] source,int deep){
		List<Integer> result = new ArrayList<Integer>();
		int begin = 0;
		int size = source.length;
		int midValue = 0;
		if(deep <= 1){
			midValue = source[(begin+size)>>1];
			result.add(midValue);
		}else{
			int resultSize = 2<<(deep-2);
			if(resultSize > size/4){
				return result;
			}
			for(int i=1;i<=resultSize;i++){
				int index = size*(1+(2*(i-1)))/(2<<(deep-1));
				midValue = source[index];
				result.add(midValue);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		BinarySearch bs = new BinarySearch();
		int[] sortedData = new int[100];
		for(int i=0;i<100;i++){
			sortedData[i] = i+1;
		}
		int findValue = 9;
		int length = sortedData.length;
		int pos = bs.searchRecursive(sortedData, 0, length - 1, findValue);
		System.out.println("Recursice:" + findValue + " found in pos " + pos
				+ ";count:" + bs.getrCount());
		int pos2 = bs.searchLoop(sortedData, findValue);
		System.out.println("Loop:" + findValue + " found in pos " + pos2
				+ ";count:" + bs.getlCount());
		List<Integer> printMiddleValue = bs.printMiddleValue(sortedData,0,sortedData.length, 2);
//		List<Integer> printMiddleValue = bs.printMiddleValueByDeep(sortedData, 4);
		int cc = 0;
		for(Integer i : printMiddleValue){
			System.out.println(++cc +"  "+i);
		}
	}
}