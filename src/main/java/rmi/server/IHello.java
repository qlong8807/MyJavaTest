package rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author admin
 * ����һ��Զ�̽ӿڣ�����̳�Remote�ӿڣ�������ҪԶ�̵��õķ��������׳�RemoteException�쳣 
 */
public interface IHello extends Remote{
	
	public String sayName(String name) throws RemoteException;
	
	public boolean isConnect() throws RemoteException;
}
