package com.buseni.ecoledimanche.core.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "annee_scolaire")
@Where(clause = "enabled <> 0")
@AttributeOverride(name = "id", column = @Column(name = "annee_scolaire_id",   nullable = false))
public class AnneeScolaire extends BaseEntityAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotEmpty
	private String annee;
	public String getAnnee() {
		return annee;
	}
	public void setAnnee(String annee) {
		this.annee = annee;
	}
	@Override
	public String toString() {
		return "AnneeScolaire [annee=" + annee + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((annee == null) ? 0 : annee.hashCode());
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
		AnneeScolaire other = (AnneeScolaire) obj;
		if (annee == null) {
			if (other.annee != null)
				return false;
		} else if (!annee.equals(other.annee))
			return false;
		return true;
	}
	
}
