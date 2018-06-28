package enum_test;

public class EnumTest {

	public static void main(String[] args) {
		for(First f : First.values()) {
			System.out.println("first:"+f+" name:"+f.name()+" ordinal:"+f.ordinal()+" toString:"+f.toString());
		}
		
		for(Second s : Second.values()) {
			System.out.println("second:"+s+" name:"+s.name()+" ordinal:"+s.ordinal()+ " value:"+s.getTypeName()+" toString:"+s.toString());
		}
		System.out.println(Second.fromValue("wi"));
		System.out.println(Second.valueOf("Spring"));
	}
}
