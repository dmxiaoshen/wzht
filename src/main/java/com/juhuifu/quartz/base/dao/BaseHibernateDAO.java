package com.juhuifu.quartz.base.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.jdbc.Work;

import com.juhuifu.quartz.base.entity.BaseEntity;
import com.juhuifu.quartz.base.page.Pagination;

public interface BaseHibernateDAO {
    /**
     * flush
     */
    void flush();

    /**
     * 清除缓存
     */
    void clear();

    /**
     * 清除缓存中指定的对象
     * 
     * @param object
     *            object
     */
    void evict(Object object);

    /**
     * 保存新增或修改的对象.
     * 
     * @param entity
     *            对象必须是session中的对象或含id属性的transient对象.
     */
    void save(BaseEntity entity);

    /**
     * 纯创建一个对象
     * 
     * @param entity
     *            和数据库关系的对象
     */
    void add(Object entity);

    /**
     * 保存修改的对象.
     * 
     * @param entity
     *            对象必须是session中的对象或含id属性的transient对象.
     */
    void saveOrUpdate(Object entity);

    /**
     * 删除对象.
     * 
     * @param entity
     *            对象必须是session中的对象或含id属性的transient对象.
     */
    void delete(Object entity);

    /**
     * 按id删除对象.
     * 
     * @param clazz
     *            对象类型
     * @param id
     *            主键ID
     */
    void delete(Class<?> clazz, Long id);

    /**
     * 按id获取对象.
     * 
     * @param clazz
     *            对象类型
     * @param id
     *            主键ID
     * @param <T>
     *            泛型
     * @return T
     */
    <T> T get(Class<T> clazz, final Long id);

    /**
     * 按id load对象.
     * 
     * @param clazz
     *            对象类型
     * @param id
     *            主键ID
     * @param <T>
     *            泛型
     * @return T
     */
    <T> T load(Class<T> clazz, final Long id);

    /**
     * findByNativeSql(根据原生SQL查询对象列表)
     * 
     * @Title: findByNativeSql
     * @param nativeSql
     *            sql语句
     * @param clazz
     *            实体类型
     * @param paramMap
     *            参数.
     * @param <T>
     *            泛型
     * @return List<T> 返回类型
     */
    <T> List<T> findByNativeSql(final String nativeSql, final Class<T> clazz, final Map<String, Object> paramMap);

    /**
     * findByNativeSql(根据原生SQL查询对象列表)
     * 
     * @Title: findByNativeSql
     * @param nativeSql
     *            sql语句
     * @param paramMap
     *            参数.
     * @param <T>
     *            泛型,(数组,或Object,根据数据库类型)
     * @return List<Object[]> 返回类型
     */
    <T> List<T> findByNativeSql(String nativeSql, Map<String, Object> paramMap);
    /**
     * findMapByNativeSql(根据原生SQL查询对象列表)
     * 
     * @Title: findMapByNativeSql
     * @param nativeSql
     *            sql语句
     * @param paramMap
     *            参数.
     * @param <T>
     *            泛型,(数组,或Object,根据数据库类型)
     * @return List<Object[]> 返回类型
     */
    <T> List<T> findMapByNativeSql(String nativeSql, Map<String, Object> paramMap);

    /**
     * 按HQL分页查询.
     * 
     * @param hql
     *            hql语句.
     * @param paramMap
     *            参数.
     * @param page
     *            分页参数.不支持其中的orderBy参数.
     * @param pageSize
     *            分页参数.
     * @param <T>
     *            泛型
     * @return Pagination<T> 分页查询结果, 附带结果列表及所有查询时的参数.
     */
    <T> Pagination<T> listByNativeSql(final String hql, final Map<String, Object> paramMap, int page, int pageSize);

    /**
     * 按HQL查询对象列表.
     * 
     * @param hql
     *            hql语句.
     * @param paramMap
     *            参数.
     * @param <T>
     *            泛型
     * @return List<T> 结果列表
     */
    <T> List<T> find(final String hql, final Map<String, Object> paramMap);

    /**
     * 按HQL分页查询.
     * 
     * @param hql
     *            hql语句.
     * @param paramMap
     *            参数.
     * @param page
     *            分页参数.不支持其中的orderBy参数.
     * @param pageSize
     *            分页参数.
     * @param <T>
     *            泛型
     * @return Pagination<T> 分页查询结果, 附带结果列表及所有查询时的参数.
     */
    <T> Pagination<T> list(final String hql, final Map<String, Object> paramMap, int page, int pageSize);

    /**
     * 按聚合的HQL分页查询.
     * 
     * @param hql
     *            hql语句.
     * @param paramMap
     *            参数.
     * @param page
     *            分页参数.不支持其中的orderBy参数.
     * @param pageSize
     *            分页参数.
     * @param <T>
     *            泛型
     * @return Pagination<T> 分页查询结果, 附带结果列表及所有查询时的参数.
     */
    <T> Pagination<T> getAggregateList(final String hql, final Map<String, Object> paramMap, int page, int pageSize);

    /**
     * 按聚合的NativeSql分页查询.
     * 
     * @param nativeSql
     *            native sql
     * @param paramMap
     *            参数
     * @param page
     *            分页参数.不支持其中的orderBy参数.
     * @param pageSize
     *            分页参数.
     * @param <T>
     *            泛型
     * @return Pagination<T> 分页查询结果, 附带结果列表及所有分页属性.
     */
    <T> Pagination<T> getNativeAggregateList(final String nativeSql, final Map<String, Object> paramMap, int page,
            int pageSize);

    /**
     * 按HQL查询唯一对象.
     * 
     * @param hql
     *            hql语句
     * @param paramMap
     *            参数.
     * @param <T>
     *            泛型
     * @return 查询结果
     */
    <T> T findUnique(final String hql, final Map<String, Object> paramMap);

    /**
     * 执行HQL进行批量修改/删除操作.
     * 
     * @param hql
     *            hql语句
     * @param paramMap
     *            参数.
     * @return 更新记录数.
     */
    int batchExecute(final String hql, final Map<String, Object> paramMap);

    /**
     * 执行count查询获得本次Hql查询所能获得的对象总数.
     * 
     * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
     * 
     * @param hql
     *            hql语句
     * @param values
     *            数组参数map
     * @return 查询结果记录数量
     */
    int count(final String hql, final Map<String, Object> values);

    /**
     * 获取所有的实体列表
     * 
     * @param clazz
     *            实体类class
     * @param <T>
     *            泛型
     * @return 实体列表
     */
    <T> List<T> loadAll(Class<T> clazz);

    /**
     * doJdbcSql(执行jdbcSQL语句)
     * 
     * @Title: doJdbcSql
     * @param work
     *            参数
     */
    void doJdbcSql(Work work);

    /**
     * 限制结果位置的查找
     * 
     * @param <T>
     *            泛型
     * @param hql
     *            查询语句
     * @param paramMap
     *            参数
     * @param start
     *            开始,从0开始
     * @param max
     *            几条记录
     * @return {@link List<T>} 结果集
     */
    <T> List<T> find(String hql, Map<String, Object> paramMap, int start, int max);

    /**
     * 执行sql
     * 
     * @param sql
     *            sql语句
     * @param paramMap
     *            参数,可以为null
     * @return 影响的记录数
     */
    int executeSqlUpdate(String sql, Map<String, Object> paramMap);
    
    /**
     * 限制结果位置的查找
     * 
     * @param <T>
     *            泛型
     * @param nativeSql
     *            查询语句
     * @param paramMap
     *            参数
     * @param start
     *            开始,从0开始
     * @param max
     *            几条记录
     * @return {@link List<T>} 结果集
     */
    <T> List<T> findByNativeSql(final String nativeSql, final Map<String, Object> paramMap, int start,
            int max);
    
    /**
      * countByNativeSql(原生sql统计结果集数量)
      *
      * @Title: countByNativeSql
      * @param nativeSql sql语句
      * @param paramMap 参数
      * @return
      * @return int    查询结果集数量
      */
    int countByNativeSql(String nativeSql, Map<String, Object> paramMap);

    /**
     * findByNativeSql
     * @param <T> 泛型
     * @param nativeSql nativeSql
     * @param paramMap paramMap
     * @param clazz clazz  数据传输to类，非实体entity
     * @return List
     */
    <T> List<T> findByNativeSql(String nativeSql, Map<String, Object> paramMap,
			Class<T> clazz);
    
    /**
     * 按SQL查询唯一对象.
     * 
     * @param sql
     *            sql语句
     * @param paramMap
     *            参数.
     * @param <T>
     *            泛型
     * @return 查询结果
     */
    <T> T findNativeUnique(final String sql, final Map<String, Object> paramMap);
}
