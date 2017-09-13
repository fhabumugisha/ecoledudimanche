package com.buseni.ecoledimanche.account.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ecoledimanche.account.domain.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {

	

  UserAccount findByEmail(String email);
  
  List<UserAccount> findByEnabledTrue();
 
}