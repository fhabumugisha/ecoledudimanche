package com.buseni.ecoledimanche.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ecoledimanche.core.domain.Groupe;
import com.buseni.ecoledimanche.exception.BusinessException;

public interface GroupeService {
	
	/**
	 * 
	 * @param groupe
	 * @return 
	 * @throws BusinessException 
	 */
	Groupe addOrUpdate(Groupe groupe) throws BusinessException ;
	
	/**
	 * 
	 * @param groupe
	 * @return
	 * @throws BusinessException 
	 */
	Groupe update(Groupe groupe) throws BusinessException;
	/**
	 * 
	 * @param id
	 * @return
	 */
	Groupe findById(Integer id);
	
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Groupe> findAll(Pageable pageable) ;
	
	
	
	/**
	 * 
	 * @param enabled
	 * @param pageable
	 * @return
	 */
	Page<Groupe> findByEnabled(boolean enabled, Pageable pageable);
	
	
	/**
	 * 
	 * @param id
	 */
	void delete(Integer id);
	
	

	/**
	 * 
	 * @param Groupe
	 */
	void delete(Groupe groupe);

}
