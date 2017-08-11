package com.buseni.ecoledimanche.account.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ecoledimanche.account.domain.Role;


public interface RoleRepository extends JpaRepository<Role, Integer> {
     Role findByName(String name);

     void delete(Role role);
}