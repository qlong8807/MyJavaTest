package web.java;

import java.util.HashMap;
import java.util.Map;

public class HttpTest {

	public static void main(String[] args) throws Exception {

		/* Post Request */
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("username", "Nick Huang");
		dataMap.put("blog", "IT");
//		System.out.println(new HttpRequestor().doPost("http://localhost:8080/OneHttpServer/", dataMap));
		System.out.println(new HttpRequestor().doPost("http://chushou.tv/chushou/login.htm", dataMap));

		/* Get Request */
//		System.out.println(new HttpRequestor()
//				.doGet("http://localhost:8080/OneHttpServer/"));
	}
}
