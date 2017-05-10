package web.java;

import java.util.HashMap;
import java.util.Map;


public class HttpTest {

	public static void main(String[] args) throws Exception {

		/* Post Request */
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("username", "Nick Huang");
		dataMap.put("name", "Nick Huang");
		dataMap.put("blog", "IT");
		dataMap.put("age", "11");
//		System.out.println(new HttpRequestor().doPost("http://localhost:8080/OneHttpServer/", dataMap));
//		System.out.println(new HttpRequestor().doPost("http://chushou.tv/chushou/login.htm", dataMap));

		/* Get Request */
//		System.out.println(new HttpRequestor()
//				.doGet("http://localhost:8080/OneHttpServer/"));
		
//		System.out.println(new HttpRequestor().doGet("http://localhost:8989/src/main/java/com/cyber?names=whf&abc=123"));
//		System.out.println(new HttpRequestor().doPost("http://localhost:8989/src/hello/saay/", dataMap));
		HttpRequestor httpRequestor = new HttpRequestor();
//		httpRequestor.setCookies("_jc_save_fromStation=%u82CF%u5DDE%2CSZH; _jc_save_toStation=%u897F%u5B89%2CXAY; _jc_save_fromDate=2017-01-25; _jc_save_toDate=2017-01-25; _jc_save_wfdc_flag=dc; __NRF=C964C22FF33CAD1D70B921D637592A68; JSESSIONID=0A01D95F988020C16427C5395170B0DC91468AD43D; current_captcha_type=Z; BIGipServerotn=1608057098.38945.0000; _jc_save_fromStation=%u82CF%u5DDE%2CSZH; _jc_save_toStation=%u897F%u5B89%2CXAY; _jc_save_fromDate=2017-01-25; _jc_save_toDate=2017-01-16; _jc_save_wfdc_flag=dc");
//		System.out.println(httpRequestor.doGet("https://kyfw.12306.cn/otn/leftTicket/queryA?leftTicketDTO.train_date=2017-01-25&leftTicketDTO.from_station=SZH&leftTicketDTO.to_station=XAY&purpose_codes=ADULT"));
		
//		System.out.println(httpRequestor.doGet("http://101.200.72.251:8080/RailPass/identify/rpGetRandom.do?telPhone=12345123456"));
		//查询省市区
//		System.out.println(httpRequestor.doGet("http://101.200.72.251/RailPass/rpTicketExpress/rpLoadArea.do"));
		//查询首页活动
//		System.out.println(httpRequestor.doGet("http://101.200.72.251/RailPass/rpHomePage/rpQueryAllPartner.do"));
//		System.out.println(httpRequestor.doGet("http://101.200.72.251/RailPass/rporder/rpQueryDefaultExpressPrice.do"));
//		System.out.println(httpRequestor.doGet("http://101.200.72.251/RailPass/rpContact/rpLoadIdCardType.do"));
		System.out.println(httpRequestor.doGet("http://localhost:8080/"));
		
	}
}
