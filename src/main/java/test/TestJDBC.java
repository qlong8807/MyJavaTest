package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class TestJDBC {
	public static void main(String args[]) throws Exception {

		// DbUtil dbUtil = new DbUtil();
		// dbUtil.getConnection();
		String outId = "";
		// 加载Oracle驱动
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		// 获得Oracle数据库连接
		Connection conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@192.168.20.7:1521:sxnet", "bankalarm",
				"secretnew");

		String sql = "select * from org_info";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.getResultSet();
		ResultSetMetaData m = null;// 获取 列信息

		try {
			m = rs.getMetaData();

			int columns = m.getColumnCount();
			// 显示列,表格的表头
			for (int i = 1; i <= columns; i++) {
				System.out.print(m.getColumnName(i));
				System.out.print("\t\t");
			}

			System.out.println();
			// 显示表格内容
			while (rs.next()) {
				for (int i = 1; i <= columns; i++) {
					System.out.print(rs.getString(i));
					System.out.print("\t\t");
				}
				System.out.println();
			}
			// 执行Oracle存储过程
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
