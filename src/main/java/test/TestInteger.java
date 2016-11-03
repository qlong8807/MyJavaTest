package test;

public class TestInteger {
	public static void main(String[] args) {
		int a = 100;
		int b = a >>> 1;
		System.out.println(b);
		System.out.println(Integer.toBinaryString(a));
		System.out.println(Integer.toBinaryString(b));
	}
}
