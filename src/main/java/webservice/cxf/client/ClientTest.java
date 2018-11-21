package webservice.cxf.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.weconex.cat.cxfservice.ICatHsmManager;




public class ClientTest {

	public static void main(String[] args) {
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(HelloCXFService.class);
//		factory.setAddress("http://localhost:8899/HelloService");
//		HelloCXFService service = (HelloCXFService) factory.create();
//		//手动指定超时时间
//		CXFClientUtil.configTimeout(service);
//		System.out.println(System.currentTimeMillis()/1000);
//		System.out.println(service.say("test"));
//		System.out.println(System.currentTimeMillis()/1000);
		
		
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(ICatHsmManager.class);
		factory.setAddress("http://183.6.133.210:9688/CatHsmManager/services/CatHsmManagerService?wsdl");
		ICatHsmManager service = (ICatHsmManager) factory.create();
		//手动指定超时时间
		CXFClientUtil.configTimeout(service);
		System.out.println(System.currentTimeMillis()/1000);
		System.out.println(service.hsmTest());
		System.out.println(System.currentTimeMillis()/1000);
		
	}
}
