package com.buseni.ecoledimanche.account.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ecoledimanche.account.domain.UserAccount;
import com.buseni.ecoledimanche.account.domain.VerificationToken;

public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Integer>{

	VerificationToken findByToken(String verificationToken);

	VerificationToken findByUserAccount(UserAccount userAccount);

}
