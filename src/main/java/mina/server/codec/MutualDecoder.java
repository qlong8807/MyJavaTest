package mina.server.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MutualDecoder implements ProtocolDecoder {
	private static final Logger logger = LoggerFactory.getLogger(MutualDecoder.class);

	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
			throws Exception {
		System.out.println("decode");
	}

	@Override
	public void dispose(IoSession arg0) throws Exception {

	}

	@Override
	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1)
			throws Exception {

	}
	@SuppressWarnings("unused")
	private boolean sessionCheck(String unqieMark , IoSession session){
		return false;
	}
}
