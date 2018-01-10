package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PingUtil {

	/**
	 * ping 10次，打印ping通概率
	 * @param ip "211.157.2.91";
	 */
	public static void ping(String ip) {
		ping(ip, 10);
	}
	/**
	 * 打印ping通概率
	 * @param ip "211.157.2.91";
	 * @param times ping的次数
	 */
	public static void ping(String ip,int times) {
		Runtime runtime = Runtime.getRuntime(); // 获取当前程序的运行进对象
		Process process = null; // 声明处理类对象
		String line = null; // 返回行信息
		InputStream is = null; // 输入流
		InputStreamReader isr = null;// 字节流
		BufferedReader br = null;
		try {
			process = runtime.exec("ping " + ip); // PING

			is = process.getInputStream(); // 实例化输入流
			isr = new InputStreamReader(is);// 把输入流转换成字节流
			br = new BufferedReader(isr);// 从字节中读取文本
			line = br.readLine();
			int i = 0;
			int ping_pass = 0;
			int ping_unpass = 0;
			while ((line = br.readLine()) != null && i<times) {
				i++;
				System.out.println(line);
				if (line.contains("timeout")) {
					ping_unpass++;
				}else {
					ping_pass++;
				}
			}
			is.close();
			isr.close();
			br.close();
			System.out.println("ping了10次，成功概率为："+(ping_pass/(ping_pass+ping_unpass))*100+"%");
		} catch (IOException e) {
			System.out.println(e);
			runtime.exit(1);
		}
	}
}
