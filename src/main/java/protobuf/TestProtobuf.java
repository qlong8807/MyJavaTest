package protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

public class TestProtobuf {
	public static void main(String[] args) {
		Person.msgInfo.Builder builder = Person.msgInfo.newBuilder();
	    builder.setID(1);
	    builder.setGoodID(222);
	    Person.msgInfo info = builder.build();
	    byte[] result=info.toByteArray();
	    System.out.println("==========="+result);
	    
	    try {
	    	Person.msgInfo msg = Person.msgInfo.parseFrom(result);
			System.out.println(msg.getID());
			System.out.println(msg.getGoodID());
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}

}
