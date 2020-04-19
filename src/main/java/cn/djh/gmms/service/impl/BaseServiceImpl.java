package cn.djh.gmms.service.impl;

import cn.djh.gmms.query.BaseQuery;
import cn.djh.gmms.repository.BaseRepository;
import cn.djh.gmms.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * 实现基本的 CRUD 功能
 *	使用abstract是为了让BaseServiceImpl不可以new出来,只能继承它来获得基本 CRUD 功能
 */
@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
public abstract class BaseServiceImpl<T, ID extends Serializable> implements IBaseService<T,ID> {

	// 加入泛型<T,ID>,以解决Spring注入的不知道注入哪个实现类问题
	@Autowired
	private BaseRepository<T,ID> baseRepository;
	@PersistenceContext
	private EntityManager entityManager;//获取事务管理器,以用AOP添加事务

	// 添加或更新对象
	@Override
	@Transactional// 添加、更新操作,必加事务
	public void save(T t) {
		baseRepository.save(t);
	}

	@Override
	@Transactional// 删除操作,必加事务
	public void delete(ID id) {
		baseRepository.delete(id);
	}

	@Override
	public List<T> findAll() {
		return baseRepository.findAll();
	}

	@Override
	public T findOne(ID id) {
		return baseRepository.findOne(id);
	}

	// 根据查询条件(query对象)查询数据
	@Override
	public List<T> queryAll(BaseQuery baseQuery) {
		// 拿到规则
		Specification spec = baseQuery.createSpec();
		// 按规则查询,并返回
		return baseRepository.findAll(spec);
	}

	// 根据查询条件(query对象)查询分页数据
	@Override
	public Page<T> queryPage(BaseQuery baseQuery) {
		// 拿到规则
		Specification spec = baseQuery.createSpec();
		// 拿到排序对象
		Sort sort = baseQuery.createSort();
		// 拿到分页对象
		Pageable pageable = new PageRequest(baseQuery.getJpaPage(),baseQuery.getPageSize(),sort);
		// 按规则查询,并返回
		return baseRepository.findAll(spec, pageable);
	}

	/**
	 *	根据JPQL进行查询
	 *	执行jpql,需要EntityManager对象
	 */
	@Override
	public List findByJPQL(String jpql,Object... params) {
		// 创建query对象
		Query query = entityManager.createQuery(jpql);
		// 循环设置JPQL语句参数
		for (int i=0;i<params.length;i++){
			// JPQL语句参数下标从 1 开始
			query.setParameter(i+1, params[i]);
		}
		return query.getResultList();
	}
}
