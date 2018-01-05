package com.buseni.ecoledimanche.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ecoledimanche.core.domain.Eleve;
import com.buseni.ecoledimanche.core.web.EleveSearch;
import com.buseni.ecoledimanche.exception.BusinessException;

public interface EleveService {
	
	/**
	 * 
	 * @param eleve
	 * @return 
	 * @throws BusinessException 
	 */
	Eleve addOrUpdate(Eleve eleve) throws BusinessException ;
	
	/**
	 * 
	 * @param eleve
	 * @return
	 * @throws BusinessException 
	 */
	Eleve update(Eleve eleve) throws BusinessException;
	/**
	 * 
	 * @param id
	 * @return
	 */
	Eleve findById(Integer id);
	
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Eleve> findAll(Pageable pageable) ;
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Eleve> search(EleveSearch seach ,Pageable pageable) ;
	
	/**
	 * 
	 * @param activeFlag
	 * @param pageable
	 * @return
	 */
	Page<Eleve> findByEnabled(boolean enabled, Pageable pageable);
	
	
	/**
	 * 
	 * @param id
	 */
	void delete(Integer id);
	
	

	/**
	 * 
	 * @param eleve
	 */
	void delete(Eleve eleve);

}
