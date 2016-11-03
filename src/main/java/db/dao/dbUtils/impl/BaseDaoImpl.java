package db.dao.dbUtils.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import db.dao.dbUtils.BaseDao;
import db.dao.dbUtils.MySqlConnPoolUtil;
import db.dao.dbUtils.common.DBEntity;
import db.dao.dbUtils.common.ObjectForMap;
import db.dao.dbUtils.common.ObjectForMapException;

public class BaseDaoImpl<T> implements BaseDao<T> {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> queryForList(String sql, Class clazz, Object... args)
			throws SQLException {
		try {
			/* sql��Ҫִ�е�sql;args:�õ��Ĳ��� */
			QueryRunner qryRun = new QueryRunner();
			Connection conn = MySqlConnPoolUtil.getContainer().get();
			List<T> list = qryRun.query(conn, sql,
					new BeanListHandler<T>(clazz), args);
			conn.commit();
			return list;
		} catch (SQLException e) {
			throw e;
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public T queryForObject(String sql, Class clazz, Object... args)
			throws SQLException {
		try {
			/* sql��Ҫִ�е�sql;args:�õ��Ĳ��� */
			QueryRunner qryRun = new QueryRunner();
			Connection conn = MySqlConnPoolUtil.getContainer().get();
			T t = qryRun.query(conn, sql, new BeanHandler<T>(clazz), args);
			conn.commit();
			return t;
		} catch (SQLException e) {
			throw e;
		}
	}

	public void executeUpdate(String sql, Object... args) throws SQLException {
		try {
			/* sql��Ҫִ�е�sql;args:�õ��Ĳ��� */
			QueryRunner qryRun = new QueryRunner();
			Connection conn = MySqlConnPoolUtil.getContainer().get();
			qryRun.update(conn, sql, args);
			conn.commit();
		} catch (SQLException e) {
			throw e;
		}
	}

	public int[] batchUpdate(String sql, Object[][] objs) throws SQLException {
		int[] ids = null;
		try {
			QueryRunner qryRun = new QueryRunner();
			Connection conn = MySqlConnPoolUtil.getContainer().get();
			ids = qryRun.batch(conn, sql, objs);
			conn.commit();
		} catch (SQLException e) {
			throw e;
		}
		return ids;
	}

	@Override
	public int add(T t) throws SQLException {
		try {
			// ���ʵ��������
			DBEntity entity = ObjectForMap.dbObjectForMap(t);
			// �õ���ע��
			String tableName = entity.getTableName();
			// �õ���������
			String fields = entity.fieldNamesToString();
			// �õ����Ե�ֵ
			Object[] values = entity.fieldValues();
			// ƴ��sql
			StringBuilder builder = new StringBuilder();
			builder.append("INSERT INTO ");
			builder.append(tableName);
			builder.append("(" + fields.toUpperCase());
			builder.append(") VALUES(");
			for (int i = 0; i < values.length; i++) {
				if (i == 0) {
					builder.append("?");
				} else {
					builder.append(",?");
				}
			}
			builder.append(")");
			// ִ��sql
			executeUpdate(builder.toString(), values);
			return 1;
		} catch (ObjectForMapException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int update(T t) throws SQLException {
		try {
			// ���ʵ��������
			DBEntity entity = ObjectForMap.dbObjectForMap(t);
			// �õ���ע��
			String tableName = entity.getTableName();
			// �õ�������Ч����
			String[] fields = entity.fieldNames();
			// �õ���Ч���Ե�ֵ
			Object[] values = entity.fieldValues();
			// ƴ��sql
			StringBuilder builder = new StringBuilder();
			builder.append("UPDATE ");
			builder.append(tableName.toUpperCase());
			builder.append(" SET ");
			for (int i = 0; i < fields.length; i++) {
				if (i == 0) {
					builder.append(fields[i].toUpperCase() + "=?");
				} else {
					builder.append("," + fields[i].toUpperCase() + "=?");
				}
			}
			builder.append(" WHERE " + entity.getPrimaryKeyName().toUpperCase()
					+ "=?");
			// ִ��sql
			Object[] objValues=new Object[values.length+1];
			for (int i = 0; i < objValues.length; i++) {
				if(i==objValues.length-1){
					objValues[i]=entity.getPrimaryKeyValue();
				}else {
					objValues[i]=values[i];
				}
			}
			executeUpdate(builder.toString(),objValues );
			return 1;
		} catch (ObjectForMapException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int delete(Class clazz, int[] id) throws SQLException {
		try {
			T obj = (T) clazz.newInstance();
			DBEntity entity = ObjectForMap.dbObjectForMap(obj);
			// �õ���ע��
			String tableName = entity.getTableName();
			// �õ�����
			String primaryKey = entity.getPrimaryKeyName();
			// ƴ��sql
			StringBuilder builder = new StringBuilder();
			builder.append("DELETE FROM ");
			builder.append(tableName.toUpperCase());
			builder.append(" WHERE ");
			builder.append(primaryKey.toUpperCase());
			builder.append(" IN (");
			for (int i = 0; i < id.length; i++) {
				if (i == 0) {
					builder.append(id[i]);
				} else {
					builder.append("," + id[i]);
				}
			}
			builder.append(")");
			// ִ��sql
			executeUpdate(builder.toString());
			return 1;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ObjectForMapException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public T findById(int id, Class clazz) {

		try {
			T obj = (T) clazz.newInstance();
			DBEntity entity = ObjectForMap.dbObjectForMap(obj);
			// �õ���Ч����
			String fields = entity.fieldNamesToString();
			// ƴ������
			fields += "," + entity.getPrimaryKeyName();
			// ƴ��sql
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT ");
			builder.append(fields.toUpperCase());
			builder.append(" FROM ");
			builder.append(entity.getTableName());
			builder.append(" where " + entity.getPrimaryKeyName().toUpperCase()
					+ "=?");
			return queryForObject(builder.toString().toUpperCase(), clazz, id);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ObjectForMapException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<T> findAll(Class clazz, int... param) {
		// ���ж�param�Ƿ�Ϊ�գ�����Ϊ�����ѯ����ҳ��param[0]:��ʼλ�ã�param[1]:����λ��
		try {
			T obj = (T) clazz.newInstance();
			DBEntity entity = ObjectForMap.dbObjectForMap(obj);
			// �õ���Ч����
			String fields = entity.fieldNamesToString();
			// ƴ������
			fields += "," + entity.getPrimaryKeyName();
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT ");
			builder.append(fields.toUpperCase());
			builder.append(" FROM ");
			builder.append(entity.getTableName());
			if (param != null && param.length > 0) {
				// ��ҳ��ѯ
				builder.append(" LIMIT ?,?");
				return queryForList(builder.toString(), clazz, param[0],
						param[1] - param[0]);
			} else {
				// �������е�
				return queryForList(builder.toString(), clazz);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ObjectForMapException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
