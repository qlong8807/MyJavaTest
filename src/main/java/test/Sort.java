package test;

public class Sort {
	public static void main(String[] args) {
		int[] numbers = { 3, 2, 5, 1, 5, 6, 7 };
		// bubbleSort(numbers);
		// quickSort(numbers, 0, numbers.length - 1);
		// selectSort(numbers);
		// insertSort(numbers);
//		shellSort(numbers);
		shellSort2(numbers);
		for (int i : numbers) {
			System.out.print(i + "  ");
		}
		System.out.println();
	}

	/**
	 * 冒泡法排序<br/>
	 * 
	 * <li>比较相邻的元素。如果第一个比第二个大，就交换他们两个。</li> <li>
	 * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。</li> <li>
	 * 针对所有的元素重复以上的步骤，除了最后一个。</li> <li>持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。</li>
	 * 
	 * @param numbers
	 *            需要排序的整型数组
	 */
	public static void bubbleSort(int[] numbers) {
		int temp; // 记录临时中间值
		int size = numbers.length; // 数组大小
		for (int i = 0; i < size - 1; i++) {
			for (int j = i + 1; j < size; j++) {
				if (numbers[i] < numbers[j]) { // 交换两数的位置
					temp = numbers[i];
					numbers[i] = numbers[j];
					numbers[j] = temp;
				}
			}
		}
	}

	/**
	 * 快速排序<br/>
	 * <ul>
	 * <li>从数列中挑出一个元素，称为“基准”</li>
	 * <li>重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。在这个分割之后，
	 * 该基准是它的最后位置。这个称为分割（partition）操作。</li>
	 * <li>递归地把小于基准值元素的子数列和大于基准值元素的子数列排序。</li>
	 * </ul>
	 * 
	 * @param numbers
	 * @param start
	 * @param end
	 */
	public static void quickSort(int[] numbers, int start, int end) {
		if (start < end) {
			int base = numbers[start]; // 选定的基准值（第一个数值作为基准值）
			int temp; // 记录临时中间值
			int i = start, j = end;
			do {
				while ((numbers[i] < base) && (i < end))
					i++;
				while ((numbers[j] > base) && (j > start))
					j--;
				if (i <= j) {
					temp = numbers[i];
					numbers[i] = numbers[j];
					numbers[j] = temp;
					i++;
					j--;
				}
			} while (i <= j);
			if (start < j)
				quickSort(numbers, start, j);
			if (end > i)
				quickSort(numbers, i, end);
		}
	}

	/**
	 * 选择排序<br/>
	 * <li>在未排序序列中找到最小元素，存放到排序序列的起始位置</li> <li>再从剩余未排序元素中继续寻找最小元素，然后放到排序序列末尾。</li>
	 * <li>以此类推，直到所有元素均排序完毕。</li>
	 * 
	 * 
	 * @param numbers
	 */
	public static void selectSort(int[] numbers) {
		int size = numbers.length, temp;
		for (int i = 0; i < size; i++) {
			int k = i;
			for (int j = size - 1; j > i; j--) {
				if (numbers[j] < numbers[k])
					k = j;
			}
			temp = numbers[i];
			numbers[i] = numbers[k];
			numbers[k] = temp;
		}
	}

	/**
	 * 插入排序<br/>
	 * <ul>
	 * <li>从第一个元素开始，该元素可以认为已经被排序</li>
	 * <li>取出下一个元素，在已经排序的元素序列中从后向前扫描</li>
	 * <li>如果该元素（已排序）大于新元素，将该元素移到下一位置</li>
	 * <li>重复步骤3，直到找到已排序的元素小于或者等于新元素的位置</li>
	 * <li>将新元素插入到该位置中</li>
	 * <li>重复步骤2</li>
	 * </ul>
	 * 直接插入就是找到temp的合适位置。
	 * 
	 * @param numbers
	 */
	public static void insertSort(int[] numbers) {
		int size = numbers.length, temp, j;
		for (int i = 1; i < size; i++) {
			temp = numbers[i];
			for (j = i; j > 0; j--) {
				if (temp < numbers[j - 1]) {
					numbers[j] = numbers[j - 1];
				} else {
					break;
				}
			}
			// for (j = i; j > 0 && temp < numbers[j - 1]; j--){
			// System.out.println("========"+j);
			// numbers[j] = numbers[j - 1];
			// }
			numbers[j] = temp;
		}
	}

	/**
	 *	基本思想：算法先将要排序的一组数按某个增量d（n/2,n为要排序数的 个数）分成若干组，每组中记录的下标相差d.对每组中全部元素进行直接插入排序，
		然后再用一个较小的增量（d/2）对它进行分组，在每组中再进行直接插入 排序。当增量减到1时，进行直接插入排序后，排序完成。
	 * @param arr
	 */
	private static void shellSort(int[] arr) {
		int j;
		int len = arr.length;
		for (int val = len >> 1; val > 0; val >>= 1) {
			// 下面是对本次的所有分组做直接插入排序
			for (int i = val; i < len; i++) {
				int temp = arr[i];
				for (j = i; j >= val && temp < arr[j - val]; j -= val) {
					arr[j] = arr[j - val];
				}
				arr[j] = temp;
			}
		}
	}

	public static void shellSort2(int[] numbers) {
		int d = numbers.length;
		int temp = 0;
		while (true) {
			d = (int) Math.ceil(d / 2);
			for (int x = 0; x < d; x++) {
				for (int i = x + d; i < numbers.length; i += d) {
					int j = i - d;
					temp = numbers[i];
					for (; j >= 0 && temp < numbers[j]; j -= d) {
						numbers[j + d] = numbers[j];
					}
					numbers[j + d] = temp;
				}
			}
			if (d == 1)
				break;
		}
	}
}
