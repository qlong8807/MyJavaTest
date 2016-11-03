package rmi.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

	public static void main(String[] args) {
		try {
			IHello hello = new HelloImpl();
			LocateRegistry.createRegistry(10102);//默认端口为1099
			Naming.rebind("rmi://127.0.0.1:10102/Hello", hello);
//			Naming.rebind("//127.0.0.1:10101/Hello", hello);
			System.out.println(">>>>>INFO:远程IHello对象绑定成功！"); 
		} catch (RemoteException e) {
			System.out.println("创建远程对象发生异常！");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("发生URL畸形异常！");
            e.printStackTrace(); 
		}

	}

}
