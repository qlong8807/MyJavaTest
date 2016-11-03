package rmi.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

	public static void main(String[] args) {
		try {
			IHello hello = new HelloImpl();
			LocateRegistry.createRegistry(10102);//Ĭ�϶˿�Ϊ1099
			Naming.rebind("rmi://127.0.0.1:10102/Hello", hello);
//			Naming.rebind("//127.0.0.1:10101/Hello", hello);
			System.out.println(">>>>>INFO:Զ��IHello����󶨳ɹ���"); 
		} catch (RemoteException e) {
			System.out.println("����Զ�̶������쳣��");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("����URL�����쳣��");
            e.printStackTrace(); 
		}

	}

}
