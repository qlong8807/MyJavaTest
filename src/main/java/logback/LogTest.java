/**
 * 
 */
package logback;

import logback.service.UService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author zyl
 * @date 2015年11月16日
 */
public class LogTest {
	private static Logger logger = LoggerFactory.getLogger(LogTest.class);
	public static void main(String[] args) {
		
		for(int i=0;i<1;i++){
			UService service = new UService();
			service.testPrint();
		}
	}
}
