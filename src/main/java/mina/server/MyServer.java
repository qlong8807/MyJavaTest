package mina.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import mina.fillter.MyIoFilter;
import mina.server.codec.MutualCodecFactory;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.util.ReferenceCountingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MyServer {
	private static ProtocolCodecFactory codecFactory = new MutualCodecFactory();
	public static void main(String[] args) {
		IoAcceptor acceptor=new NioSocketAcceptor();
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,10);
		acceptor.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(
				new TextLineCodecFactory(
				Charset.forName("UTF-8"),
				LineDelimiter.WINDOWS.getValue(),
				LineDelimiter.WINDOWS.getValue()
				)));
		acceptor.getFilterChain().addLast("myIoFilter",
				new ReferenceCountingFilter(new MyIoFilter()));
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(codecFactory));
		acceptor.setHandler(new MyIoHandler());
		try {
			acceptor.bind(new InetSocketAddress(9123));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
}
