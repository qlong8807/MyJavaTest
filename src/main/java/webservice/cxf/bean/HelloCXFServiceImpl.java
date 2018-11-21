package webservice.cxf.bean;

import javax.jws.WebService;

@WebService
public class HelloCXFServiceImpl implements HelloCXFService {

	@Override
	public String say(String name) {
		System.out.println(name);
//		try {
//			Thread.sleep(70000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		return "hello  "+name;
	}

}
