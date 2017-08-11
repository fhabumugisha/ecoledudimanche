package com.buseni.ecoledimanche.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ecoledimanche.core.domain.Planning;
import com.buseni.ecoledimanche.core.web.EleveSearch;
import com.buseni.ecoledimanche.exception.BusinessException;

public interface PlanningService {
	
	/**
	 * 
	 * @param planning
	 * @return 
	 * @throws BusinessException 
	 */
	Planning addOrUpdate(Planning planning) throws BusinessException ;
	
	/**
	 * 
	 * @param planning
	 * @return
	 * @throws BusinessException 
	 */
	Planning update(Planning planning) throws BusinessException;
	/**
	 * 
	 * @param id
	 * @return
	 */
	Planning findById(Integer id);
	
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Planning> findAll(Pageable pageable) ;
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Planning> search(EleveSearch seach ,Pageable pageable) ;
	
	/**
	 * 
	 * @param activeFlag
	 * @param pageable
	 * @return
	 */
	Page<Planning> findByEnabled(boolean enabled, Pageable pageable);
	
	
	/**
	 * 
	 * @param id
	 */
	void delete(Integer id);
	
	

	/**
	 * 
	 * @param planning
	 */
	void delete(Planning planning);

}
