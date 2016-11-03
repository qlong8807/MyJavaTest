package db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class ConnectionPool {

	private static Vector<Connection> pool;

	/* �������� */
	private static String url = "jdbc:mysql://172.16.1.230:3306/test";
	private static String username = "root";
	private static String password = "root";
	private static String driverClassName = "com.mysql.jdbc.Driver";

	private static int poolSize = 2;
	private static ConnectionPool instance = null;
	static Connection conn = null;

	/* ���췽������һЩ��ʼ������ */
	static {
		pool = new Vector<Connection>(poolSize);

		for (int i = 0; i < poolSize; i++) {
			try {
				Class.forName(driverClassName);
				conn = DriverManager.getConnection(url, username, password);
				pool.add(conn);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/* �������ӵ����ӳ� */
	public static synchronized void release() {
		pool.add(conn);
	}

	/* �������ӳ��е�һ����ݿ����� */
	public static synchronized Connection getConnection() {
		if (pool.size() > 0) {
			Connection conn = pool.get(0);
			pool.remove(conn);
			return conn;
		} else {
			return null;
		}
	}
}
