package com.buseni.ecoledimanche.core.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.buseni.ecoledimanche.account.domain.UserAccount;

@Entity
@Table(name = "groupe_annuel")
@AttributeOverride(name = "id", column = @Column(name = "groupe_annuel_id",   nullable = false))
public class GroupeAnnuel extends BaseEntityAudit implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="annee_scolaire")
	private String anneeScolaire;
	
	private String description;
	
	@OneToOne
	@JoinColumn(name="groupe_id")
	private Groupe groupe;
	
	@OneToMany(mappedBy="groupeAnnuel")
	private Set<Eleve> eleves = new HashSet<>();
	
	@OneToMany(mappedBy="groupeAnnuel")
	private Set<UserAccount> moniteurs =  new HashSet<>();
	
	@OneToMany(mappedBy="groupeAnnuel")
	private Set<Lecon> lecons = new HashSet<>();

	public String getAnneeScolaire() {
		return anneeScolaire;
	}

	public void setAnneeScolaire(String anneeScolaire) {
		this.anneeScolaire = anneeScolaire;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	public Set<Eleve> getEleves() {
		return eleves;
	}

	public void setEleves(Set<Eleve> eleves) {
		this.eleves = eleves;
	}

	public Set<UserAccount> getMoniteurs() {
		return moniteurs;
	}

	public void setMoniteurs(Set<UserAccount> moniteurs) {
		this.moniteurs = moniteurs;
	}

	@Override
	public String toString() {
		return "GroupeAnnuel [anneeScolaire=" + anneeScolaire + ", description=" + description + ", groupe=" + groupe
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((anneeScolaire == null) ? 0 : anneeScolaire.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((groupe == null) ? 0 : groupe.hashCode());
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
		GroupeAnnuel other = (GroupeAnnuel) obj;
		if (anneeScolaire == null) {
			if (other.anneeScolaire != null)
				return false;
		} else if (!anneeScolaire.equals(other.anneeScolaire))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (groupe == null) {
			if (other.groupe != null)
				return false;
		} else if (!groupe.equals(other.groupe))
			return false;
		return true;
	}
	
	

}
