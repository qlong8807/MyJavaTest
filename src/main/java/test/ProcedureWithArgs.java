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
   //加载Oracle驱动 
//   DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver()); 
   //获得Oracle数据库连接 
//   Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.20.7:1521:sxnet", "bankalarm", "secretnew"); 
	DriverManager.registerDriver(new com.mysql.jdbc.Driver()); 
	Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.20.7:3306/sxnet", "root", "root"); 

    //创建Oracle存储过程的对象，调用存储过程 61010 00000 20000 00004
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
		System.out.println("执行获取国标ID存储过程异常："+outResult);
	}
    //执行Oracle存储过程 
    conn.close(); 
} 
} 
