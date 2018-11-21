package webservice.cxf.server;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

import webservice.cxf.bean.HelloCXFService;
import webservice.cxf.bean.HelloCXFServiceImpl;
import webservice.cxf.interceptor.AuthInInterceptor;


public class Server {

	public static void main(String[] args) {
		JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
		HelloCXFService analysisWS = new HelloCXFServiceImpl();
		factory.setServiceClass(HelloCXFService.class);
		factory.setAddress("http://localhost:8899/HelloService?wsdl");
		factory.setServiceBean(analysisWS);
		factory.getInInterceptors().add(new AuthInInterceptor());
		factory.create();
	}

}
