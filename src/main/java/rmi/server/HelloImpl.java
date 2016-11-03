package rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl extends UnicastRemoteObject implements IHello {

	protected HelloImpl() throws RemoteException {
		super();
	}

	@Override
	public String sayName(String name) throws RemoteException{
		return "hello "+name;
	}

	@Override
	public boolean isConnect() {
		return true;
	}

}
