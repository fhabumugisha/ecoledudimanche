package com.buseni.ecoledimanche.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ecoledimanche.core.domain.Lecon;
import com.buseni.ecoledimanche.core.web.LeconSearch;
import com.buseni.ecoledimanche.exception.BusinessException;

public interface LeconService {
	
	/**
	 * 
	 * @param lecon
	 * @return 
	 * @throws BusinessException 
	 */
	Lecon addOrUpdate(Lecon lecon) throws BusinessException ;
	
	/**
	 * 
	 * @param lecon
	 * @return
	 * @throws BusinessException 
	 */
	Lecon update(Lecon lecon) throws BusinessException;
	/**
	 * 
	 * @param id
	 * @return
	 */
	Lecon findById(Integer id);
	
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Lecon> findAll(Pageable pageable) ;
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Lecon> search(LeconSearch seach ,Pageable pageable) ;
	
	/**
	 * 
	 * @param activeFlag
	 * @param pageable
	 * @return
	 */
	Page<Lecon> findByEnabled(boolean enabled, Pageable pageable);
	
	
	/**
	 * 
	 * @param id
	 */
	void delete(Integer id);
	
	

	/**
	 * 
	 * @param lecon
	 */
	void delete(Lecon lecon);

}
