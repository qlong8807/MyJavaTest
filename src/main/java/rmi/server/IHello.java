package rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author admin
 * 定义一个远程接口，必须继承Remote接口，其中需要远程调用的方法必须抛出RemoteException异常 
 */
public interface IHello extends Remote{
	
	public String sayName(String name) throws RemoteException;
	
	public boolean isConnect() throws RemoteException;
}
