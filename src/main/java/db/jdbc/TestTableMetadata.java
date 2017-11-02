package db.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestTableMetadata {

	private static Connection con = null;

	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.216:1521:orcl", "ykt", "ykt");
			if (null == con) {
				System.out.println("获取不到数据库连接");
				System.exit(0);
			}
			get();
			getTableInfo("card_trade_detail");
			// sysoutOracleTCloumns("card_trade_detail", "ykt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void get() throws Exception {
		DatabaseMetaData dbmd = con.getMetaData();
		List<String> v = new ArrayList<String>();
		ResultSet rs = null;
		String[] typeList = new String[] { "TABLE" };
		rs = dbmd.getTables(null, "ykt", null, typeList);
		for (boolean more = rs.next(); more; more = rs.next()) {
			String s = rs.getString("TABLE_NAME");
			String type = rs.getString("TABLE_TYPE");
			if (type.equalsIgnoreCase("table") && s.indexOf("$") == -1)
				v.add(s);
		}
		for (String s : v) {
			System.out.println(s);
		}
	}

	/***
	 * 打印ORACLE的表模板参数文件(jsp):
	 * 
	 * @throws SQLException
	 */
	public static void sysoutOracleTCloumns(String Table, String Owner) throws SQLException {
		List<HashMap<String, String>> columns = new ArrayList<HashMap<String, String>>();
		try {
			Statement stmt = con.createStatement();
			String sql = "select " + "         comments as \"Name\"," + "         a.column_name \"Code\","
					+ "         a.DATA_TYPE as \"DataType\"," + "         b.comments as \"Comment\","
					+ "         decode(c.column_name,null,'FALSE','TRUE') as \"Primary\","
					+ "         decode(a.NULLABLE,'N','TRUE','Y','FALSE','') as \"Mandatory\","
					+ "         '' \"sequence\"" + "   from " + "       all_tab_columns a, "
					+ "       all_col_comments b," + "       (" + "        select a.constraint_name, a.column_name"
					+ "          from user_cons_columns a, user_constraints b"
					+ "         where a.constraint_name = b.constraint_name"
					+ "               and b.constraint_type = 'P'" + "               and a.table_name = '" + Table + "'"
					+ "       ) c" + "   where " + "     a.Table_Name=b.table_Name "
					+ "     and a.column_name=b.column_name" + "     and a.Table_Name='" + Table + "'"
					+ "     and a.owner=b.owner " + "     and a.owner='" + Owner + "'"
					+ "     and a.COLUMN_NAME = c.column_name(+)" + "  order by a.COLUMN_ID";
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("Name", rs.getString("Name"));
				map.put("Code", rs.getString("Code"));
				map.put("DataType", rs.getString("DataType"));
				map.put("Comment", rs.getString("Comment"));
				map.put("Primary", rs.getString("Primary"));
				map.put("Mandatory", rs.getString("Mandatory"));
				columns.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}

		// 输出
		for (HashMap<String, String> map : columns) {
			String Name = map.get("Name");
			String Code = map.get("Code");
			String Comment = map.get("Comment");
			String DataType = map.get("DataType");
			String Primary = map.get("Primary");
			Name = Comment.split("\\s+")[0];
			String Mandatory = map.get("Mandatory");
			String sequence = map.get("sequence");
			String str = "table.cols.add(new Column(\"" + Name + "\",\"" + Code + "\",\"" + Comment + "\",\"" + DataType
					+ "\",\"" + Primary + "\",\"" + Mandatory + "\",\"" + (sequence == null ? "" : sequence) + "\"));";
			System.out.println(str);
		}
	}

	public static void getTableInfo(String tableName) throws Exception {
		String sql = "select * from " + tableName;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			String colname = rsmd.getColumnName(i);
			String typeName = rsmd.getColumnTypeName(i);
			int itype = rsmd.getColumnType(i);
			int size = rsmd.getColumnDisplaySize(i);
			int precision = rsmd.getPrecision(i);
			int n = rsmd.isNullable(i);
			int scale = rsmd.getScale(i);
			boolean nullable = true;
			switch (n) {
			case 0: // '/0'
				nullable = false;
				break;

			case 1: // '/001'
				nullable = true;
				break;

			default:
				nullable = true;
				break;
			}
			
			System.out.println(colname+"--"+typeName);
			
		}
	}
}
