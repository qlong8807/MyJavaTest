package base.java8;

/**
 * @author zyl
 * @date 2018年8月16日
 * @desc ava8添加了对无符号数的额外支持。Java中的数值总是有符号的，例如，让我们来观察Integer:int可表示最多2**32个数。
 *       Java中的数值默认为有符号的，所以最后一个二进制数字表示符号(0为正数，1为负数)。所以从十进制的0开始，最大的有符号正整数为2**31-1。
 */
public class IntTest {
	public static void main(String[] args) {
		System.out.println(Integer.MAX_VALUE); // 2147483647
		System.out.println(Integer.MAX_VALUE + 1); // -2147483648

		long maxUnsignedInt = (1l << 32) - 1;
		String s1 = String.valueOf(maxUnsignedInt);
		System.out.println("s1:"+s1);
		int parseUnsignedInt = Integer.parseUnsignedInt(s1);
		System.out.println("parseInt:"+parseUnsignedInt);
		String unsignedString = Integer.toUnsignedString(parseUnsignedInt);
		System.out.println(unsignedString);

	}
}
