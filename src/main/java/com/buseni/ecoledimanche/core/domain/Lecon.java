package com.buseni.ecoledimanche.core.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;



import com.buseni.ecoledimanche.account.domain.UserAccount;

@Entity
@Table(name = "lecon")
@AttributeOverride(name = "id", column = @Column(name = "lecon_id",   nullable = false))
public class Lecon extends BaseEntityAudit implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Temporal(TemporalType.DATE)
	private Date date;
	
	@NotEmpty
	private String titre;
	
	@NotEmpty
	@Lob
	@Column(length=500)
	private String description;
	
	@Lob
	@Column(length=500)
	private String commentaire;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="theme_lecon_id")
	private ThemeLecon themeLecon;
	
	@OneToMany
	@JoinTable(name = "lecon_eleves", joinColumns = { @JoinColumn(name = "lecon_id") }, inverseJoinColumns = { @JoinColumn(name = "eleve_id") })
	private Set<Eleve> eleves = new HashSet<>();
	
	@OneToMany
	@JoinTable(name = "lecon_moniteurs", joinColumns = { @JoinColumn(name = "lecon_id") }, inverseJoinColumns = { @JoinColumn(name = "user_account_id") })
	private Set<UserAccount> moniteurs = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name="groupe_id")
	private Groupe groupe;
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
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
		return "Lecon [date=" + date + ", titre=" + titre + ", description=" + description + ", commentaires="
				+ commentaire + ", themeLecon=" + themeLecon + ", groupe=" + groupe + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((commentaire == null) ? 0 : commentaire.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((groupe == null) ? 0 : groupe.hashCode());
		result = prime * result + ((themeLecon == null) ? 0 : themeLecon.hashCode());
		result = prime * result + ((titre == null) ? 0 : titre.hashCode());
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
		Lecon other = (Lecon) obj;
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
		if (themeLecon == null) {
			if (other.themeLecon != null)
				return false;
		} else if (!themeLecon.equals(other.themeLecon))
			return false;
		if (titre == null) {
			if (other.titre != null)
				return false;
		} else if (!titre.equals(other.titre))
			return false;
		return true;
	}
	public ThemeLecon getThemeLecon() {
		return themeLecon;
	}
	public void setThemeLecon(ThemeLecon themeLecon) {
		this.themeLecon = themeLecon;
	}
	
	
}
