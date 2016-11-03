package webservice.cxf.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import webservice.cxf.bean.HelloCXFService;




public class ClientTest {

	public static void main(String[] args) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(HelloCXFService.class);
		factory.setAddress("http://localhost:8899/HelloService");
		HelloCXFService service = (HelloCXFService) factory.create();
		//手动指定超时时间
		CXFClientUtil.configTimeout(service);
		System.out.println(System.currentTimeMillis()/1000);
		System.out.println(service.say("test"));
		System.out.println(System.currentTimeMillis()/1000);
		
	}
}
