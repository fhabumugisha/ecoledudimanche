package com.buseni.ecoledimanche.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ecoledimanche.core.domain.GroupeAnnuel;
import com.buseni.ecoledimanche.core.web.GroupeAnnuelSearch;
import com.buseni.ecoledimanche.exception.BusinessException;

public interface GroupeAnnuelService {
	
	/**
	 * 
	 * @param groupeAnnuel
	 * @return 
	 * @throws BusinessException 
	 */
	GroupeAnnuel addOrUpdate(GroupeAnnuel groupeAnnuel) throws BusinessException ;
	
	/**
	 * 
	 * @param groupeAnnuel
	 * @return
	 * @throws BusinessException 
	 */
	GroupeAnnuel update(GroupeAnnuel groupeAnnuel) throws BusinessException;
	/**
	 * 
	 * @param id
	 * @return
	 */
	GroupeAnnuel findById(Integer id);
	
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<GroupeAnnuel> findAll(Pageable pageable) ;
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<GroupeAnnuel> search(GroupeAnnuelSearch seach ,Pageable pageable) ;
	
	/**
	 * 
	 * @param enabled
	 * @param pageable
	 * @return
	 */
	Page<GroupeAnnuel> findByEnabled(boolean enabled, Pageable pageable);
	
	
	/**
	 * 
	 * @param id
	 */
	void delete(Integer id);
	
	

	/**
	 * 
	 * @param groupeAnnuel
	 */
	void delete(GroupeAnnuel groupeAnnuel);

}
