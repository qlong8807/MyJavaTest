package webservice.cxf.server;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

import webservice.cxf.bean.HelloCXFService;
import webservice.cxf.bean.HelloCXFServiceImpl;


public class Server {

	public static void main(String[] args) {
		JaxWsServerFactoryBean config_DataAnalysisfactory = new JaxWsServerFactoryBean();
		HelloCXFService analysisWS = new HelloCXFServiceImpl();
		config_DataAnalysisfactory.setServiceClass(HelloCXFService.class);
		config_DataAnalysisfactory.setAddress("http://localhost:8899/HelloService");
		config_DataAnalysisfactory.setServiceBean(analysisWS);
		config_DataAnalysisfactory.create();
	}

}
