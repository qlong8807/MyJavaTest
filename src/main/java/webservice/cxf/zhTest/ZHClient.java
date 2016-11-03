package webservice.cxf.zhTest;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class ZHClient {

	public static void main(String[] args) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		/*factory.setServiceClass(DataAnalysisWebService.class);
		factory.setAddress("http://10.10.10.173:22110/DAnalysisWS?wsdl");
		DataAnalysisWebService service = (DataAnalysisWebService) factory.create();
		List<Integer> list = new ArrayList<Integer>();
		list.add(310000);
		byte[] b = service.getVehiclePassInArea(list, 1, 1446307200, 1446393599, 111);
//			byte[] b = service.getACCStatusAlarmRecords(new long[]{1111}, 1446307200, 1446393599, new CommonParameter());
		System.out.println(b);*/
		
	}
}
