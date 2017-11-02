package db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestOracleJDBC2 {
	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
//		oneinsert();
		test();
		long t2 = System.currentTimeMillis();
		System.out.println(t2-t1);
	}

	public static void oneinsert() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection(
					"jdbc:Oracle:thin:@192.168.0.230:1521:orcl", "ykt", "ykt");
			if (null == connection) {
				System.out.println("获取不到数据库连接");
				System.exit(0);
			}
			int id = 1;
			long t1 = System.currentTimeMillis() / 1000;
			int i = 0;
			long l1 = System.currentTimeMillis();
			while (id < 2000000) {
				i++;
				id++;
				String sql = "insert into BUS_VALIDATE2(KEY1,CREAT_DATE) values(?,?)";
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setString(1, "ASLDFJAS;DLKFJALSDKFJASDLKFJASDFAUSHDFOQERYHFNVZKNEaRYFHVNBDJCXVBNALJDSHUFOUEFASNDKLJNVZXC"+id);
				ps.setString(2, "20161031");
				boolean flag = ps.execute();
//				if(!flag) {
//					System.out.println("error-----------"+i);
//				}
				// System.out.println("插入第" + id + "条记录，结果：" + flag);
				long t2 = System.currentTimeMillis() / 1000;
				if (t1 != t2) {
					System.out.println(t2 - t1 + "秒插入条数：" + i + ",当前ID：" + id);
					i = 0;
					t1 = t2;
				}
				ps.close();
			}
			long lt = System.currentTimeMillis();
			System.out.println("time:"+(lt-l1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void test() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection(
					"jdbc:Oracle:thin:@192.168.0.230:1521:orcl", "ykt", "ykt");
			if (null == connection) {
				System.out.println("获取不到数据库连接");
				System.exit(0);
			}
			long l1 = System.currentTimeMillis();
			String sql = "select count(t1.card_inner_no) from card_account t1 where 1=1";
			String sql2 = "SELECT * FROM (SELECT a.*, ROWNUM rn FROM (select t1.card_version,t1.internal_card_type,t1.card_status,t1.recharge_counter,t1.total_recharge_number,t1.biggest_consumer_counter, t1.card_auth_code,t1.state_change_effect_date,t1.test_card,t1.card_print_no,t1.reserve_field, t1.card_csn,t1.selling_way,t1.consum_aggregate_amount,t1.last_trade_date,t1.card_inner_no, t1.card_type,t1.aggregate_amount,t1.last_trade_time,t1.media_type,t1.deposit,t1.sell_card_time,t1.total_consum_number, t1.regist_card_pwd,t1.sell_card_date,t1.remaining_amount,t1.consum_counter,t1.biggest_recharge_counter,t1.issuance_batch_no, t1.create_card_date,t1.validity,t1.book_balance,t1.regist_auth_info,t1.card_city,t1.is_sleep,t2.description from CARD_ACCOUNT t1 LEFT JOIN card_type_info t2 on t1.card_type = t2.id where 1=1 order by card_inner_no desc) \r\n" + 
					"   a WHERE ROWNUM <= 15) WHERE rn >= 1 ";
//			PreparedStatement ps = connection.prepareStatement(sql);
//			ResultSet rs = ps.executeQuery();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql2);
//			rs.next();
//			int int1 = rs.getInt(1);
			int count = 0;
	        try {
	            while(rs.next()){
	                count = count + 1;
	            }
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
//			System.err.println(int1);
	        System.err.println(count);
//			ps.close();
			long lt = System.currentTimeMillis();
			System.out.println("time:"+(lt-l1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
