package test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDBMetaData {

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rm;
		DatabaseMetaData dmd;
		int n = 1;

		String url = "jdbc:oracle:thin:@192.168.20.7:1521:sxnet";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, "bankalarm", "secretnew");
			dmd = conn.getMetaData();
			if (dmd == null) {
				System.out.println("no Meta avaliable.");
			} else {
				System.out.println("Database Name:"
						+ dmd.getDatabaseProductName());
				System.out.println("Database Version:"
						+ dmd.getDatabaseProductVersion());
				System.out.println("Database Driver:" + dmd.getDriverName());
				System.out.println("Database DriverVerion:"
						+ dmd.getDriverVersion());
				System.out.println("Database DataBase TypeList:");
				rs = dmd.getTypeInfo();
				rs.next();
				for (int i = 1; i <= n; i++) {
					if (rs.getString(i) != null) {
						n += 1;
					}
				}
				while (rs.next()) {
					for (int i1 = 1; i1 < n; i1++) {
						System.out.print("\t" + rs.getString(i1) + "\t");
					}
					System.out.println();
				}
				rs.close();
			}
			stmt = conn.createStatement();
			String sql = "select * from SIP_DEVICE_INFO";
			rs = stmt.executeQuery(sql);
			System.out.println("The data table structure info:");
			rm = rs.getMetaData();
			int columnNum = rm.getColumnCount();
			System.out.println("Num \tcolumnName \tDataType");
			for (int i = 1; i <= columnNum; i++) {
				System.out.println(i + "\t" + rm.getColumnName(i) + "\t"
						+ rm.getColumnTypeName(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}