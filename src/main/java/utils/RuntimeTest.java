/**
 * 
 */
package utils;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author zyl
 * @date 2017年5月2日
 * 
 */
public class RuntimeTest {
	public static String executeCommand(String cmd) throws IOException {
		Process ps = Runtime.getRuntime().exec(cmd);
		Scanner scanner = new Scanner(ps.getInputStream());
		StringBuilder result = new StringBuilder();
		while (scanner.hasNextLine()) {
			result.append(scanner.nextLine());
			result.append(System.getProperty("line.separator"));
		}
		scanner.close();
		return result.toString();
	}

	// 列出服务器当前网络状态
	public static void main(String[] args) throws InterruptedException,
			IOException {
		System.out.println(executeCommand("netstat"));

	}
}
