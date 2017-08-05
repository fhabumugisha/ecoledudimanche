package com.buseni.ecoledimanche.account.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.buseni.ecoledimanche.core.domain.BaseEntityAudit;
import com.buseni.ecoledimanche.core.domain.GroupeAnnuel;

@Entity
@Table(name = "user_account")
//@AttributeOverride(name = "id", column = @Column(name = "user_account_id",   nullable = false))
public  class UserAccount extends BaseEntityAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 840767790155118289L;
	

	@Column(unique = true)
	@Email
	@NotEmpty(message = "{error.user.requiredfield.email}")
	private String email;

	@NotEmpty(message = "{error.user.requiredfield.password}")
	private String password;

	@Column(name ="est_coequipier")
	private  Boolean estCoequipier;

	

	@Column(name ="token_expired")
	private Boolean tokenExpired;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	

	@Column(name="phone_number")
	private String phoneNumber;
	
	@Temporal(TemporalType.DATE)
	@Column(name="birth_date")
	private Date birthDate;

	@ManyToMany
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Role> roles = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name="groupe_annuel_id")
	private GroupeAnnuel groupeAnnuel;
	
	
	public UserAccount() {

	}
	
	public UserAccount(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	


	public Boolean isTokenExpired() {
		return tokenExpired;
	}

	public void setTokenExpired(Boolean tokenExpired) {
		this.tokenExpired = tokenExpired;
	}
	

	@Override
	public String toString() {
		return "UserAccount [email=" + email + ", password=" + password + ", estCoequipier=" + estCoequipier
				+ ", tokenExpired=" + tokenExpired + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", phoneNumber=" + phoneNumber + ", birthDate=" + birthDate + ", roles=" + roles + ", groupeAnnuel="
				+ groupeAnnuel + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (estCoequipier ? 1231 : 1237);
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((groupeAnnuel == null) ? 0 : groupeAnnuel.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + (tokenExpired ? 1231 : 1237);
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
		UserAccount other = (UserAccount) obj;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (estCoequipier != other.estCoequipier)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (groupeAnnuel == null) {
			if (other.groupeAnnuel != null)
				return false;
		} else if (!groupeAnnuel.equals(other.groupeAnnuel))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (tokenExpired != other.tokenExpired)
			return false;
		return true;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Boolean isEstCoequipier() {
		return estCoequipier;
	}

	public void setEstCoequipier(Boolean estCoequipier) {
		this.estCoequipier = estCoequipier;
	}

	public GroupeAnnuel getGroupeAnnuel() {
		return groupeAnnuel;
	}

	public void setGroupeAnnuel(GroupeAnnuel groupeAnnuel) {
		this.groupeAnnuel = groupeAnnuel;
	}
	



}
