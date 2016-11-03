package jmx;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import com.sun.jdmk.comm.HtmlAdaptorServer;

/**
 * @author admin ˵����
 * 
 *         �ȴ�����һ��MBeanServer��������MBean������
 *         ��Hello�����ע�뵽MBeanServer�У�ע����Ҫ����һ��ObjectName��
 *         ����һ��AdaptorServer������ཫ����MBean�Ĺ������
 *         ������������ͨ��Html�ͽ��档AdaptorServer��ʵҲ��һ��MBean��
 *         chengang:name=HelloWorld����������һ�������
 *         ����ʽΪ��������:name=MBean���ơ���������MBean���ƶ���������ȡ��
 * 
 *         4������HelloAgent��Ȼ�����ҳ��http://localhost:8082/ ��
 *         ������name=HelloWorld�����ӽ��롣
 */
public class HelloAgent {

	public static void main(String[] args) {
		MBeanServer server = MBeanServerFactory.createMBeanServer();
		try {
			ObjectName helloName = new ObjectName(
					"myDomain:name=HelloWorld222222");
			server.registerMBean(new Hello(), helloName);
			ObjectName adapterName = new ObjectName(
					"HelloAgent:name=htmladapter,port=8082");
			HtmlAdaptorServer adapter = new HtmlAdaptorServer();
			server.registerMBean(adapter, adapterName);
			adapter.start();
			System.out.println("start.....");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}