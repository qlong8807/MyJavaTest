package db.jdbcOracleProc;

import oracle.jdbc.OracleCallableStatement;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;
import java.util.ArrayList;

/**
 * 该存储过程第一个参数为对象，第二个参数为数组。一般需要orai18n.jar，否则会乱码
<hr>
 不用判断list集合大小、不用考虑数组长度的做法就是用table。
 使用的类型：你要新增的数据有多少字段就添加相应个数的类型
 create or replace type i_table is table of number;
 create or replace type t_table is table of varchar2(30);
 create or replace type a_table is table of varchar2(30);
 存储过程:
 create or replace procedure pro_forall_insert(v_1 i_table,
 v_2 t_table,
 v_3 a_table) as
 begin
   forall i in 1 .. v_1.count
      insert into test_table values (v_1(i), v_2(i), v_3(i));
 END;

 <hr>
 在数据库中建立相应的java对象(Oracle中的类型)和数组.
 CREATE OR REPLACE TYPE yOracleObject(类型名称) AS OBJECT(
 yid number,ytel varchar2(50),yanumber varchar2(50)
 );
 数组：
 CREATE OR REPLACE TYPE y_Oracle_LIST(数组名称) AS VARRAY(5000) OF yOracleObject(类型名称);
 存储过程：
 CREATE OR REPLACE PROCEDURE proc_test_new(y_Array IN y_oracle_list,
                                            p_out   OUT NUMBER) AS
 v_yID     number;
 v_yTel    varchar(50);
 v_anumber varchar(50);
 v_type    yoracleobject;
 begin
     FOR I IN 1 .. y_Array.COUNT LOOP
         v_type    := y_Array(i);
         v_yID     := v_type.yid;
         v_yTel    := v_type.ytel;
         v_anumber := v_type.yanumber;
         insert into test_table values (v_yID, v_yTel, v_anumber);
     end loop;
     commit;
     p_out := 0;
  EXCEPTION
     WHEN OTHERS THEN
         p_out := -1;
         ROLLBACK;
 END;
 <hr>

 */
public class TestJdbcProcStruct {

    public static void main(String[] args) throws Exception {
        Connection con = getCon();
        CallableStatement stmt = con.prepareCall("{call SCMS_TOOL_PKG.VERIFY(?,?,?,?)}");
        StructDescriptor structdesc = new StructDescriptor("TOOL_VERIFY_TYPE", con);
        STRUCT struct = null;
        Object[] result = new Object[11];
        result[0] = "313";//工具单编号
        result[1] = "0";//核验标志，0成功/1失败
        result[2] = "777";//inUserId
        result[3] = "inUserName";//inUserName
        result[4] = "777";//inUserIdcardNo
        result[5] = "S12";//verifyChannel
        result[6] = "111";//verifyUserId
        result[7] = "1verifyUserName";//verifyUserName
        result[8] = "111";//verifyUserIdcardNo
        result[9] = "0";//verifyFailReason
        result[10] = "verifyFailRemark";//verifyFailRemark
        /*
         * 一定要记得导入orai18n.jar
         * 否则一遇到字符串就乱码、添加不到数据
         */
        struct = new STRUCT(structdesc, con, result);
        ((OracleCallableStatement) stmt).setSTRUCT(1, struct);

        ArrayList<String> params = new ArrayList<>();
        ARRAY adArray = getOracleArray(con, "Y_ORACLE_LIST",params);
        ((OracleCallableStatement) stmt).setARRAY(2, adArray);

        stmt.registerOutParameter(3, Types.INTEGER);
        stmt.registerOutParameter(4, Types.NVARCHAR);
        stmt.execute();
        Integer res = stmt.getInt(3);
        String errInfo = stmt.getString(4);
        System.out.println(res+" --- "+errInfo);
    }
    private static ARRAY getOracleArray(Connection con, String Oraclelist,
                                        ArrayList objlist) throws Exception {
        ARRAY array = null;
        if (objlist != null && objlist.size() > 0) {
            /**
             * 必须大写类型名称
             * 否则会报错：java.sql.SQLException: 无效的名称模式: M_ORDER.yoracleobject
             */
            StructDescriptor structdesc = new StructDescriptor("YORACLEOBJECT", con);
            STRUCT[] structs = new STRUCT[objlist.size()];
            Object[] result;
            for (int i = 0; i < objlist.size(); i++) {
                result = new Object[3];
                TestBean t = (TestBean)(objlist.get(i));
                result[0] = t.getYid();
                result[1] = t.getYtel();
                result[2] = t.getYanumber();
                /*
                 * 一定要记得导入orai18n.jar
                 * 否则一遇到字符串就乱码、添加不到数据
                 */
                structs[i] = new STRUCT(structdesc, con, result);
            }
            ArrayDescriptor desc = ArrayDescriptor.createDescriptor(Oraclelist,
                    con);
            array = new ARRAY(desc, con, structs);
        } else {
            ArrayDescriptor desc = ArrayDescriptor.createDescriptor(Oraclelist,
                    con);
            STRUCT[] structs = new STRUCT[0];
            array = new ARRAY(desc, con, structs);
        }
        return array;
    }
    public static Connection getCon() {
        Connection connection = null;
        try {
            String url="jdbc:oracle:thin:@10.254.8.38:1521:ORCL";
            String user="scms";
            String pwd="ytkj_scms";
            String driver="oracle.jdbc.driver.OracleDriver";
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, pwd);
        } catch (Exception err) {
            err.printStackTrace();
        }
        return connection;
    }
}


class TestBean {

    private Integer yid;
    private String ytel;
    private String yanumber;

    public Integer getYid() {
        return yid;
    }

    public void setYid(Integer yid) {
        this.yid = yid;
    }

    public String getYtel() {
        return ytel;
    }

    public void setYtel(String ytel) {
        this.ytel = ytel;
    }

    public String getYanumber() {
        return yanumber;
    }

    public void setYanumber(String yanumber) {
        this.yanumber = yanumber;
    }
}
