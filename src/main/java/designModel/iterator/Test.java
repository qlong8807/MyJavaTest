package designModel.iterator;

public class Test {

	public static void main(String[] args) {
		
		String string[] = { "A", "B", "C", "D", "E" };
		
		Collection collection = new MyCollection(string);
		
		Iterator it = collection.iterator();

		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
}
