package com.sora.vo;

import java.util.List;

/*
 * 是EasyUI的DataGrid的专用结果
 */
public class DataGridResult {
	private Long total;
	private List<?> rows;

	public DataGridResult() {
		super();
	}

	public DataGridResult(Long total, List<?> rows) {
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
