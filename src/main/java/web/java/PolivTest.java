package web.java;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;


public class PolivTest {

	public static void main(String[] args) throws Exception {
		String appSecret = "c7e56e0757b4413494942383cada13e2";
		String appId = "ekti0pbte4";
		String userId = "e49e5b3aa0";
		long time = System.currentTimeMillis()/1000;
		String md5Hex = DigestUtils.md5Hex(appSecret+"appId"+appId+"timestamp"+time+"userId"+userId+appSecret).toUpperCase();
		//查询频道信息
		String getStreamResponse = new HttpRequestor().doGet("http://api.live.polyv.net/v1/channels/105268?userId="+userId+"&appId="+appId+"&timestamp="+time+"&sign="+md5Hex);
		JSONObject j = new JSONObject(getStreamResponse);
		String streamId = "";
		if("success".equals(j.get("status"))){
			Object result = j.get("result");
			j = new JSONObject(result.toString());
			streamId = j.get("stream").toString();
			System.out.println("streamID:"+j.get("stream"));
		}else {
			System.err.println("发生错误："+getStreamResponse);
		}
		//查询是否正在直播
		String iszhibo = new HttpRequestor().doGet("http://api.live.polyv.net/live_status/query?stream="+streamId);
		System.out.println(iszhibo);
	}
}
