package com.buseni.ecoledimanche.account.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ecoledimanche.account.domain.PasswordResetToken;
import com.buseni.ecoledimanche.account.domain.Privilege;
import com.buseni.ecoledimanche.account.domain.Role;
import com.buseni.ecoledimanche.account.domain.UserAccount;
import com.buseni.ecoledimanche.account.domain.VerificationToken;
import com.buseni.ecoledimanche.account.repo.PasswordResetTokenRepo;
import com.buseni.ecoledimanche.account.repo.RoleRepository;
import com.buseni.ecoledimanche.account.repo.UserAccountRepository;
import com.buseni.ecoledimanche.account.repo.VerificationTokenRepo;
import com.buseni.ecoledimanche.exception.BusinessException;
import com.buseni.ecoledimanche.exception.CustomError;
import com.buseni.ecoledimanche.exception.CustomErrorBuilder;

@Service
@Transactional(readOnly=true)
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private PasswordResetTokenRepo passwordTokenRepo;

	@Autowired
	private VerificationTokenRepo verificationTokenRepo;

	@Autowired
	private LoginAttemptService loginAttemptService;

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	@Transactional
	public UserAccount create(UserAccount account) throws BusinessException {
		if (account == null) {
			throw new NullPointerException("Account must not be null");
		}
		if (emailExist(account.getEmail())) {
			CustomErrorBuilder ceb = new CustomErrorBuilder(
					"error.account.emailexists");
			CustomError ce = ceb.field("email")
					.errorArgs(new String[] { account.getEmail() }).buid();
			throw new BusinessException(ce);

		}
		return userAccountRepository.save(account);

	}

	@Override
	@Transactional
	public UserAccount update(UserAccount account) {
		if (account == null) {
			throw new NullPointerException("Account must not be null");
		}
		return userAccountRepository.save(account);

	}

	private boolean emailExist(String email) {
		UserAccount user = userAccountRepository.findByEmail(email);
		if (user != null) {
			return true;
		}
		return false;
	}

	// API

	@Override
	public UserDetails loadUserByUsername(final String email)
			throws UsernameNotFoundException {
		/*String ip = request.getRemoteAddr();
		if (loginAttemptService.isBlocked(ip)) {
			throw new RuntimeException("blocked");
		}*/

		try {
			final UserAccount user = userAccountRepository.findByEmail(email);
			if (user == null) {
				throw new UsernameNotFoundException(
						"No user found with username: " + email);
			}

			return new User(user.getEmail(), user.getPassword(),
					user.isEnabled(), true, true, true,
					getAuthorities(user.getRoles()));
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	// UTIL

	public final Collection<? extends GrantedAuthority> getAuthorities(
			final Collection<Role> roles) {
		return getGrantedAuthorities(getPrivileges(roles));
	}

	private final List<String> getPrivileges(final Collection<Role> roles) {
		final List<String> privileges = new ArrayList<String>();
		//final List<Privilege> collection = new ArrayList<Privilege>();
		for (Role role : roles) {
			privileges.add(role.getName());
			for ( Privilege item : role.getPrivileges()) {
				privileges.add(item.getName());
			}
			//collection.addAll(role.getPrivileges());
		}
		/*for (final Privilege item : collection) {
			privileges.add(item.getName());
		}*/
		return privileges;
	}

	private final List<GrantedAuthority> getGrantedAuthorities(
			final List<String> privileges) {
		final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (final String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}
	


	@Override
	@Transactional
	public void createPasswordResetTokenForUser(final UserAccount account,
			final String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token,
				account);
		passwordTokenRepo.save(myToken);
	}

	@Override
	public PasswordResetToken getPasswordResetToken(final String token) {
		return passwordTokenRepo.findByToken(token);
	}

	@Override
	public UserAccount getUserByPasswordResetToken(final String token) {
		return passwordTokenRepo.findByToken(token).getUserAccount();
	}

	@Override
	@Transactional
	public void changeUserPassword(final UserAccount user, final String password) {
		if (password == null || user == null) {
			throw new NullPointerException("Null password");
		}
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(password));
		userAccountRepository.save(user);
	}

	/**
	 * 
	 * @param verificationToken
	 * @return
	 */
	@Override
	public UserAccount getUserByVerificationToken(final String verificationToken) {
		if (null == verificationToken) {
			throw new NullPointerException("verification token is null");
		}
		return verificationTokenRepo.findByToken(verificationToken)
				.getUserAccount();

	}

	@Override
	public VerificationToken getVerificationToken(final String VerificationToken) {
		if (null == VerificationToken) {
			throw new NullPointerException("verificationtoken is null");
		}
		return verificationTokenRepo.findByToken(VerificationToken);
	}

	@Override
	@Transactional
	public void createVerificationTokenForUser(final UserAccount user,
			final String token) {
		if (null == user || token == null) {
			throw new NullPointerException("UserAccount or toke are null");
		}
		final VerificationToken myToken = new VerificationToken(token, user);
		verificationTokenRepo.save(myToken);
	}

	@Override
	@Transactional
	public VerificationToken generateNewVerificationToken(	final String existingVerificationToken) {
		if (null == existingVerificationToken) {
			throw new NullPointerException("existingverificationtoken is null");
		}
		VerificationToken vToken = verificationTokenRepo
				.findByToken(existingVerificationToken);
		vToken.updateToken(UUID.randomUUID().toString());
		vToken = verificationTokenRepo.save(vToken);
		return vToken;
	}

	/**
	 * 
	 * @param userEmail
	 * @return
	 */
	@Override
	public UserAccount findByUsername(String userEmail) {
		if (userEmail == null) {
			throw new NullPointerException("Null password");
		}
		return userAccountRepository.findByEmail(userEmail);
	}

	@Override
	@Transactional
	public UserAccount addOrUpdate(UserAccount account) throws BusinessException {
		//business control 
		if(account == null){
			throw new NullPointerException();
		}
		BusinessException be = new BusinessException();
		if(StringUtils.isEmpty(account.getFirstName())){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.moniteurs.field.null");			
			CustomError  ce = ceb.field("firstName").errorArgs(new String[] { "firstName" }).buid();
			be.addError(ce);
		
		}
		if(StringUtils.isEmpty(account.getLastName())){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.moniteurs.field.null");			
			CustomError  ce = ceb.field("lastName").errorArgs(new String[] { "lastName" }).buid();
			be.addError(ce);
		
		}
		if(StringUtils.isEmpty(account.getEmail())){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.moniteurs.field.null");			
			CustomError  ce = ceb.field("email").errorArgs(new String[] { "email" }).buid();
			be.addError(ce);
		
		}
		if(StringUtils.isEmpty(account.getPhoneNumber())){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.moniteur.field.null");			
			CustomError  ce = ceb.field("phoneNumber").errorArgs(new String[] { "phoneNumber" }).buid();
			be.addError(ce);
		
		}
		if(account.getGroupe() == null || account.getGroupe().getId() == null){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.moniteurs.field.null");			
			CustomError  ce = ceb.field("groupe").errorArgs(new String[] { "Groupe" }).buid();
			be.addError(ce);
		
		}
		if(CollectionUtils.isEmpty(account.getListRoles())){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.moniteurs.field.null");			
			CustomError  ce = ceb.field("listRoles").errorArgs(new String[] { "Roles" }).buid();
			be.addError(ce);
		}
		
		if (account.getId()== null && emailExist(account.getEmail())) {
			CustomErrorBuilder ceb = new CustomErrorBuilder(
					"error.account.emailexists");
			CustomError ce = ceb.field("email")
					.errorArgs(new String[] { account.getEmail() }).buid();
			be.addError(ce);

		}
		if(CollectionUtils.isNotEmpty(be.getErrors())){					
			throw be;
		}
		
		PasswordEncoder encoder = new BCryptPasswordEncoder();		
		account.setPassword(encoder.encode(account.getPassword()));
		
		if(account.getId() == null) {
			for (String roleName : account.getListRoles()) {
				Role role  =  roleRepository.findByName(roleName);
				if(role != null){
					account.getRoles().add(role);
				}
			}
			return userAccountRepository.save(account);
			
		}else {
			UserAccount  dbUserAccount  =  userAccountRepository.findById(account.getId()).get();
			//Changes in roles
			List<Role> adminRoles = dbUserAccount.getRoles();
			if (adminRoles.size() != account.getListRoles().size()) {
				//Add new roles
				if(account.getListRoles().size() > adminRoles.size()){
					for (String roleName : account.getListRoles()) {					
						if (!containsRole(adminRoles, roleName)) {
							Role role = roleRepository.findByName(roleName);
							dbUserAccount.getRoles().add(role);
						}
					}
					//remove of roles
				}else if(account.getListRoles().size() < adminRoles.size()){
					List<Role> rolesToRemove = new ArrayList<Role>();
					for (Role adminRole : adminRoles) {
						for (String roleName : account.getListRoles()) {
							if(!adminRole.getName().equals(roleName)){
								rolesToRemove.add(adminRole);							
							}
						}
					}
					dbUserAccount.getRoles().removeAll(rolesToRemove);
				}
			}
			dbUserAccount.setEstCoequipier(account.getEstCoequipier());
			dbUserAccount.setEnabled(account.getEnabled());
			dbUserAccount.setEstReferent(account.getEstReferent());
			dbUserAccount.setFirstName(account.getFirstName());
			dbUserAccount.setLastName(account.getLastName());
			dbUserAccount.setBirthDate(account.getBirthDate());
			return userAccountRepository.save(dbUserAccount);
		}
		
	}

	/*
	 * 
	 */
	private boolean containsRole(List<Role> roles, String roleName){
		for(Role role : roles){
			if(role.getName().equals(roleName)){
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public Page<UserAccount> findAll(Pageable pageable) {
		if(pageable == null){
			return new PageImpl<>(userAccountRepository.findAll());
		}
		return userAccountRepository.findAll(pageable);
	}

	@Override
	public UserAccount findById(Integer id) {
		if(id != null){
			Optional<UserAccount> o = userAccountRepository.findById(id);
			if(o.isPresent()){
				return o.get();
			}
		}
		return null;
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		if( id != null){
			userAccountRepository.deleteById(id);
		}
		
	}
}
