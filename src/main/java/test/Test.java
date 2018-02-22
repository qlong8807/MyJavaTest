package test;

import java.util.Arrays;

public class Test {
	public static void main(String[] args) throws Exception {
		String[] a = new String[0];
		
		for(int i=0;i<3;i++) {
			String [] b = {i+"a",i+"b",i+"c"};
			b = Arrays.copyOfRange(b, 1, b.length);
			a = byteMerger(a, b);
		}
		for(String s:a) {
			System.out.println(s);
		}
	}
	public static String[] byteMerger(String[] bt1, String[] bt2){  
		String[] bt = new String[bt1.length+bt2.length];  
        System.arraycopy(bt1, 0, bt, 0, bt1.length);  
        System.arraycopy(bt2, 0, bt, bt1.length, bt2.length);  
        return bt;  
    }
}
