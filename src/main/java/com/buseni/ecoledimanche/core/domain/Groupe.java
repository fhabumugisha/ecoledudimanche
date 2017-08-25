package com.buseni.ecoledimanche.core.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.buseni.ecoledimanche.account.domain.UserAccount;

@Entity
@Table(name = "groupe")
@AttributeOverride(name = "id", column = @Column(name = "groupe_id",   nullable = false))

public class Groupe  extends BaseEntityAudit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	@Lob
	@Column(length=500)
	private String description;
	
	@NotEmpty
	@Column(name="annee_scolaire")
	private String anneeScolaire;
	
	@OneToMany(mappedBy="groupe")
	private Set<Eleve> eleves = new HashSet<>();
	
	@OneToMany(mappedBy="groupe")
	private Set<UserAccount> moniteurs =  new HashSet<>();
	
	@OneToMany(mappedBy="groupe")
	private Set<Lecon> lecons = new HashSet<>();

	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Groupe [name=" + name + ", description=" + description + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Groupe other = (Groupe) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public String getAnneeScolaire() {
		return anneeScolaire;
	}
	public void setAnneeScolaire(String anneeScolaire) {
		this.anneeScolaire = anneeScolaire;
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
	public Set<Lecon> getLecons() {
		return lecons;
	}
	public void setLecons(Set<Lecon> lecons) {
		this.lecons = lecons;
	}
	

}
