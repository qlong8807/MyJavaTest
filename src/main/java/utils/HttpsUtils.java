package utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zyl
 * @date 2018年11月21日
 * @desc 支持http、https,支持request、response拦截器。但是使用拦截器会导致Entity不能toString两次，使用拦截器前需测试。
 */
public class HttpsUtils {
	
	private static Logger logger = LoggerFactory.getLogger(HttpsUtils.class);
	
	private static final String HTTP = "http";
	private static final String HTTPS = "https";
	private static SSLConnectionSocketFactory sslsf = null;
	private static PoolingHttpClientConnectionManager cm = null;
	private static SSLContextBuilder builder = null;
//	private HttpRequestInterceptor httpRequestInterceptor;
//	private HttpResponseInterceptor	httpResponseInterceptor;
	static {
		try {
			builder = new SSLContextBuilder();
			// 全部信任 不做身份鉴定
			builder.loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
					return true;
				}
			});
			sslsf = new SSLConnectionSocketFactory(builder.build(),
					new String[] { "SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2" }, null, NoopHostnameVerifier.INSTANCE);
			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register(HTTP, new PlainConnectionSocketFactory()).register(HTTPS, sslsf).build();
			cm = new PoolingHttpClientConnectionManager(registry);
			cm.setMaxTotal(200);// max connection
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public HttpsUtils() {
//	}
//	public HttpsUtils(HttpRequestInterceptor requestInterceptor,HttpResponseInterceptor responseInterceptor) {
//		httpRequestInterceptor = requestInterceptor;
//		httpResponseInterceptor = responseInterceptor;
//	}

	/**
	 * httpClient post请求
	 * 
	 * @param url
	 *            请求url
	 * @param header
	 *            头部信息
	 * @param param
	 *            请求参数 form提交适用
	 * @return 可能为空 需要处理
	 * @throws Exception
	 *
	 */
	public static String post(String url, Map<String, String> header, Map<String, String> param)
			throws Exception {
		logger.debug("post请求参数 url:{},header:{},body:{}",url,header,param);
		String result = "";
		CloseableHttpClient httpClient = null;
		try {
			httpClient = getHttpClient();
			HttpPost httpPost = new HttpPost(url);
			// 设置头信息
			if (MapUtils.isNotEmpty(header)) {
				for (Map.Entry<String, String> entry : header.entrySet()) {
					httpPost.addHeader(entry.getKey(), entry.getValue());
				}
			}
			// 设置请求参数
			if (MapUtils.isNotEmpty(param)) {
//				List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//				for (Map.Entry<String, String> entry : param.entrySet()) {
//					// 给参数赋值
//					formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//				}
//				UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
//				httpPost.setEntity(urlEncodedFormEntity);
				StringEntity entity = new StringEntity(JsonUtil.objectToJson(param),"UTF-8");
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json;charset=utf-8");
				httpPost.setEntity(entity);
			}
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(2000).build();//设置请求和传输超时时间
			httpPost.setConfig(requestConfig);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity resEntity = httpResponse.getEntity();
				result = EntityUtils.toString(resEntity);
			} else {
				result = readHttpResponse(httpResponse);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (httpClient != null) {
				httpClient.close();
			}
		}
		return result;
	}

	private static CloseableHttpClient getHttpClient() throws Exception {
		HttpClientBuilder httpClientBuilder = HttpClients.custom().setSSLSocketFactory(sslsf).setConnectionManager(cm)
				.setConnectionManagerShared(true);
//		if(null != httpRequestInterceptor) httpClientBuilder.addInterceptorLast(httpRequestInterceptor);
//		if(null != httpResponseInterceptor) httpClientBuilder.addInterceptorLast(httpResponseInterceptor);
		CloseableHttpClient httpClient = httpClientBuilder.build();
		return httpClient;
	}

	/**
	 * 返回Entity的字符串，name=zzz&pwd=cc
	 * @param httpResponse
	 * @return
	 * @throws ParseException
	 * @throws IOException 
	 */
	private static String readHttpResponse(HttpResponse httpResponse) throws ParseException, IOException {
		StringBuilder builder = new StringBuilder();
		String entityString = "";
		// 获取响应消息实体
		HttpEntity entity = httpResponse.getEntity();
		// 响应状态
		builder.append("status:" + httpResponse.getStatusLine());
		builder.append("headers:");
		HeaderIterator iterator = httpResponse.headerIterator();
		while (iterator.hasNext()) {
			builder.append("\t" + iterator.next());
		}
		// 判断响应实体是否为空
		if (entity != null) {
			String responseString = EntityUtils.toString(entity);
			builder.append("response length:" + responseString.length());
			builder.append("response content:" + responseString.replace("\r\n", ""));
			entityString = responseString;
		}
		System.err.println("收到的异常响应为："+builder.toString());
		return entityString;
	}
	public static void main(String[] args) {
		
		List<NameValuePair> parameters = new ArrayList<>();
		BasicNameValuePair p1 = new BasicNameValuePair("name", "zzz");
		parameters.add(p1);
		BasicNameValuePair p2 = new BasicNameValuePair("pwd", "cc");
		parameters.add(p2);
		try {
			HttpEntity entity = new UrlEncodedFormEntity(parameters);
			System.out.println(EntityUtils.toString(entity));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}