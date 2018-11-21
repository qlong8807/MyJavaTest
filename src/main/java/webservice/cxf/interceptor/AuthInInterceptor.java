package webservice.cxf.interceptor;

import java.util.List;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class AuthInInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

	public AuthInInterceptor() {
		super(Phase.PRE_INVOKE);
	}

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		// TODO Auto-generated method stub
		MessageContentsList contentsList = getContentsList(message);
		System.out.println(contentsList);
	}

	public MessageContentsList getContentsList(Message msg) {
		List<Object> o = CastUtils.cast(msg.getContent(List.class));
		if (o == null) {
			return null;
		}
		if (!(o instanceof MessageContentsList)) {
			MessageContentsList l2 = new MessageContentsList(o);
			msg.setContent(List.class, l2);
			return l2;
		}
		return (MessageContentsList) o;

	}

}
