package webservice.cxf.bean;

public class HelloCXFServiceImpl implements HelloCXFService {

	@Override
	public String say(String hello) {
		System.out.println(hello);
		try {
			Thread.sleep(70000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "zhang "+hello;
	}

}
