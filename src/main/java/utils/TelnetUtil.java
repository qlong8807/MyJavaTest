package utils;

import java.io.IOException;

import org.apache.commons.net.telnet.TelnetClient;

public class TelnetUtil {

	public static void main(String[] args) {
		boolean connected = TelnetUtil.isConnected("172.16.1.226", 21800);
		System.out.println(connected);
	}
	public static boolean isConnected(String ip,int port){
		boolean flag = false;
		TelnetClient telnetClient = new TelnetClient();
		try {
			telnetClient.connect(ip,port);
			flag = telnetClient.isConnected();
		} catch (Exception e) {
			flag = false;
		}finally{
			if(flag){
				try {
					telnetClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

}
