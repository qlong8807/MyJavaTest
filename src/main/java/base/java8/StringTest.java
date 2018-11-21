package base.java8;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zyl
 * @date 2018年8月16日
 * @desc java8新加入的方法 join 和 chars
 */
public class StringTest {
	public static void main(String[] args) {
		String join = String.join(":", "for","bar","hell");
		System.out.println(join);
		
		String collect = "forbar:foo:bar".chars().distinct().mapToObj(c -> String.valueOf((char)c)).sorted().collect(Collectors.joining());
		System.out.println(collect);
		
		String collect2 = Pattern.compile(":").splitAsStream("foobar:foo:bar").filter(s -> s.contains("bar")).sorted().collect(Collectors.joining("-"));
		System.out.println(collect2);
		
		Pattern compile = Pattern.compile(".*@hotmail\\.com");
		long count = Stream.of("abc@gmail.com","def@hotmail.com").filter(compile.asPredicate()).count();
		System.out.println(count);
	}
}
