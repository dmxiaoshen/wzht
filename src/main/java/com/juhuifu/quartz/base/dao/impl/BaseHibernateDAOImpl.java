package com.juhuifu.quartz.base.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.juhuifu.quartz.base.dao.BaseHibernateDAO;
import com.juhuifu.quartz.base.entity.BaseEntity;
import com.juhuifu.quartz.base.page.PageUtil;
import com.juhuifu.quartz.base.page.Pagination;

/**
 * 封装Hibernate原生API的DAO泛型基类.
 * 
 * 
 */
@Repository("baseHibernateDAO")
public class BaseHibernateDAOImpl implements BaseHibernateDAO {
    /**
     * the initial capacity StringBuffer初始化
     */
    private static final int INIT_CAPACITY = 128;
    private SessionFactory sessionFactory;
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void clear() {
        getSession().clear();
    }

    @Override
    public void flush() {
        getSession().flush();
    }

    @Override
    public void evict(Object object) {
        getSession().evict(object);
    }

    @Override
    public void add(Object entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().save(entity);
        logger.debug("save entity: {}", entity);
    }

    @Override
    public void save(BaseEntity entity) {
        Assert.notNull(entity, "entity不能为空");
        if (entity.getId() == null) {
            getSession().save(entity);
        } else {
            getSession().saveOrUpdate(entity);
        }
        logger.debug("save entity: {}", entity);
    }

    @Override
    public void saveOrUpdate(Object entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().saveOrUpdate(entity);
        logger.debug("save entity: {}", entity);
    }

    @Override
    public void delete(Object entity) {
        Assert.notNull(entity, "entity不能为空");
        getSession().delete(entity);
        logger.debug("delete entity: {}", entity);
    }

    @Override
    public void delete(Class<?> clazz, Long id) {
        Assert.notNull(id, "id不能为空");
        getSession().delete(getSession().load(clazz, id));
        logger.debug("delete entity ,id is {}", id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz, final Long id) {
        Assert.notNull(id, "id不能为空");
        return (T) getSession().get(clazz, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T load(Class<T> clazz, final Long id) {
        Assert.notNull(id, "id不能为空");
        return (T) getSession().load(clazz, id);
    }

    private void setQuery(Query query, final Map<String, Object> paramMap) {
        if (paramMap == null || paramMap.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            Object v = entry.getValue();
            if (v instanceof Object[]) {
                query.setParameterList(entry.getKey(), (Object[]) v);
            } else if (v instanceof Collection) {
                query.setParameterList(entry.getKey(), (Collection<?>) v);
            } else {
                query.setParameter(entry.getKey(), v);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> findByNativeSql(final String nativeSql,
    		final Class<T> clazz, final Map<String, Object> paramMap) {
        SQLQuery query = getSession().createSQLQuery(nativeSql).addEntity(clazz);
        setQuery(query, paramMap);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> findByNativeSql(String nativeSql, Map<String, Object> paramMap) {
        SQLQuery query = getSession().createSQLQuery(nativeSql);
        setQuery(query, paramMap);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> findMapByNativeSql(String nativeSql, Map<String, Object> paramMap) {
        SQLQuery query = getSession().createSQLQuery(nativeSql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        setQuery(query, paramMap);
        return query.list();
    }
    
    @Override
    public int countByNativeSql(String nativeSql, Map<String, Object> paramMap) {
        StringBuffer sb = new StringBuffer(INIT_CAPACITY).append("select count(1) from ").append(
                StringUtils.substringBeforeLast(StringUtils.substringAfter(nativeSql, "from"), "order "));
        SQLQuery query = getSession().createSQLQuery(sb.toString());
        setQuery(query, paramMap);
        return ((Number) query.uniqueResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> findByNativeSql(final String nativeSql, final Map<String, Object> paramMap, int start, int max) {
        SQLQuery query = getSession().createSQLQuery(nativeSql);
        setQuery(query, paramMap);
        return query.setFirstResult(start).setMaxResults(max).list();
    }

    @Override
    public int count(String hql, Map<String, Object> paramMap) {
        StringBuffer sb = new StringBuffer(INIT_CAPACITY).append("select count(*) from ").append(
                StringUtils.substringBeforeLast(StringUtils.substringAfter(hql, "from"), "order "));
        Query query = getSession().createQuery(sb.toString());
        setQuery(query, paramMap);
        return ((Long) query.uniqueResult()).intValue();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> find(final String hql, final Map<String, Object> paramMap) {
        Query query = getSession().createQuery(hql);
        setQuery(query, paramMap);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> find(final String hql, final Map<String, Object> paramMap, int start, int max) {
        Query query = getSession().createQuery(hql);
        setQuery(query, paramMap);
        return query.setFirstResult(start).setMaxResults(max).list();
    }

    @Override
    public <T> Pagination<T> list(final String hql, final Map<String, Object> paramMap, int page, int pageSize) {
        int record = count(hql, paramMap);
        int p = PageUtil.validatePage(record, page, pageSize);
        if (record == 0) {
            return new Pagination<T>(p, pageSize, record, new ArrayList<T>(0));
        }
        List<T> result = find(hql, paramMap, PageUtil.getStart(p, pageSize), pageSize);
        return new Pagination<T>(p, pageSize, record, result);
    }

    @Override
    public <T> T findUnique(final String hql, final Map<String, Object> paramMap) {
        List<T> list = find(hql, paramMap);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public int batchExecute(final String hql, final Map<String, Object> paramMap) {
        Query query = getSession().createQuery(hql);
        setQuery(query, paramMap);
        return query.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> loadAll(Class<T> clazz) {
        return (List<T>) getSession().createCriteria(clazz).list();
    }

    @Override
    public void doJdbcSql(Work work) {
        getSession().doWork(work);
    }

    @Override
    public <T> Pagination<T> listByNativeSql(String sql, Map<String, Object> paramMap, int page, int pageSize) {
        int record = countByNativeSql(sql, paramMap);
        int p = PageUtil.validatePage(record, page, pageSize);
        if (record == 0) {
            return new Pagination<T>(p, pageSize, record, new ArrayList<T>(0));
        }
        List<T> result = findByNativeSql(sql, paramMap, PageUtil.getStart(p, pageSize), pageSize);
        return new Pagination<T>(p, pageSize, record, result);
    }

    @Override
    public <T> Pagination<T> getAggregateList(String hql, Map<String, Object> paramMap, int page, int pageSize) {
        int record = getAggregateCount(hql, paramMap);
        int p = PageUtil.validatePage(record, page, pageSize);
        if (record == 0) {
            return new Pagination<T>(p, pageSize, record, new ArrayList<T>(0));
        }
        List<T> result = find(hql, paramMap, PageUtil.getStart(p, pageSize), pageSize);
        return new Pagination<T>(p, pageSize, record, result);
    }

    private int getAggregateCount(String hql, Map<String, Object> paramMap) {
        StringBuffer sb = new StringBuffer(INIT_CAPACITY).append("select count(*) from ").append(
                StringUtils.substringBeforeLast(StringUtils.substringAfter(hql, "from"), "order "));
        Query query = getSession().createQuery(sb.toString());
        setQuery(query, paramMap);
        return query.list().size();
    }

    @Override
    public int executeSqlUpdate(String sql, Map<String, Object> paramMap) {
        SQLQuery query = getSession().createSQLQuery(sql);
        if (paramMap != null) {
            setQuery(query, paramMap);
        }
        return query.executeUpdate();
    }

    @Override
    public <T> Pagination<T> getNativeAggregateList(final String nativeSql, final Map<String, Object> paramMap,
            int page, int pageSize) {
        int record = getNativeAggregateCount(nativeSql, paramMap);
        int p = PageUtil.validatePage(record, page, pageSize);
        if (record == 0) {
            return new Pagination<T>(p, pageSize, record, new ArrayList<T>(0));
        }
        List<T> result = findByNativeSql(nativeSql, paramMap, PageUtil.getStart(p, pageSize), pageSize);
        return new Pagination<T>(p, pageSize, record, result);
    }

    private int getNativeAggregateCount(String nativeSql, Map<String, Object> paramMap) {
        StringBuffer sb = new StringBuffer(INIT_CAPACITY).append("select count(1) as cut_0 from ").append(
                StringUtils.substringBeforeLast(StringUtils.substringAfter(nativeSql, "from"), "order "));
        SQLQuery query = getSession().createSQLQuery(sb.toString());
        setQuery(query, paramMap);
        return query.list().size();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> findByNativeSql(String nativeSql, Map<String, Object> paramMap, 
           Class<T> clazz) {
        SQLQuery query = (SQLQuery) getSession().createSQLQuery(nativeSql).
        		setResultTransformer(Transformers.aliasToBean(clazz));
        setQuery(query, paramMap);
        return query.list();
    }
    
    @Override
    public <T> T findNativeUnique(final String sql, final Map<String, Object> paramMap) {
        List<T> list = findByNativeSql(sql, paramMap);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
// /**
// * 查询记录总数
// *
// * @param hql
// * hql
// * @param values
// * 参数数组
// * @return 记录总数
// */
// @Override
// @Deprecated
// public int count(String hql, Object... values) {
// StringBuffer sb = new
// StringBuffer(INIT_CAPACITY).append("select count(*) from ").append(
// StringUtils.substringBefore(StringUtils.substringAfter(hql, "from"),
// "order"));
// Query query = getSession().createQuery(sb.toString());
// for (int i = 0, length = values.length; i < length; i++) {
// query.setParameter(i, values[i]);
// }
// return ((Long) query.uniqueResult()).intValue();
// }
//
// @Override
// @Deprecated
// @SuppressWarnings("unchecked")
// public <T> List<T> find(final String hql, final Object... values) {
// Query query = getSession().createQuery(hql);
// for (int i = 0, length = values.length; i < length; i++) {
// query.setParameter(i, values[i]);
// }
// return query.list();
// }
//
// @Override
// @Deprecated
// @SuppressWarnings("unchecked")
// public <T> List<T> findByNativeSql(final String hql, final Class<T> clazz,
// final Object... values) {
// SQLQuery query = getSession().createSQLQuery(hql).addEntity(clazz);
// for (int i = 0, length = values.length; i < length; i++) {
// query.setParameter(i, values[i]);
// }
// return query.list();
// }
// @Deprecated
// @SuppressWarnings("unchecked")
// private <T> List<T> find(final String hql, int start, int max, final
// Object... values) {
// Query query = getSession().createQuery(hql);
// for (int i = 0, length = values.length; i < length; i++) {
// query.setParameter(i, values[i]);
// }
// return query.setFirstResult(start).setMaxResults(max).list();
// }
// @Deprecated
// @Override
// public <T> Pagination<T> list(String hql, int page, int pageSize, Object...
// values) {
// int record = count(hql, values);
// int p = PageUtil.validatePage(record, page, pageSize);
// if (record == 0) {
// return new Pagination<T>(p, pageSize, record, new ArrayList<T>(0));
// }
// List<T> result = find(hql, PageUtil.getStart(p, pageSize), pageSize, values);
// return new Pagination<T>(p, pageSize, record, result);
// }
// /**
// * 通过creteria查询实体分页
// *
// * @param <T>
// * 泛型
// * @param clazz
// * 实体类class
// * @param filter
// * 查询参数filter
// * @param page
// * 页码
// * @param pageSize
// * pageSize
// * @return 实体分页
// */
// @Override
// public <T> Pagination<T> list(Class<T> clazz, QueryFilter filter, final int
// page, final int pageSize) {
// int record = count(clazz, filter);
// int p = PageUtil.validatePage(record, page, pageSize);
// if (record == 0) {
// return new Pagination<T>(p, pageSize, record, new ArrayList<T>(0));
// }
// List<T> result = find(clazz, filter, PageUtil.getStart(p, pageSize),
// pageSize);
// return new Pagination<T>(p, pageSize, record, result);
// }
// @SuppressWarnings("unchecked")
// private <T> List<T> find(Class<T> clazz, QueryFilter filter, Order order, int
// start, int max) {
// Criteria criteria = getSession().createCriteria(clazz);
// for (Criterion criterion : filter.getCriteria()) {
// criteria.add(criterion);
// }
// if (order != null) {
// criteria.addOrder(order);
// }
// return (List<T>) criteria.setFirstResult(start).setMaxResults(max).list();
// }
// /**
// * 分页排序查询
// *
// * @param <T>
// * 泛型
// * @param clazz
// * 实体类class
// * @param filter
// * 查询参数filter
// * @param order
// * 排序
// * @param page
// * 页码
// * @param pageSize
// * pageSize
// * @return 实体分页
// */
// @Override
// public <T> Pagination<T> list(Class<T> clazz, QueryFilter filter, Order
// order, final int page, final int pageSize) {
// int record = count(clazz, filter);
// int p = PageUtil.validatePage(record, page, pageSize);
// if (record == 0) {
// return new Pagination<T>(p, pageSize, record, new ArrayList<T>(0));
// }
// List<T> result = find(clazz, filter, order, PageUtil.getStart(p, pageSize),
// pageSize);
// return new Pagination<T>(p, pageSize, record, result);
// }
// /**
// * hql使用in查询
// *
// * @param <T>
// * 泛型
// * @param hql
// * hql
// * @param paramMap
// * 参数map
// * @return List<T> 实体列表
// */
// @SuppressWarnings("unchecked")
// @Override
// public <T> List<T> findByIn(String hql, Map<String, Object> paramMap) {
// Query query = getSession().createQuery(hql);
// for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
// Object v = entry.getValue();
// if (v instanceof Object[]) {
// query.setParameterList(entry.getKey(), (Object[]) v);
// } else if (v instanceof Collection) {
// query.setParameterList(entry.getKey(), (Collection<?>) v);
// } else {
// query.setParameter(entry.getKey(), v);
// }
// }
// return query.list();
// }
// @Override
// public <T> int count(Class<T> clazz, QueryFilter filter) {
// Criteria criteria = getSession().createCriteria(clazz);
// for (Criterion criterion : filter.getCriteria()) {
// criteria.add(criterion);
// }
// return ((Long)
// criteria.setProjection(Projections.projectionList().add(Projections.rowCount()))
// .uniqueResult()).intValue();
// }
// @Override
// @SuppressWarnings("unchecked")
// public <T> List<T> find(Class<T> clazz, QueryFilter filter) {
// Criteria criteria = getSession().createCriteria(clazz);
// for (Criterion criterion : filter.getCriteria()) {
// criteria.add(criterion);
// }
// return (List<T>) criteria.list();
// }
// @Override
// public int bulkExecute(String hql, Map<String, Object[]> paramMap) {
// Query query = getSession().createQuery(hql);
// for (Map.Entry<String, Object[]> entry : paramMap.entrySet()) {
// Object v = entry.getValue();
// if (v instanceof Object[]) {
// query.setParameterList(entry.getKey(), (Object[]) v);
// } else if (v instanceof Collection) {
// query.setParameterList(entry.getKey(), (Collection<?>) v);
// } else {
// query.setParameter(entry.getKey(), v);
// }
// }
// return query.executeUpdate();
// }
// @Deprecated
// @Override
// public int batchExecute(final String hql, final Object... values) {
// Query query = getSession().createQuery(hql);
// for (int i = 0, length = values.length; i < length; i++) {
// query.setParameter(i, values[i]);
// }
// return query.executeUpdate();
// }
// @Deprecated
// @Override
// public <T> T findUnique(final String hql, final Object... values) {
// List<T> list = find(hql, values);
// if (list.isEmpty()) {
// return null;
// } else {
// return list.get(0);
// }
// }
// @Override
// public <T> Object executeCriteria(CriteriaCallback<T> callback) {
// Assert.notNull(callback, "callback不能为空");
// Class<T> persistentClass = callback.getPersistentClass();
// Assert.notNull(persistentClass, "实体类不能为空");
// Criteria criteria = getSession().createCriteria(persistentClass);
// return callback.doCallback(criteria);
// }
// /**
// *
// * 通过creteria查询实体分页列表
// *
// * @param <T>
// * 泛型
// * @param clazz
// * 实体类class
// * @param filter
// * 查询参数filter
// * @param start
// * start
// * @param max
// * max
// * @return 实体分页列表
// */
// @SuppressWarnings("unchecked")
// public <T> List<T> find(Class<T> clazz, QueryFilter filter, int start, int
// max) {
// Criteria criteria = getSession().createCriteria(clazz);
// for (Criterion criterion : filter.getCriteria()) {
// criteria.add(criterion);
// }
// return (List<T>) criteria.setFirstResult(start).setMaxResults(max).list();
// }