package rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import rmi.server.IHello;

public class Client {

	public static void main(String[] args) {
		IHello rhello = null;
		try {
			// 在RMI服务注册表中查找名称为RHello的对象，并调用其上的方法
			rhello = (IHello) Naming
					.lookup("rmi://127.0.0.1:10102/Hello");
			// IHello rhello =(IHello) Naming.lookup("Hello");
			
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		while(true){
			try {
//				IHello rhello = (IHello) Naming
//						.lookup("rmi://127.0.0.1:10102/Hello");
//				System.out.println(rhello.sayName("熔岩"));
				System.out.println(rhello.isConnect());
			} catch (Exception e1) {
				System.err.println("---");
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
