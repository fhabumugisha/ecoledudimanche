package com.buseni.ecoledimanche.core.web;

import java.io.Serializable;

public class LeconSearch implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer idTheme;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIdTheme() {
		return idTheme;
	}
	public void setIdTheme(Integer idTheme) {
		this.idTheme = idTheme;
	}
	
	

}
