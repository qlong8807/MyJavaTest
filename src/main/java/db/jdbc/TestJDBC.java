package db.jdbc;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestJDBC {

	@Test
	public void AddUser() {
		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection connection = DriverManager.getConnection(
//					"jdbc:mysql://172.16.1.230:3306/test", "root", "root");
			 Connection connection = ConnectionPool.getConnection();
			if (null == connection) {
				System.out.println("获取不到数据库连接");
				System.exit(0);
			}
			String sql = "insert into t_user values(?,?,?,?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, 3);
			ps.setString(2, "zhang");
			ps.setString(3, "zhang");
			ps.setInt(4, 22);
			boolean flag = ps.execute();
			ConnectionPool.release();
			System.out.println(flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过反射查找单个对象
	 * @param sql
	 * @param params
	 * @param clz
	 * @return
	 * @throws Exception
	 */
	public <T> T findSimpleReflectResule(String sql,List<Object> params,Class<T> clz) throws Exception{
		T resultObject = null;
		Connection conn = ConnectionPool.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		pst = conn.prepareStatement(sql);
		int index = 1;
		if(null != params && params.size() > 0){
			for (int i = 0; i < params.size(); i++) {
				pst.setObject(index++, params.get(i));
			}
		}
		rs = pst.executeQuery();
		ResultSetMetaData metaData = rs.getMetaData();
		int result_len = metaData.getColumnCount();
		while(rs.next()){
			resultObject = clz.newInstance();
			for(int i=0;i<result_len;i++){
				String col_name = metaData.getColumnName(i+1);
				Object col_value = rs.getObject(col_name);
				if(null == col_value){
					col_value = "";
				}
				Field field = clz.getDeclaredField(col_name);
				field.setAccessible(true);//打开javabean的访问private的权限
				field.set(resultObject, col_value);
				
			}
		}
		ConnectionPool.release();
		return resultObject;
	}
	@Test
	public void testReflect(){
		List<Object> params = new ArrayList<Object>();
		params.add(1);
		try {
			TUser user = findSimpleReflectResule("select * from t_user where id = ?", params, TUser.class);
			System.out.println(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
