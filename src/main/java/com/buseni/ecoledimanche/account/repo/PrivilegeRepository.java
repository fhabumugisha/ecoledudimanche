package com.buseni.ecoledimanche.account.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ecoledimanche.account.domain.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {
     Privilege findByName(String name);

     void delete(Privilege privilege);
}