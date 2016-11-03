package test;

public class TestEnum {
	public enum Colors {
		red, blue, gree
	}

	public static void main(String[] args) {
		System.out.println(Colors.blue.name());

		System.out.println("遍历枚举：第一种通过反射");
		Class clz = Colors.class;
		for (Object obj : clz.getEnumConstants()) {
			System.out.println(obj);
		}
		System.out.println("遍历枚举：第二种通过枚举静态方法values()");
		for (Colors rate : Colors.values()) {
			System.out.println(rate);
		}
		
		System.out.println(getColors().equals(Colors.blue));
	}
	
	public static Colors getColors(){
		return Colors.blue;
	}
}
