package com.buseni.ecoledimanche.account.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ecoledimanche.account.domain.PasswordResetToken;


public interface PasswordResetTokenRepo  extends JpaRepository<PasswordResetToken, Integer>{

	PasswordResetToken findByToken(String token);

}
