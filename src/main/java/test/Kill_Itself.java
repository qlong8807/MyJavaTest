/**
 * 
 */
package test;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author zyl
 * @date 2016年6月28日
 * 杀掉当前进程
 */
public class Kill_Itself {
	public static void main(String[] args) {
		String name = ManagementFactory.getRuntimeMXBean().getName();
		System.out.println(name);
		final String pid = name.split("@")[0];
		System.out.println("Pid is:" + pid);
		Properties props=System.getProperties(); //获得系统属性集    
        final String osName = props.getProperty("os.name"); //操作系统名称    
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					System.out.println("kill");
					if(null != osName && (osName.contains("Windows")||osName.contains("windows"))){
						Runtime.getRuntime().exec("tskill "+pid);
					}else{
						Runtime.getRuntime().exec("kill "+pid);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 5000);
		while (true) {

		}
	}
}
