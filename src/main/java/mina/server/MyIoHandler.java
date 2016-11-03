package mina.server;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class MyIoHandler extends IoHandlerAdapter {
//	private final static Logger log = LoggerFactory
//			.getLogger(MyIoHandler.class);

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String str = message.toString();
//		log.info("The message received is [" + str + "]");
		System.out.println("The message received is [" + str + "]");
		if (str.endsWith("quit")) {
			session.close(true);
			return;
		}
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("created");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("opened");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("closed");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		System.out.println("idle");
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		System.out.println("exception");
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("sent");
	}

	@Override
	public void inputClosed(IoSession session) throws Exception {
		System.out.println("inputClosed");
	}
	
}