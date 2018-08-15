package com.article.config.page;

import java.util.List;

public class DatatablesViewPage<T> {

	private List<T> aaData; // aaData 与datatales 加载的“dataSrc"对应
	private int iTotalDisplayRecords;
	private int iTotalRecords;

	public DatatablesViewPage() {

	}

	// get set方法 此处省略
	public List<T> getAaData() {
		return aaData;
	}

	public void setAaData(List<T> aaData) {
		this.aaData = aaData;
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

}