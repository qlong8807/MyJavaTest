package test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
public class ProcedureWithArgs  
  { 
public static void main(String args[]) throws Exception 
{ 
	String outId = "";
	String outResult = "";
   //����Oracle���� 
//   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver()); 
   //���Oracle���ݿ����� 
//   Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.20.7:1521:sxnet", "bankalarm", "secretnew"); 
	DriverManager.registerDriver(new com.mysql.jdbc.Driver()); 
	Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.20.7:3306/sxnet", "root", "root"); 

    //����Oracle�洢���̵Ķ��󣬵��ô洢���� 61010 00000 20000 00004
    CallableStatement call=conn.prepareCall("{call proc_sip_create_sipid(?,?,?,?,?,?)}"); 

    call.setString(1, "00");
	call.setString(2, "PLATFORM_DEV");
	call.setString(3, "200");
//	call.setString(2, "PRE_DEV");
//	call.setString(3, "111");
	call.setInt(4, 0);
	call.registerOutParameter(5, java.sql.Types.VARCHAR);
	call.registerOutParameter(6, java.sql.Types.VARCHAR);
	call.execute();
	outId = call.getString(5);
	outResult = call.getString(6);
	if ("OK".equalsIgnoreCase(outResult)) {
		System.out.println(outId);
	}else{
		System.out.println("ִ�л�ȡ����ID�洢�����쳣��"+outResult);
	}
    //ִ��Oracle�洢���� 
    conn.close(); 
} 
} 
