package com.buseni.ecoledimanche.core.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.buseni.ecoledimanche.account.domain.UserAccount;

@Entity
@Table(name = "planning")
@AttributeOverride(name = "id", column = @Column(name = "planning_id",   nullable = false))
public class Planning extends BaseEntityAudit {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	private Date date;
	
	@OneToMany
	private Set<UserAccount> moniteurs =  new HashSet<>();
	
	@Lob
	@Column(length=500)
	private String commentaire;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<UserAccount> getMoniteurs() {
		return moniteurs;
	}

	public void setMoniteurs(Set<UserAccount> moniteurs) {
		this.moniteurs = moniteurs;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	@Override
	public String toString() {
		return "Planning [date=" + date + ", moniteurs=" + moniteurs + ", commentaire=" + commentaire + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((commentaire == null) ? 0 : commentaire.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((moniteurs == null) ? 0 : moniteurs.hashCode());
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
		Planning other = (Planning) obj;
		if (commentaire == null) {
			if (other.commentaire != null)
				return false;
		} else if (!commentaire.equals(other.commentaire))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (moniteurs == null) {
			if (other.moniteurs != null)
				return false;
		} else if (!moniteurs.equals(other.moniteurs))
			return false;
		return true;
	}
	
	

}
