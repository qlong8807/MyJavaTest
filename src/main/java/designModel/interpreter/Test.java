package designModel.interpreter;


/**
 * 一般主要应用在OOP开发中的编译器的开发中，所以适用面比较窄。
 * 解释器模式用来做各种各样的解释器，如正则表达式等的解释器等等！
 */
public class Test {

	public static void main(String[] args) {

		// 计算9+2-8的值
		Context c1 = new Context(9, 2);
		Plus plus = new Plus();
		int p1 = plus.interpret(c1);
		Context c2 = new Context(p1, 8);
		Minus minus = new Minus();
		int m1 = minus.interpret(c2);
		System.out.println(m1);
	}
}
