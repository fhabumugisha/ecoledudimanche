/**
 * 
 */
package com.buseni.ecoledimanche.core.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ecoledimanche.core.domain.Groupe;
import com.buseni.ecoledimanche.core.repo.GroupeRepo;
import com.buseni.ecoledimanche.core.service.GroupeService;
import com.buseni.ecoledimanche.exception.BusinessException;
import com.buseni.ecoledimanche.exception.CustomError;
import com.buseni.ecoledimanche.exception.CustomErrorBuilder;

/**
 * @author habumugisha
 *
 */
@Service
@Transactional(readOnly=true)
public class GroupeServiceImpl implements GroupeService {
	
	private GroupeRepo groupeRepo;
	
	public GroupeServiceImpl(GroupeRepo groupeRepo) {
		this.groupeRepo = groupeRepo;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.GroupeService#addOrUpdate(com.buseni.ecoledimanche.core.domain.Groupe)
	 */
	@Override
	@Transactional
	public Groupe addOrUpdate(Groupe groupe) throws BusinessException {
		//business control 
				if(groupe == null){
					throw new NullPointerException();
				}
				if(StringUtils.isEmpty(groupe.getName())){
					CustomErrorBuilder ceb =  new CustomErrorBuilder("error.groupe.name.null");			
					CustomError  ce = ceb.field("name").buid();
					throw new BusinessException(ce);
				}
				return groupeRepo.save(groupe);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.GroupeService#update(com.buseni.ecoledimanche.core.domain.Groupe)
	 */
	@Override
	@Transactional
	public Groupe update(Groupe groupe) throws BusinessException {
		//business control 
		if(groupe == null){
			throw new NullPointerException();
		}
		if(StringUtils.isEmpty(groupe.getName())){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.groupe.name.null");			
			CustomError  ce = ceb.field("name").buid();
			throw new BusinessException(ce);
		}
		return groupeRepo.save(groupe);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.GroupeService#findById(java.lang.Integer)
	 */
	@Override
	public Groupe findById(Integer id) {
		if(id != null){
			Optional<Groupe> o = groupeRepo.findById(id);
			if(o.isPresent()){
				return o.get();
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.GroupeService#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Groupe> findAll(Pageable pageable) {
		if(pageable == null){
			return new PageImpl<>(groupeRepo.findAll());
		}
		//PageRequest pr =  PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize());
		return groupeRepo.findAll(pageable);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.GroupeService#findByEnabled(boolean, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Groupe> findByEnabled(boolean enabled, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.GroupeService#delete(java.lang.Integer)
	 */
	@Override
	@Transactional
	public void delete(Integer id) {
		if( id != null){
			groupeRepo.deleteById(id);
		}

	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.GroupeService#delete(com.buseni.ecoledimanche.core.domain.Groupe)
	 */
	@Override
	@Transactional
	public void delete(Groupe groupe) {
		if( groupe != null){
			groupeRepo.delete(groupe);
		}

	}

}
