package lyn.ssh.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lyn.ssh.dao.BaseDaoI;
/**
 * 
 * @ClassName: BaseDaoImpl 
 * @Description: 基础的dao层,用于定义公共的操作 
 * @author A18ccms a18ccms_gmail_com 
 * @date 2016-8-10 下午4:52:22 
 * @param <T>
 */
@Repository("baseDao")
@Transactional
public class BaseDaoImpl<T> implements BaseDaoI<T> {

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * 
	 * @Title: getCurrentSession 
	 * @Description: 获取session用于操作数据库 
	 * @param @return 设定文件 
	 * @return Session 返回类型 
	 * @throws
	 */
	private Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}
	/*
	 * (非 Javadoc) 
	 * <p>Title:save</p> 
	 * <p>Description: 传入一个对象,添加记录到数据库</p> 
	 * @param o
	 * @return 
	 * @see lyn.dao.BaseDaoI#save(java.lang.Object)
	 */
	@Override
	public Serializable save(T o) {
		return this.getCurrentSession().save(o);
	}

	/*
	 * (非 Javadoc) 
	 * <p>Title:get</p> 
	 * <p>Description: 传入吴传参的hql语句,返回一条记录</p> 
	 * @param hql
	 * @return 
	 * @see lyn.dao.BaseDaoI#get(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T get(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public T get(String hql, Object[] params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				q.setParameter(i, params[i]);
			}
		}
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}*/

	/*
	 * (非 Javadoc) 
	 * <p>Title:get</p> 
	 * <p>Description: 传入带参数的hql语句,返回一条记录</p> 
	 * @param hql
	 * @param params
	 * @return 
	 * @see lyn.dao.BaseDaoI#get(java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T get(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	/*
	 * (非 Javadoc) 
	 * <p>Title:delete</p> 
	 * <p>Description: 删除传入的对象</p> 
	 * @param o 
	 * @see lyn.dao.BaseDaoI#delete(java.lang.Object)
	 */
	@Override
	public void delete(T o) {
		this.getCurrentSession().delete(o);
	}

	/*
	 * (非 Javadoc) 
	 * <p>Title:update</p> 
	 * <p>Description: 更新传入的对象</p> 
	 * @param o 
	 * @see lyn.dao.BaseDaoI#update(java.lang.Object)
	 */
	@Override
	public void update(T o) {
		this.getCurrentSession().update(o);
	}

	/*
	 * (非 Javadoc) 
	 * <p>Title:saveOrUpdate</p> 
	 * <p>Description: 保存或更新传入对象</p> 
	 * @param o 
	 * @see lyn.dao.BaseDaoI#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(T o) {
		this.getCurrentSession().saveOrUpdate(o);
	}

	/*
	 * (非 Javadoc) 
	 * <p>Title:find</p> 
	 * <p>Description: 传入无传参的hql语句,返回对应的List集合</p> 
	 * @param hql
	 * @return 
	 * @see lyn.dao.BaseDaoI#find(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.list();
	}

	/*
	 * (非 Javadoc) 
	 * <p>Title:find</p> 
	 * <p>Description: 传入带参的hql语句,返回对应的List集合</p> 
	 * @param hql
	 * @param params
	 * @return 
	 * @see lyn.dao.BaseDaoI#find(java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}

	/*
	 * (非 Javadoc) 
	 * <p>Title:find</p> 
	 * <p>Description: 传入带参的hql和分页参数,返回对应的List集合</p> 
	 * @param hql
	 * @param params
	 * @param page
	 * @param rows
	 * @return 
	 * @see lyn.dao.BaseDaoI#find(java.lang.String, java.util.Map, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	/*
	 * (非 Javadoc) 
	 * <p>Title:find</p> 
	 * <p>Description: 传入无参的hql语句和分页参数,返回对应的List集合</p> 
	 * @param hql
	 * @param page
	 * @param rows
	 * @return 
	 * @see lyn.dao.BaseDaoI#find(java.lang.String, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String hql, int page, int rows) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	/*
	 * (非 Javadoc) 
	 * <p>Title:count</p> 
	 * <p>Description: 传入无参的hql语句,返回记录数</p> 
	 * @param hql
	 * @return 
	 * @see lyn.dao.BaseDaoI#count(java.lang.String)
	 */
	@Override
	public Long count(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return (Long) q.uniqueResult();
	}

	/*
	 * (非 Javadoc) 
	 * <p>Title:count</p> 
	 * <p>Description: 传入带参的hql语句,返回记录数</p> 
	 * @param hql
	 * @param params
	 * @return 
	 * @see lyn.dao.BaseDaoI#count(java.lang.String, java.util.Map)
	 */
	@Override
	public Long count(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if(params!=null && params.size()>0){
			for(String key : params.keySet()){
				q.setParameter(key, params.get(key));
			}
		}
		return (Long) q.uniqueResult();
	}

}
