package com.sora.pojo;

import java.util.List;

public class EasyUiResult {

	
	private Long total;
	
	private List<?> rows;
	
	

	public EasyUiResult(Long total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
	
	
}
