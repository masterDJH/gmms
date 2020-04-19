package cn.djh.gmms.common;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 分页 工具类（可能会改动）
 */
public class UIPage {
	private List rows;
	private Long total;

	public UIPage(Page page) {
		this.total = page.getTotalElements();
		this.rows = page.getContent();
	}
	public UIPage(List page, Long count) {
		this.total = count;
		this.rows = page;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "UIPage{" +
				"rows=" + rows +
				", total=" + total +
				'}';
	}
}
