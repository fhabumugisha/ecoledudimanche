/**
 * 
 */
package com.buseni.ecoledimanche.core.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.buseni.ecoledimanche.core.domain.GroupeAnnuel;
import com.buseni.ecoledimanche.core.repo.GroupeAnnuelRepo;
import com.buseni.ecoledimanche.core.service.GroupeAnnuelService;
import com.buseni.ecoledimanche.core.web.GroupeAnnuelSearch;
import com.buseni.ecoledimanche.exception.BusinessException;
import com.buseni.ecoledimanche.exception.CustomError;
import com.buseni.ecoledimanche.exception.CustomErrorBuilder;

/**
 * @author habumugisha
 *
 */
@Service
@Transactional(readOnly=true)
public class GroupeAnnuelServiceImpl implements GroupeAnnuelService {
	
	private GroupeAnnuelRepo groupeAnnuelRepo;
	
	public GroupeAnnuelServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.GroupeAnnuelService#addOrUpdate(com.buseni.ecoledimanche.core.domain.GroupeAnnuel)
	 */
	@Override
	@Transactional
	public GroupeAnnuel addOrUpdate(GroupeAnnuel groupeAnnuel) throws BusinessException {
		//business control 
		if(groupeAnnuel == null){
			throw new NullPointerException();
		}
		BusinessException be = new BusinessException();
		if(StringUtils.isEmpty(groupeAnnuel.getAnneeScolaire())){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.groupeannuel.annee.null");			
			CustomError  ce = ceb.field("anneeScolaire").buid();
			be.addError(ce);
		
		}
		if(groupeAnnuel.getGroupe() == null || groupeAnnuel.getGroupe().getId() == null){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.groupeannuel.groupe.null");			
			CustomError  ce = ceb.field("groupe").buid();
			be.addError(ce);
		
		}
		
		if(CollectionUtils.isEmpty(be.getErrors())){					
			return groupeAnnuelRepo.save(groupeAnnuel);
		}else{
			throw be;
		}
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.GroupeAnnuelService#update(com.buseni.ecoledimanche.core.domain.GroupeAnnuel)
	 */
	@Override
	@Transactional
	public GroupeAnnuel update(GroupeAnnuel groupeAnnuel) throws BusinessException {
		//business control 
				if(groupeAnnuel == null){
					throw new NullPointerException();
				}
				BusinessException be = new BusinessException();
				if(StringUtils.isEmpty(groupeAnnuel.getAnneeScolaire())){
					CustomErrorBuilder ceb =  new CustomErrorBuilder("error.groupeannuel.annee.null");			
					CustomError  ce = ceb.field("anneeScolaire").buid();
					be.addError(ce);
				
				}
				if(groupeAnnuel.getGroupe() == null || groupeAnnuel.getGroupe().getId() == null){
					CustomErrorBuilder ceb =  new CustomErrorBuilder("error.groupeannuel.groupe.null");			
					CustomError  ce = ceb.field("groupe").buid();
					be.addError(ce);
				
				}
				
				if(CollectionUtils.isEmpty(be.getErrors())){					
					return groupeAnnuelRepo.save(groupeAnnuel);
				}else{
					throw be;
				}
				
		
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.GroupeAnnuelService#findById(java.lang.Integer)
	 */
	@Override
	public GroupeAnnuel findById(Integer id) {
		if(id != null){
			Optional<GroupeAnnuel> o = groupeAnnuelRepo.findById(id);
			if(o.isPresent()){
				return o.get();
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.GroupeAnnuelService#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<GroupeAnnuel> findAll(Pageable pageable) {
		if(pageable == null){
			return new PageImpl<>(groupeAnnuelRepo.findAll());
		}
		PageRequest pr =  PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize());
		return groupeAnnuelRepo.findAll(pr);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.GroupeAnnuelService#search(com.buseni.ecoledimanche.core.web.GroupeAnnuelSearch, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<GroupeAnnuel> search(GroupeAnnuelSearch seach, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.GroupeAnnuelService#findByEnabled(boolean, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<GroupeAnnuel> findByEnabled(boolean enabled, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.GroupeAnnuelService#delete(java.lang.Integer)
	 */
	@Override
	@Transactional
	public void delete(Integer id) {
		if( id != null){
			groupeAnnuelRepo.deleteById(id);
		}

	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.GroupeAnnuelService#delete(com.buseni.ecoledimanche.core.domain.GroupeAnnuel)
	 */
	@Override
	@Transactional
	public void delete(GroupeAnnuel groupeAnnuel) {
		if( groupeAnnuel != null){
			groupeAnnuelRepo.delete(groupeAnnuel);
		}

	}

}
