package mina.fillter;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import mina.bean.User;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class TestEncoder extends ProtocolEncoderAdapter{
	
	private final Charset charset;
	
	public TestEncoder(Charset charset){
		this.charset = charset;
	}

	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		User u = (User)message;
		CharsetEncoder ce = charset.newEncoder();
		IoBuffer ioBuffer = IoBuffer.allocate(100).setAutoExpand(true).setAutoShrink(true);
		
		ioBuffer.putString("abc", ce);
		ioBuffer.flip();
		out.write(ioBuffer);
		
	}

}
