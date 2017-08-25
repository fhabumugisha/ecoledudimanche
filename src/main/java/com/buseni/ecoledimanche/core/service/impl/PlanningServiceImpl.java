/**
 * 
 */
package com.buseni.ecoledimanche.core.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.buseni.ecoledimanche.core.domain.Planning;
import com.buseni.ecoledimanche.core.repo.PlanningRepo;
import com.buseni.ecoledimanche.core.service.PlanningService;
import com.buseni.ecoledimanche.core.web.EleveSearch;
import com.buseni.ecoledimanche.exception.BusinessException;
import com.buseni.ecoledimanche.exception.CustomError;
import com.buseni.ecoledimanche.exception.CustomErrorBuilder;

/**
 * @author habumugisha
 *
 */
@Service
@Transactional(readOnly=true)
public class PlanningServiceImpl implements PlanningService {
	
	private PlanningRepo planningRepo;
	
	public PlanningServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.EleveService#addOrUpdate(com.buseni.ecoledimanche.core.domain.Planning)
	 */
	@Override
	@Transactional
	public Planning addOrUpdate(Planning planning) throws BusinessException {
		//business control 
				if(planning == null){
					throw new NullPointerException();
				}
				BusinessException be = new BusinessException();
				if(null == planning.getDate()){
					CustomErrorBuilder ceb =  new CustomErrorBuilder("error.planning.date.null");			
					CustomError  ce = ceb.field("date").buid();
					be.addError(ce);
				
				}
				
				if(CollectionUtils.isEmpty(planning.getMoniteurs())){
					CustomErrorBuilder ceb =  new CustomErrorBuilder("error.planning.moniteurs.null");			
					CustomError  ce = ceb.field("moniteurs").buid();
					be.addError(ce);
				
				}
				
				
				if(CollectionUtils.isEmpty(be.getErrors())){					
					return planningRepo.save(planning);
				}else{
					throw be;
				}
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.EleveService#update(com.buseni.ecoledimanche.core.domain.Planning)
	 */
	@Override
	@Transactional
	public Planning update(Planning planning) throws BusinessException {
		//business control 
		if(planning == null){
			throw new NullPointerException();
		}
		BusinessException be = new BusinessException();
		if(null == planning.getDate()){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.planning.date.null");			
			CustomError  ce = ceb.field("date").buid();
			be.addError(ce);
		
		}
		
		if(CollectionUtils.isEmpty(planning.getMoniteurs())){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.planning.moniteurs.null");			
			CustomError  ce = ceb.field("moniteurs").buid();
			be.addError(ce);
		
		}
		
		if(CollectionUtils.isEmpty(be.getErrors())){					
			return planningRepo.save(planning);
		}else{
			throw be;
		}
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.EleveService#findById(java.lang.Integer)
	 */
	@Override
	public Planning findById(Integer id) {
		if(id != null){
			Optional<Planning> o = planningRepo.findById(id);
			if(o.isPresent()){
				return o.get();
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.EleveService#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Planning> findAll(Pageable pageable) {
		if(pageable == null){
			return new PageImpl<>(planningRepo.findAll());
		}
//		PageRequest pr =  PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize());
		return planningRepo.findAll(pageable);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.EleveService#search(com.buseni.ecoledimanche.core.web.EleveSearch, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Planning> search(EleveSearch seach, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.EleveService#findByEnabled(boolean, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Planning> findByEnabled(boolean enabled, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.EleveService#delete(java.lang.Integer)
	 */
	@Override
	@Transactional
	public void delete(Integer id) {
		if( id != null){
			planningRepo.deleteById(id);
		}

	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.EleveService#delete(com.buseni.ecoledimanche.core.domain.Planning)
	 */
	@Override
	@Transactional
	public void delete(Planning planning) {
		if( planning != null){
			planningRepo.delete(planning);
		}

	}

}
