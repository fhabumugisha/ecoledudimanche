package com.buseni.ecoledimanche.account.service;

import java.util.List;

import com.buseni.ecoledimanche.account.domain.Role;

public interface RoleService {

	List<Role> findAll();
	
	List<Role> findAdminRoles();
}
