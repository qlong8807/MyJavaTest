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
		// ����Oracle����
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		// ���Oracle���ݿ�����
		Connection conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@192.168.20.7:1521:sxnet", "bankalarm",
				"secretnew");

		String sql = "select * from org_info";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.getResultSet();
		ResultSetMetaData m = null;// ��ȡ ����Ϣ

		try {
			m = rs.getMetaData();

			int columns = m.getColumnCount();
			// ��ʾ��,���ı�ͷ
			for (int i = 1; i <= columns; i++) {
				System.out.print(m.getColumnName(i));
				System.out.print("\t\t");
			}

			System.out.println();
			// ��ʾ�������
			while (rs.next()) {
				for (int i = 1; i <= columns; i++) {
					System.out.print(rs.getString(i));
					System.out.print("\t\t");
				}
				System.out.println();
			}
			// ִ��Oracle�洢����
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
