package base.java8;

import java.util.Optional;

public class NullTest {
	public static void main(String[] args) {
		//解决Outer-Nested-Inner嵌套的null判断
		Optional.of(new Outer()).map(Outer::getNested).map(Nested::getInner).map(Inner::getFoo).ifPresent(System.out::println);
		
//		Optional.ofNullable(value)
	}
}

class Outer{
	Nested nested;
	Nested getNested() {
		return nested;
	}
}
class Nested{
	Inner inner;
	Inner getInner() {
		return inner;
	}
}
class Inner{
	String foo;
	String getFoo() {
		return foo;
	}
}