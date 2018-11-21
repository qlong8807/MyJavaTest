package base.java8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FileTest {
	public static void main(String[] args) {
		//java1.7的try-with-resource语法，会自动关闭数据流。
		//打印项目根目录
		try (Stream<Path> stream = Files.list(Paths.get(""));) {
			String collect = stream.map(String::valueOf).filter(path -> !path.startsWith(".")).sorted()
					.collect(Collectors.joining(";"));
			System.out.println(collect);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//查找文件
		Path start = Paths.get("");
		int maxDepth = 5;
		try(Stream<Path> stream = Files.find(start, maxDepth, (path,attr) -> String.valueOf(path).endsWith(".xml"))){
			String collect = stream.sorted().map(String::valueOf).collect(Collectors.joining(";\n"));
			System.out.println(collect);
		}catch (Exception e) {
			e.printStackTrace();
		}
		IntStream.range(0, 100).forEach(i -> System.out.print("-"));
		System.err.println("---------------");
		//同上(查找文件)，也可以使用walk
		try(Stream<Path> stream = Files.walk(start, maxDepth)){
			String collect = stream.map(String::valueOf).filter(p -> p.endsWith(".xml")).sorted().collect(Collectors.joining(";\n"));
			System.out.println(collect);
		}catch (Exception e) {
			e.printStackTrace();
		}

		System.err.println("----------------------------------------------------------------------------------------------------");
		//读写文件
		try{
			List<String> readAllLines = Files.readAllLines(Paths.get("pom.xml"));
			System.out.println(readAllLines.size());
			readAllLines.add("a new line");
//			Files.write(Paths.get("pom2.xml"),readAllLines);
		}catch (Exception e) {
			e.printStackTrace();
		}
		//要注意这些方法对内存并不十分高效，因为整个文件都会读进内存。文件越大，所用的堆区也就越大。
		//你可以使用 Files.lines 方法来作为内存高效的替代。这个方法读取每一行，并 使用函数式数据流来对其流式处理，而不是一次性把所有行都读进内存。
		try(Stream<String> stream = Files.lines(Paths.get("pom.xml"))){
			stream.filter(line -> line.contains("common")).map(String::trim).forEach(l -> System.out.println(l));
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		//如果你需要更多的精细控制，你需要构造一个新的 BufferedReader 来代替
		try(BufferedReader reader = Files.newBufferedReader(Paths.get("pom.xml"))){
//			System.out.println(reader.readLine());
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//写入文件
		try(BufferedWriter writer = Files.newBufferedWriter(Paths.get("pom2.xml"))){
			writer.write("hello");
		}catch (Exception e) {
			e.printStackTrace();
		}
			
	}
}
