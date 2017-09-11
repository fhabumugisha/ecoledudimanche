package com.buseni.ecoledimanche.core.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ecoledimanche.core.domain.Groupe;

public interface GroupeRepo extends JpaRepository<Groupe, Integer>{ 
	
	List<Groupe> findByEnabledTrue();

}
