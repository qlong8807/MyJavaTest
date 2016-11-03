package mina.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler extends IoHandlerAdapter {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ClientHandler.class);
	private final String values;

	public ClientHandler(String values) {
		System.out.println("ִ����clientHandler����");
		this.values = values;
	}

	@Override
	public void sessionOpened(IoSession session) {
		System.out.println("ִ����sessionOpened����");
		session.write(values);
	}
}