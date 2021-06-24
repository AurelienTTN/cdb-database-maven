package com.excilys.cdb.servlets;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Page;

@Component
public class Session {

	
	private String orderBy;
	private String search;
	private Pageable pageable;
	private Page page;
	private int nbComputerFound;
	private String sort;
	
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page=page;
	}
	public Pageable getPageable() {
		return pageable;
	}
	public void setPageable(Pageable page) {
		this.pageable = page;
	}
	public int getNbComputerFound() {
		return nbComputerFound;
	}
	public void setNbComputerFound(int nbComputerFound) {
		this.nbComputerFound = nbComputerFound;
	}
	
	
	
}