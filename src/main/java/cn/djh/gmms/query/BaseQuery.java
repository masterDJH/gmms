package cn.djh.gmms.query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;


/**
 * 父类:
 *   1.提供一些公共属性及方法(省一些代码)
 *   2.对子类形成相应的规范
 *   3.增加以后代码的扩展性
 */
public abstract class BaseQuery {

	//当前的页数
	private Integer currentPage = 1;
	//一页的数据条数
	private Integer pageSize = 10;
	//排序类型 默认降序 (true:DESC 降序 false:ASC 升序)
	private Boolean orderType = false;
	//排序字段 默认 id 排序 (以解决用户管理初次查询的数据混乱问题)
	private String orderName = "id";

	//创建排序对象
	public Sort createSort(){
		if(StringUtils.isNotBlank(orderName)){// orderName 非空时,才排序
			// 设置 排序类型、排序字段
			Sort sort = new Sort(orderType ? Sort.Direction.DESC:Sort.Direction.ASC,orderName);
			return sort;
		}
		return null;
	}

	// 统一一下该接口的实现类的方法名称
	public abstract Specification createSpec();

	public Integer getCurrentPage() {
		return currentPage;
	}
	//获取符合分页习惯的当前页数（从 0 开始）
	public Integer getJpaPage() {
		return currentPage-1;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	// 适应EasyUI组件的分页起点参数名page
	public void setPage(Integer page) {
		this.currentPage = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	// 适应EasyUI组件的分页大小参数名rows
	public void setRows(Integer rows) {
		this.pageSize = rows;
	}

	public Boolean getOrderType() {
		return orderType;
	}

	public void setOrderType(Boolean orderType) {
		this.orderType = orderType;
	}

	// 适应EasyUI组件的排序参数名order
	public void setOrder(String order) {
		if (order.equals("desc")){
			this.orderType = true;
		}else if (order.equals("asc")){
			this.orderType = false;
		}

	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	// 适应EasyUI组件的排序参数名sort
	public void setSort(String sort) {
		this.orderName = sort;
	}

	@Override
	public String toString() {
		return "BaseQuery{" +
				"currentPage=" + currentPage +
				", pageSize=" + pageSize +
				", orderType=" + orderType +
				", orderName='" + orderName + '\'' +
				'}';
	}
}
