package com.buseni.ecoledimanche.core.web;

import java.io.Serializable;

public class GroupeAnnuelSearch implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String anneeScolaire;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAnneeScolaire() {
		return anneeScolaire;
	}
	public void setAnneeScolaire(String anneeScolaire) {
		this.anneeScolaire = anneeScolaire;
	}
	
	

}
