package webservice.cxf.bean;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface HelloCXFService {

	@WebMethod
	public String say(@WebParam(name="name")String name);
}
