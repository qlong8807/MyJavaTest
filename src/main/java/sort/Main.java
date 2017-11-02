package sort;

/**
 * @author zyl
 * @date 2017年11月2日
 * @desc 常用排序性能比较 <a>http://www.cnblogs.com/Evsward/p/sort.html</a>
 */
public class Main {
	public static void main(String[] args) {
		System.out.println("------冒泡------");
		long t1 = System.currentTimeMillis();
		Sort s = new BubbleSort();
		int[] array = s.getIntArrayRandom(1000, 100);
		array = s.sort(array);
		// s.show(array);
		long t2 = System.currentTimeMillis();
		System.err.println("冒泡耗时：" + (t2 - t1));
		System.out.println("------选择------");
		t1 = System.currentTimeMillis();
		s = new SelectSort();
		array = s.getIntArrayRandom(1000, 100);
		array = s.sort(array);
		// s.show(array);
		t2 = System.currentTimeMillis();
		System.err.println("选择耗时：" + (t2 - t1));
		System.out.println("------插入------");
		t1 = System.currentTimeMillis();
		s = new InsertSort();
		array = s.getIntArrayRandom(1000, 100);
		array = s.sort(array);
		// s.show(array);
		t2 = System.currentTimeMillis();
		System.err.println("插入耗时：" + (t2 - t1));
		System.out.println("------快速------");
		t1 = System.currentTimeMillis();
		s = new QuickSort();
		array = s.getIntArrayRandom(1000, 100);
		array = s.sort(array);
		// s.show(array);
		t2 = System.currentTimeMillis();
		System.err.println("快速耗时：" + (t2 - t1));
		System.out.println("------希尔------");
		t1 = System.currentTimeMillis();
		s = new ShellSort();
		array = s.getIntArrayRandom(1000, 100);
		array = s.sort(array);
		// s.show(array);
		t2 = System.currentTimeMillis();
		System.err.println("希尔耗时：" + (t2 - t1));
		System.out.println("------堆------");
		t1 = System.currentTimeMillis();
		s = new HeapSort();
		array = s.getIntArrayRandom(1000, 100);
		array = s.sort(array);
		// s.show(array);
		t2 = System.currentTimeMillis();
		System.err.println("堆耗时：" + (t2 - t1));
		System.out.println("------归并------");
		t1 = System.currentTimeMillis();
		s = new MergeSort();
		array = s.getIntArrayRandom(1000, 100);
		array = s.sort(array);
		// s.show(array);
		t2 = System.currentTimeMillis();
		System.err.println("归并耗时：" + (t2 - t1));
		System.out.println("------计数排序------");
		t1 = System.currentTimeMillis();
		s = new CountingSort();
		array = s.getIntArrayRandom(1000, 100);
		array = s.sort(array);
		// s.show(array);
		t2 = System.currentTimeMillis();
		System.err.println("计数排序耗时：" + (t2 - t1));
		System.out.println("------桶排序------");
		t1 = System.currentTimeMillis();
		s = new BubbleSort();
		array = s.getIntArrayRandom(1000, 100);
		array = s.sort(array);
		// s.show(array);
		t2 = System.currentTimeMillis();
		System.err.println("桶排序耗时：" + (t2 - t1));
	}
}

/**
 * @author zyl
 * @date 2017年11月2日
 * @desc 冒泡排序
 */
class BubbleSort extends Sort {
	public int[] sort(int[] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length - 1; j++) {
				if (array[j] < array[j + 1]) {
					array = swap(array, j, j + 1);
				}
			}
		}
		return array;
	}
}

/**
 * @author zyl
 * @date 2017年11月2日
 * @desc 选择排序
 */
class SelectSort extends Sort {
	public int[] sort(int[] array) {
		for (int i = 0; i < array.length - 1; i++) {// 控制交换的次数，最多交换n-1次。
			int maxIndex = i;
			for (int j = i + 1; j < array.length; j++) {
				if (array[j] > array[maxIndex]) {
					maxIndex = j;
				}
			}
			if (maxIndex != i) {// 找到当前位置后面最小值的位置，交换。
				swap(array, maxIndex, i);
			}
		}
		return array;
	}
}

class InsertSort extends Sort {
	public int[] sort(int[] array) {
		// 从第二张牌开始比较
		for (int i = 1; i < array.length; i++) {
			int target = array[i];
			int j = i;
			// 如果比前一个大，就把前一个放到当前目标牌的位置，把前一个的位置空出来，然后继续跟更前一个比较，循环去找到最准确的目标位置
			while (j > 0 && target > array[j - 1]) {
				array[j] = array[j - 1];
				j--;
			}
			// 在目标位置的插入操作
			array[j] = target;
		}
		return array;
	}
}

class QuickSort extends Sort {
	public int[] sort(int[] array) {
		return quickSort(array, 0, array.length - 1);
	}

	/**
	 * 分割的方法
	 * 
	 * @param array
	 * @param left
	 *            [left, right]
	 * @param right
	 * @return 两情相悦的位置
	 */
	private int partition(int[] array, int left, int right) {
		int pivot = array[left];// 定义基准数
		int pivotIndex = left;// 保存基准数的位置

		while (left < right) {// 直到中间相遇为止
			while (left < right && array[right] <= pivot)// 在右侧找到第一个比基准数大的
				right--;
			while (left < right && array[left] >= pivot)// 在左侧找到第一个比基准数小的
				left++;
			swap(array, left, right);// 互换上面找到的第一个比基准数大的和第一个比基准数小的位置
		}
		swap(array, pivotIndex, left);// 最后交换基准数到两情相悦的位置（不一定是中间）。
		return left;
	}

	/**
	 * 一到递归别迷糊：用于递归的方法quickSort，而非partition
	 * 
	 * @param array
	 * @param left
	 *            [left,right]
	 * @param right
	 * @return
	 */
	private int[] quickSort(int[] array, int left, int right) {
		if (left >= right)// 递归的终止条件，这是必要的。
			return array;
		int pivotIndex = partition(array, left, right);// 初次分割
		quickSort(array, left, pivotIndex - 1);// 快速排序基准数左边的数组
		quickSort(array, pivotIndex + 1, right);// 快速排序基准数右边的数组
		return array;
	}
}

class ShellSort extends Sort {

	@Override
	protected int[] sort(int[] array) {
		int lastStep = 0;// 控制循环次数，保存上一个step，避免重复
		for (int d = 2; d < array.length; d++) {
			int step = array.length / d;
			if (lastStep != step) {
				lastStep = step;
//				System.out.println("step: " + step);// 监控step，shellSort执行次数
				shellSort(array, step);
			} else {
				continue;
			}
		}
		return array;
	}

	private void shellSort(int[] array, int step) {
		for (int i = 0; i < array.length - step; i++) {
			if (array[i] < array[i + step]) {
				swap(array, i, i + step);
			}
		}
	}
}
class CountingSort extends Sort {

    @Override
    protected int[] sort(int[] array) {
        countingSort(array);
        return array;
    }

    /*
     * 计数排序
     * 
     * @example [1,0,2,0,3,1,1,2,8] 最大值是8，建立一个计数数组a[]统计原数组中每个元素出现的次数，长度为9(因为是从0到8)
     * 
     * @开始计数：第一个统计0的次数为2，则a[0]=2;第二个统计1的次数为3，则a[1]=3;第三个按照数组下标以此类推，最终获得一个统计数组。
     * 
     * @开始排序：因为按照统计数组的下标，已经是有顺序的，只要循环输出每个重复的数就可以了。
     */
    private void countingSort(int[] array) {
        int max = max(array);
        // 开始计数
        int[] count = new int[max + 1];
        for (int a : array) {
            count[a]++;
        }

        // 输出回原数组
        int k = 0;
        for (int i = 0; i < count.length; i++) {
            for (int j = 0; j < count[i]; j++) {
                array[k++] = i;
            }
        }
    }
}