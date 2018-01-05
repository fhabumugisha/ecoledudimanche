package com.buseni.ecoledimanche.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ecoledimanche.core.domain.ThemeLecon;
import com.buseni.ecoledimanche.exception.BusinessException;

public interface ThemeLeconService {
	/**
	 * 
	 * @param themeLecon
	 * @return 
	 * @throws BusinessException 
	 */
	ThemeLecon addOrUpdate(ThemeLecon themeLecon) throws BusinessException ;
	
	/**
	 * 
	 * @param themeLecon
	 * @return
	 * @throws BusinessException 
	 */
	ThemeLecon update(ThemeLecon themeLecon) throws BusinessException;
	/**
	 * 
	 * @param id
	 * @return
	 */
	ThemeLecon findById(Integer id);
	
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<ThemeLecon> findAll(Pageable pageable) ;
	
	
	
	/**
	 * 
	 * @param enabled
	 * @param pageable
	 * @return
	 */
	Page<ThemeLecon> findByEnabled(boolean enabled, Pageable pageable);
	
	
	/**
	 * 
	 * @param id
	 */
	void delete(Integer id);
	
	

	/**
	 * 
	 * @param ThemeLecon
	 */
	void delete(ThemeLecon themeLecon);

}
