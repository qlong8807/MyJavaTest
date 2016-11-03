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
			// ��RMI����ע����в�������ΪRHello�Ķ��󣬲��������ϵķ���
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
//				System.out.println(rhello.sayName("����"));
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
