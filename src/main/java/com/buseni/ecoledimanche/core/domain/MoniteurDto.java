package com.buseni.ecoledimanche.core.domain;

import java.io.Serializable;

public class MoniteurDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	
	public MoniteurDto() {
		// TODO Auto-generated constructor stub
	}
	
	public MoniteurDto(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
