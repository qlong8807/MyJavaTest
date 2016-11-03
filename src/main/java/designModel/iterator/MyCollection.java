package designModel.iterator;

public class MyCollection implements Collection {

	public String string[];

	public MyCollection(String[] str) {
		string = str;
	}

	@Override
	public Iterator iterator() {
		return new MyIterator(this);
	}

	@Override
	public Object get(int i) {
		return string[i];
	}

	@Override
	public int size() {
		return string.length;
	}
}
