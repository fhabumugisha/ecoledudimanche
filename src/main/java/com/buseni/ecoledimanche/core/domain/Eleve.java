package com.buseni.ecoledimanche.core.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "eleve")
@AttributeOverride(name = "id", column = @Column(name = "eleve_id",   nullable = false))

public class Eleve extends BaseEntityAudit implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String nom;
	@NotNull
	private String prenom;
	private Integer age;
	private String classe;
	private String contact;
		
	@ManyToOne
	@JoinColumn(name="groupe_annuel_id")
	private GroupeAnnuel groupeAnnuel;
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	@Override
	public String toString() {
		return "Eleve [nom=" + nom + ", prenom=" + prenom + ", age=" + age + ", classe=" + classe + ", contact="
				+ contact + ", groupeAnnuel=" + groupeAnnuel + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((classe == null) ? 0 : classe.hashCode());
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
		result = prime * result + ((groupeAnnuel == null) ? 0 : groupeAnnuel.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Eleve other = (Eleve) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (classe == null) {
			if (other.classe != null)
				return false;
		} else if (!classe.equals(other.classe))
			return false;
		if (contact == null) {
			if (other.contact != null)
				return false;
		} else if (!contact.equals(other.contact))
			return false;
		if (groupeAnnuel == null) {
			if (other.groupeAnnuel != null)
				return false;
		} else if (!groupeAnnuel.equals(other.groupeAnnuel))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		return true;
	}
	public GroupeAnnuel getGroupeAnnuel() {
		return groupeAnnuel;
	}
	public void setGroupeAnnuel(GroupeAnnuel groupeAnnuel) {
		this.groupeAnnuel = groupeAnnuel;
	}
	
	
	

}
