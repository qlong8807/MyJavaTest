package db.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestOracleJDBC {
	public static void main(String[] args) {
		batchinsert();
	}

	public static void oneinsert() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection(
					"jdbc:Oracle:thin:@192.168.0.216:1521:orcl", "pig", "pig");
			if (null == connection) {
				System.out.println("获取不到数据库连接");
				System.exit(0);
			}
			PreparedStatement psp = connection
					.prepareStatement("select max(TRADE_BASE_NO) from TRADE_BASE");
			ResultSet executeQuery = psp.executeQuery();
			executeQuery.next();
			int id = executeQuery.getInt(1);
			long t1 = System.currentTimeMillis() / 1000;
			int i = 0;
			while (id < 200000000) {
				id++;
				i++;
				String sql = "insert into TRADE_BASE(TRADE_BASE_NO,REQUEST_HISTORY_NO,TOTAL_AMOUNT,TRADE_TYPE,PAY_TYPE,CREATE_DATE,UPDATE_DATE) values(?,?,?,?,?,?,?)";
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setInt(1, id);
				ps.setLong(2, 20161031151600044L);
				ps.setLong(3, 140);
				ps.setString(4, "0001");
				ps.setString(5, "0001");
				long millis = System.currentTimeMillis();
				ps.setDate(6, new Date(millis));
				ps.setDate(7, new Date(millis));
				boolean flag = ps.execute();
				// System.out.println("插入第" + id + "条记录，结果：" + flag);
				long t2 = System.currentTimeMillis() / 1000;
				if (t1 != t2) {
					System.out.println(t2 - t1 + "秒插入条数：" + i + ",当前ID：" + id);
					i = 0;
					t1 = t2;
				}
				ps.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void batchinsert() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection(
					"jdbc:Oracle:thin:@192.168.0.216:1521:orcl", "pig", "pig");
			if (null == connection) {
				System.out.println("获取不到数据库连接");
				System.exit(0);
			}
			PreparedStatement psp = connection
					.prepareStatement("select max(TRADE_BASE_NO) from TRADE_BASE");
			ResultSet executeQuery = psp.executeQuery();
			executeQuery.next();
			int id = executeQuery.getInt(1);
			long t1 = System.currentTimeMillis() / 1000;
			int i = 0;
			while (id < 200000000) {
				id++;
				i++;
				connection.setAutoCommit(false);
				String sql = "insert into TRADE_BASE(TRADE_BASE_NO,REQUEST_HISTORY_NO,TOTAL_AMOUNT,TRADE_TYPE,PAY_TYPE,CREATE_DATE,UPDATE_DATE) values(?,?,?,?,?,?,?)";
				PreparedStatement ps = connection.prepareStatement(sql);
				for (int j = 0; j < 10000; j++) {
					ps.setInt(1, id);
					ps.setLong(2, 20161031151600044L);
					ps.setLong(3, 140);
					ps.setString(4, "0001");
					ps.setString(5, "0001");
					long millis = System.currentTimeMillis();
					ps.setDate(6, new Date(millis));
					ps.setDate(7, new Date(millis));
					ps.addBatch();
					id++;
					i++;
				}
				ps.executeBatch();
				connection.commit();
				long t2 = System.currentTimeMillis() / 1000;
				if (t1 != t2) {
					System.out.println(t2 - t1 + "秒插入条数：" + i + ",当前ID：" + id);
					i = 0;
					t1 = t2;
				}
				ps.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
