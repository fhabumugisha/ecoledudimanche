package com.buseni.ecoledimanche.account.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.buseni.ecoledimanche.account.domain.PasswordResetToken;
import com.buseni.ecoledimanche.account.domain.UserAccount;
import com.buseni.ecoledimanche.account.domain.VerificationToken;
import com.buseni.ecoledimanche.exception.BusinessException;



public interface UserAccountService extends UserDetailsService {


	UserAccount create(UserAccount account) throws BusinessException;


	UserAccount update(UserAccount account) ;


	void createPasswordResetTokenForUser(final UserAccount account, final String token) ;   


	PasswordResetToken getPasswordResetToken(final String token) ;


	UserAccount getUserByPasswordResetToken(final String token) ;


	void changeUserPassword(final UserAccount user, final String password) ;

	UserAccount getUserByVerificationToken(final String verificationToken) ;


	VerificationToken getVerificationToken(final String VerificationToken);


	void createVerificationTokenForUser(final UserAccount user, final String token);


	VerificationToken generateNewVerificationToken(final String existingVerificationToken) ;

	UserAccount findByUsername(String userEmail) ;
}
