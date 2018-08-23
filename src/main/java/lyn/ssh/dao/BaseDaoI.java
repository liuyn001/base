package lyn.ssh.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDaoI<T>  {

	public Serializable save(T o) throws Exception;

	public void delete(T o) throws Exception;

	public void update(T o) throws Exception;

	public void saveOrUpdate(T o) throws Exception;

	public T get(String hql);

	// public T get(String hql, Object[] params);

	public T get(String hql, Map<String, Object> params) throws Exception;

	public List<T> find(String hql) throws Exception;

	public List<T> find(String hql, Map<String, Object> params) throws Exception;

	public List<T> find(String hql, int page, int rows) throws Exception;

	public List<T> find(String hql, Map<String, Object> params, int page, int rows) throws Exception;

	public Long count(String hql) throws Exception;

	public Long count(String hql, Map<String, Object> params) throws Exception;

}
