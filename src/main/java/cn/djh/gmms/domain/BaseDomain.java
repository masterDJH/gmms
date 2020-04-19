package cn.djh.gmms.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 	在JPA里面表示用于映射的父类,不持久化到表,方便JPA识别此父类,加到子类中
 */
@MappedSuperclass
public class BaseDomain {
	@Id
	@GeneratedValue
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
