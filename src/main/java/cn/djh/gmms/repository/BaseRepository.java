package cn.djh.gmms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * 公共父Repository接口
 * 	便于使用公共父接口,调用实现类的方法
 */
@NoRepositoryBean //不让Spring为其创建 bean 对象
public interface BaseRepository<T,ID extends Serializable> extends JpaRepository<T,ID>,JpaSpecificationExecutor<T> {
}
