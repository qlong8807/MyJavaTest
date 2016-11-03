package db.dao.dbUtils;

import java.sql.SQLException;
import java.util.List;


/**
 * 操作mysql数据库的公共接口�?
 * @author ss
 *
 * @param <T> 实体
 */
public interface BaseDao<T> {
	/**
	 * 添加数据
	 * @param t 实体�?
	 * @return 1：添加成功，0：添加失�?
	 * @throws SQLException SQL异常
	 */
	public int add(T t)throws SQLException;
	/**
	 * 修改数据
	 * @param t 实体�?
	 * @return 1：添加成功，0：添加失�?
	 * @throws SQLException SQL异常
	 */
	public int update(T t) throws SQLException;
	/**
	 * 批量删除数据，支持单条删�?
	 * @param id {@link Integer}主键
	 * @return 1：删除成功，0：删除失�?
	 * @throws SQLException SQL异常
	 */
	@SuppressWarnings("rawtypes")
	public int delete(Class clazz,int[] id) throws SQLException;
	/**
	 * 根据主键查找数据
	 * @param id {@link Integer}主键
	 * @return 实体
	 */
	@SuppressWarnings("rawtypes")
	public T findById(int id,Class clazz);
	/**
	 * 查找数据，支持分页查�?
	 * @param param {@link Integer}[]当param未空的时候返回所有记录，不为空时代表分页查询，param[0]:起始位置，param[1]:结束位置
	 * @return 实体集合
	 */
	@SuppressWarnings("rawtypes")
	public List<T> findAll(Class clazz,int... param);
	
	
}
