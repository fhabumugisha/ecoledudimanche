package com.buseni.ecoledimanche.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ecoledimanche.core.domain.Eleve;
import com.buseni.ecoledimanche.core.domain.Planning;

public interface PlanningRepo extends JpaRepository<Planning, Integer>{

}
