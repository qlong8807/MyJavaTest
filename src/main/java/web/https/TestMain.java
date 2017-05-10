/**
 * 
 */
package web.https;

import java.util.HashMap;
import java.util.Map;

//对接口进行测试  
public class TestMain {
	private String url = "https://192.168.1.101/";
	private String charset = "utf-8";
	private HttpClientUtil httpClientUtil = null;

	public TestMain() {
		httpClientUtil = new HttpClientUtil();
	}

	public void testPost() {
		String httpOrgCreateTest = url + "httpOrg/create";
		Map<String, String> createMap = new HashMap<String, String>();
		createMap.put("authuser", "*****");
		createMap.put("authpass", "*****");
		createMap.put("orgkey", "****");
		createMap.put("orgname", "****");
		String httpOrgCreateTestRtn = httpClientUtil.doPost(httpOrgCreateTest,
				createMap, charset);
		System.out.println("result:" + httpOrgCreateTestRtn);
	}
	public void testGet(){
		String url = "https://kyfw.12306.cn/otn/leftTicket/queryA?leftTicketDTO.train_date=2017-01-25&leftTicketDTO.from_station=SZH&leftTicketDTO.to_station=XAY&purpose_codes=ADULT";
		String cookies = "_jc_save_fromStation=%u82CF%u5DDE%2CSZH; _jc_save_toStation=%u897F%u5B89%2CXAY; _jc_save_fromDate=2017-01-25; _jc_save_toDate=2017-01-25; _jc_save_wfdc_flag=dc; __NRF=C964C22FF33CAD1D70B921D637592A68; JSESSIONID=0A01D95F988020C16427C5395170B0DC91468AD43D; current_captcha_type=Z; BIGipServerotn=1608057098.38945.0000; _jc_save_fromStation=%u82CF%u5DDE%2CSZH; _jc_save_toStation=%u897F%u5B89%2CXAY; _jc_save_fromDate=2017-01-25; _jc_save_toDate=2017-01-16; _jc_save_wfdc_flag=dc";
		String doGet = httpClientUtil.doGet(url, cookies);
		System.out.println(doGet);
	}

	public static void main(String[] args) {
		TestMain main = new TestMain();
//		main.testPost();
		main.testGet();
	}
}