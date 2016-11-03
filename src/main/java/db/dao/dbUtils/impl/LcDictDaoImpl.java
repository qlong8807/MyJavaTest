package db.dao.dbUtils.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import db.dao.dbUtils.LcDictDBEntity;
import db.dao.dbUtils.LcDictDao;
import db.dao.dbUtils.MySqlConnPoolUtil;
import db.dao.dbUtils.common.DBEntity;
import db.dao.dbUtils.common.ObjectForMap;
import db.dao.dbUtils.common.ObjectForMapException;


public class LcDictDaoImpl extends BaseDaoImpl<LcDictDBEntity> implements
		LcDictDao {


	@Override
	public List<LcDictDBEntity> queryDictList(String dtName, int dictId) {
		try {
			StringBuilder builder = new StringBuilder(
					"select dict.DICT_ID,dict.DICT_CODE,dict.GB_CODE,dict.DT_CODE,dict.DICT_NAME,dict.DICT_DATA from LC_DICT dict inner join LC_DICT_TYPE dictType on dict.DT_CODE=dictType.DT_CODE where 1=1 ");
			if (dtName != null || "".equals(dtName)) {
				builder.append(" and  dictType.DICT_NAME like '%" + dtName
						+ "%'" + " ");
			}
			if (dictId != 0) {
				builder.append(" and  dict.DICT_ID=" + dictId);
			}
			QueryRunner qryRun = new QueryRunner();
			Connection conn = MySqlConnPoolUtil.getContainer().get();
			List<LcDictDBEntity> list = qryRun.query(conn, builder.toString()
					.toUpperCase(), new BeanListHandler<LcDictDBEntity>(
					LcDictDBEntity.class));
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public int updateByDictCode(LcDictDBEntity lcDictDBEntity) {
		try {
			// 获得实体类属�?
			DBEntity entity = ObjectForMap.dbObjectForMap(lcDictDBEntity);
			// 得到类注�?
			String tableName = entity.getTableName();
			// 得到�?��有效属�?
			String[] fields = entity.fieldNames();
			// 得到有效属�?的�?
			Object[] values = entity.fieldValues();
			// 拼接sql
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
			builder.append(" WHERE DICT_CODE=?");
			// 执行sql
			Object[] objValues = new Object[values.length];
			for (int i = 0; i <= objValues.length; i++) {
				if (i == objValues.length) {
					objValues[i] = lcDictDBEntity.getDict_code();
				} else {
					objValues[i] = values[i];
				}
			}
			executeUpdate(builder.toString(), objValues);
			return 1;
		} catch (ObjectForMapException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<LcDictDBEntity> getDictByType(int dictType,int currentPage,int pageSize) {
		try {
			String sql = "SELECT dict.DICT_ID, dict.DICT_CODE, dict.GB_CODE, dict.DT_CODE, dict.DICT_NAME,dict.DICT_DATA FROM LC_DICT dict";
			QueryRunner qryRun = new QueryRunner();
			Connection conn = MySqlConnPoolUtil.getContainer().get();
			List<LcDictDBEntity> list=null;
			if(dictType != 0){
				sql=sql+" WHERE dict.DT_CODE=?";
				if(currentPage>0){
					int beginRecord = (currentPage-1)*pageSize;//�?��记录
					int endRecord = pageSize;//从开始到结束的记录数
					sql=sql+"  LIMIT "+beginRecord+","+endRecord+"";
				}
				list = qryRun.query(conn, sql.toUpperCase(),
						new BeanListHandler<LcDictDBEntity>(LcDictDBEntity.class),dictType);
			}else {
				if(currentPage>0){
					int beginRecord = (currentPage-1)*pageSize;//�?��记录
					int endRecord = pageSize;//从开始到结束的记录数
					sql=sql+"  LIMIT "+beginRecord+","+endRecord+"";
				}
				list = qryRun.query(conn, sql.toUpperCase(),
						new BeanListHandler<LcDictDBEntity>(LcDictDBEntity.class));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int deleteByCode(int[] dictCode) {
		int result=0;
		try {
			StringBuilder builder = new StringBuilder(
					"DELETE  FROM LC_DICT  WHERE DICT_CODE=?");
			QueryRunner qryRun = new QueryRunner();
			Connection conn = MySqlConnPoolUtil.getContainer().get();
			result= qryRun.update(conn, builder.toString()
					.toUpperCase(), new BeanListHandler<LcDictDBEntity>(
							LcDictDBEntity.class), dictCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<LcDictDBEntity> getDictByCode(int dictCode) {
		try {
			String sql = "SELECT dict.DICT_ID, dict.DICT_CODE, dict.GB_CODE, dict.DT_CODE, dict.DICT_NAME,dict.DICT_DATA FROM LC_DICT dict";
			QueryRunner qryRun = new QueryRunner();
			Connection conn = MySqlConnPoolUtil.getContainer().get();
			List<LcDictDBEntity> list=null;
			if(dictCode != 0){
				sql=sql+" WHERE dict.DICT_CODE=?";
				list = qryRun.query(conn, sql.toUpperCase(),
						new BeanListHandler<LcDictDBEntity>(LcDictDBEntity.class),dictCode);
			}else {
				list = qryRun.query(conn, sql.toUpperCase(),
						new BeanListHandler<LcDictDBEntity>(LcDictDBEntity.class));
			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getDictDataResCount(int dictType) {
		try {
			String sql = "SELECT dict.DICT_ID, dict.DICT_CODE, dict.GB_CODE, dict.DT_CODE, dict.DICT_NAME FROM LC_DICT dict";
			QueryRunner qryRun = new QueryRunner();
			Connection conn = MySqlConnPoolUtil.getContainer().get();
			List<LcDictDBEntity> list=null;
			int count = 0;
			if(dictType != 0){
				sql=sql+" WHERE dict.DT_CODE=?";
				list = qryRun.query(conn, sql.toUpperCase(),
						new BeanListHandler<LcDictDBEntity>(LcDictDBEntity.class),dictType);
			}else {
				list = qryRun.query(conn, sql.toUpperCase(),
						new BeanListHandler<LcDictDBEntity>(LcDictDBEntity.class));
			}
			if(list!=null){
				count = list.size();
			}
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
