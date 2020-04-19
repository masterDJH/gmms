package cn.djh.gmms.service;

import cn.djh.gmms.query.BaseQuery;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * 所有的service的父接口,保证每个service都有基本的CRUD方法
 */
public interface IBaseService<T, ID extends Serializable> {

	// 添加或更新对象
	void save(T t);

	// 在JPA中,要求主键必需实现Serializable接口
	void delete(ID id);

	// 查询指定类的所有对象
	List<T> findAll();

	// 查询一个对象
	T findOne(ID id);

	// 根据查询条件(query对象)查询数据
	List<T> queryAll(BaseQuery baseQuery);

	// 根据查询条件(query对象)查询分页数据
	Page<T> queryPage(BaseQuery baseQuery);

	// 根据JPQL进行查询
	List findByJPQL(String jpql, Object... params);
}
