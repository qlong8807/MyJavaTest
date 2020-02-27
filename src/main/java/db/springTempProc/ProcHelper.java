package db.springTempProc;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Date;
import java.util.*;

@Component
@Slf4j
public class ProcHelper {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void demo2(){
        Map<String, Object> in = new LinkedHashMap<>();
        in.put("I_TOOL_NO", "");
        in.put("I_REMARK", "");
        Map<String, Integer> out = new LinkedHashMap<>();
        long start = System.currentTimeMillis();
        CommonResultDTO result = dealProc("{call SCMS_TOOL_PKG.EDIT_VERIFY_CANCEL(?,?,?,?)}", in, out);
        long end = System.currentTimeMillis();
        log.info("调用存储过程耗时:[{}]ms,结果:[{}]",end-start,result);
    }

    public CommonResultDTO demo1(DbToolOrderVerify dbToolOrderVerify) {
        return jdbcTemplate.execute(new CallableStatementCreator()
        {
            public CallableStatement createCallableStatement(Connection con) throws SQLException
            {
                if (con.isWrapperFor(OracleConnection.class)) {
                    con = con.unwrap(OracleConnection.class);
                }
                String storedProc = "{call SCMS_TOOL_PKG.VERIFY(?,?,?)}";// 调用的sql
                CallableStatement cs = con.prepareCall(storedProc);
                STRUCT struct = convertSTRUCT(dbToolOrderVerify,con,"TOOL_VERIFY_TYPE");
                cs.setObject(1, struct);// 设置输入参数的值
                cs.registerOutParameter(2, OracleTypes.INTEGER);
                cs.registerOutParameter(3, OracleTypes.NVARCHAR);
                return cs;
            }
        },new CallableStatementCallback<CommonResultDTO>()
        {
            public CommonResultDTO doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException
            {
                cs.execute();
                Integer res = cs.getInt(2);
                String errInfo = cs.getString(3);
                System.out.println(res+" --- "+errInfo);
                CommonResultDTO dto = new CommonResultDTO();
                try {
                    // 有错误结果集的则赋值
                    dto.setOresult(cs.getString((2)));
                    dto.setOerrinfo(cs.getString((3)));
                } catch (Exception e){
                    // 由于有的没有错误结果集，所以捕获异常不做任何处理。此处不影响程序。
                }
                return dto;
            }
        });
    }

    /**
     * 入参和出参都是LinkedHashMap
     * @param procedureName 存储过程名称
     * @param in 入参 可传入Date、Integer、Long、Double、Float、对象等类型
     * @param out 出参，不需要包含o_result和o_errinfo
     * @return
     */
    public CommonResultDTO dealProc(String procedureName, Map<String, Object> in, Map<String, Integer> out) {
        CommonResultDTO commonResult = jdbcTemplate.execute(
                new CallableStatementCreator() {
                    @Override
                    public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                        if (connection.isWrapperFor(OracleConnection.class)) {
                            connection = connection.unwrap(OracleConnection.class);
                        }
                        return deal(connection, procedureName, in, out);
                    }
                },
                new CallableStatementCallback<CommonResultDTO>() {
                    @Override
                    public CommonResultDTO doInCallableStatement(CallableStatement callableStatement) throws SQLException, DataAccessException {
                        return dealResult(callableStatement, in.size(), out);
                    }
                }
        );

        if (commonResult != null && commonResult.getOerrinfo() != null) {
            commonResult.vaild();//TODO 需实现里面的方法
        }
        return commonResult;
    }
    private CallableStatement deal(Connection connection, String procedureName, Map<String, Object> conditions, Map<String, Integer> out) throws SQLException {
        OracleCallableStatement cs = null;
        try {
            cs = (OracleCallableStatement)connection.prepareCall(procedureName);
            int len = 1;
            Set<String> conditionsKeySet = conditions.keySet();
            for (String key : conditionsKeySet) {
                Object obj = conditions.get(key);
                System.out.println(len+"---"+key+"==="+obj);
                if (obj instanceof Date) {
                    String formmat = "yyyyMMddHHmmss";
                    cs.setObject(len, DateUtil.format((Date) obj, formmat));
                } else if (obj instanceof String) {
                    cs.setObject(len, obj);
                } else if (obj instanceof Integer) {
                    cs.setInt(len, Integer.parseInt(obj.toString()));
                } else if (obj instanceof Long) {
                    cs.setLong(len, Long.parseLong(obj.toString()));
                } else if (obj instanceof Double) {
                    cs.setDouble(len, Double.parseDouble(obj.toString()));
                } else if (obj instanceof Float) {
                    cs.setFloat(len, Float.parseFloat(obj.toString()));
                } else if (obj != null) {//不是基本类型，则是对象，是对象则进行STRUCT转换
                    STRUCT struct = convertSTRUCT(obj, connection, key);
                    cs.setSTRUCT(len, struct);
                }
                len++;
            }
            Set<String> outKeySet = out.keySet();

            for (String key : outKeySet) {
                cs.registerOutParameter(len, out.get(key));
                len++;
            }
            try {
                // 有错误结果集的设置参数
                cs.registerOutParameter(len, OracleTypes.INTEGER);
                cs.registerOutParameter((len + 1), OracleTypes.VARCHAR);
            }catch (Exception e){
                // 由于有的没有错误结果集，所以捕获异常不做任何处理。此处不影响程序。
            }
            log.debug("procedure {} params{}", procedureName, conditions);
            return cs;
        } catch (SQLException e) {
            if (null != cs) {
                cs.close();
            }
            log.error("procedure error! "+e.getMessage());
            throw e;
        }
    }

    public CommonResultDTO dealResult(CallableStatement callableStatement, int length, Map<String, Integer> out) throws SQLException, DataAccessException {
        CommonResultDTO commonResult = new CommonResultDTO();
        try {
            long start = System.currentTimeMillis();
            callableStatement.execute();
            long end = System.currentTimeMillis();
            log.info("调用存储过程耗时[{}]",end-start);

            Map result = new HashMap();
            Set<String> outKeySet = out.keySet();
            for (String key : outKeySet) {
                length++;
                Integer outParam = out.get(key);
                if (outParam == OracleTypes.CURSOR) {
                    // 获取游标一行的值
                    ResultSet rs = null;
                    rs = (ResultSet) callableStatement.getObject(length);
                    List<Map<String, Object>> resultColumn = new ArrayList<>();
                    result.put(key, resultColumn);
                    while (rs.next()) {
                        // 转换每行的返回值到Map中
                        Map<String, Object> rowMap = new HashMap();
                        ResultSetMetaData meta = rs.getMetaData();
                        int count = meta.getColumnCount();
                        for (int i = 1; i <= count; i++) {
                            String columnLable = meta.getColumnLabel(i);
                            rowMap.put(columnLable, rs.getString(columnLable));
                        }
                        resultColumn.add(rowMap);
                    }
                } else {
                    result.put(key, callableStatement.getString(length));
                }
            }
            commonResult.setData(result);
            try {
                // 有错误结果集的则赋值
                commonResult.setOresult(callableStatement.getString((length + 1)));
                commonResult.setOerrinfo(callableStatement.getString((length + 2)));
            } catch (Exception e){
                // 由于有的没有错误结果集，所以捕获异常不做任何处理。此处不影响程序。
            }
            log.debug("result {}", commonResult);
            return commonResult;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            if (null != callableStatement) {
                callableStatement.close();
            }
        }
    }

    /**
     * 获取定义的所有字段
     */
    private Field[] getAllFields(Object object) {
        Class clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != Object.class) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }
    private STRUCT convertSTRUCT(Object dto, Connection connection, String descName) throws SQLException {
        StructDescriptor structDescriptor = new StructDescriptor(descName.toUpperCase(), connection);
        Field[] fields = getAllFields(dto);
        Map<Integer, Object> orderValue = new TreeMap<>();
        for (Field field : fields) {
            OracleDataType type = field.getAnnotation(OracleDataType.class);
            field.setAccessible(true);
            if (type != null) {
                try {
                    orderValue.put(type.order(), field.get(dto));
                    //Date类型覆盖旧值
                    if (field.getType().equals(Date.class)) {
                        Date date = (Date) field.get(dto);
                        String formmat = "".equals(type.dateFormat()) ? "yyyyMMddHHmm" : type.dateFormat();
                        if (date != null) {
                            orderValue.put(type.order(), DateUtil.format(date, formmat));
                        }
                    }
                    if (field.getType().equals(List.class)) {
                        OracleDataType[] s = field.getDeclaredAnnotationsByType(OracleDataType.class);
                        String arrayDesc = s[0].arrayDesc();
                        String structDesc = s[0].structDesc();

                        ARRAY array = convertARRAY((List) field.get(dto), connection, arrayDesc, structDesc);
                        orderValue.put(type.order(), array);
                    }

                } catch (IllegalAccessException e) {
                    log.error("convertOne exception", e);
                }
            }
        }
        Object[] data = new Object[orderValue.size()];
        for (Integer index : orderValue.keySet()) {
            data[index - 1] = orderValue.get(index);
        }
        return new STRUCT(structDescriptor, connection, data);
    }


    private ARRAY convertARRAY(List dtoList, Connection connection, String arrayDescName, String structDescName) throws SQLException {
        if (dtoList == null) {
            return null;
        }

        ArrayDescriptor flightSetDesc = ArrayDescriptor.createDescriptor(arrayDescName.toUpperCase(), connection);
        List<Object> obj = new ArrayList<>();
        for (Object dto : dtoList) {
            STRUCT struct = convertSTRUCT(dto, connection, structDescName.toUpperCase());
            obj.add(struct);
        }
        return new ARRAY(flightSetDesc, connection, obj.toArray());
    }
}
