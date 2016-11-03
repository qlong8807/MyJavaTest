package md5;

import java.util.ArrayList;
import java.util.List;



public class TestMd5 {

	public static void main(String[] args) {
		List<Object> objects = new ArrayList<Object>();
		objects.add(1234);
		objects.add(false);
		System.out.println(new String(MD5.computeMd5("000000000000")));
		System.out.println(new String(MD5.computeMd5(objects.toString())));
	}
}
