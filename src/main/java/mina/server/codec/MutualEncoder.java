package mina.server.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MutualEncoder implements ProtocolEncoder {
	private final static Logger log = LoggerFactory.getLogger(MutualEncoder.class);
	@Override
	public void dispose(IoSession arg0) throws Exception {

	}


	@Override
	public void encode(IoSession arg0, Object arg1, ProtocolEncoderOutput arg2)
			throws Exception {
		System.out.println("encode");
	}
}
