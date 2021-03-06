package sort;

import java.util.Random;

public abstract class Sort {
	private int count;

	protected int[] sort(int[] array) {
		return null;
	};

	/**
	 * 互换位置的方法
	 * 
	 * @param array
	 *            要换位置的目标数组
	 * @param i
	 *            数组位置1
	 * @param j
	 *            数组位置2
	 * @return 换好位置以后的数组
	 */
	protected int[] swap(int[] array, int i, int j) {
		int t = array[i];
		array[i] = array[j];
		array[j] = t;
		count++;
		return array;
	}

	protected void show(int[] array) {
		for (int a : array) {
			System.out.print(a+",");
		}
		System.out.println();
		System.out.println("数组长度：" + array.length + "，执行交换次数：" + count);
	}

	public int[] getIntArrayRandom(int len, int max) {
		int[] arr = new int[len];
		Random r = new Random();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = r.nextInt(max);
		}
		return arr;
	}

	/**
     * 取得数组的最大值
     * 
     * @param arr
     * @return
     */
    protected int max(int[] arr) {
        int max = 0;
        for (int a : arr) {
            if (a > max)
                max = a;
        }
        return max;
    }
}