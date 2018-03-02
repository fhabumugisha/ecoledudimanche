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
import org.springframework.util.CollectionUtils;

import com.buseni.ecoledimanche.core.domain.Eleve;
import com.buseni.ecoledimanche.core.repo.EleveRepo;
import com.buseni.ecoledimanche.core.service.EleveService;
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
public class EleveServiceImpl implements EleveService {
	
	private EleveRepo eleveRepo;
	
	public EleveServiceImpl(EleveRepo eleveRepo) {
		this.eleveRepo = eleveRepo;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.EleveService#addOrUpdate(com.buseni.ecoledimanche.core.domain.Eleve)
	 */
	@Override
	@Transactional
	public Eleve addOrUpdate(Eleve eleve) throws BusinessException {
		//business control 
				if(eleve == null){
					throw new NullPointerException();
				}
				BusinessException be = new BusinessException();
//				if(StringUtils.isEmpty(eleve.getNom())){
//					CustomErrorBuilder ceb =  new CustomErrorBuilder("error.eleves.field.null");			
//					CustomError  ce = ceb.field("nom").errorArgs(new String[] { "Nom" }).buid();
//					be.addError(ce);
//				
//				}
				if(StringUtils.isEmpty(eleve.getPrenom())){
					CustomErrorBuilder ceb =  new CustomErrorBuilder("error.eleves.field.null");			
					CustomError  ce = ceb.field("prenom").errorArgs(new String[] { "Prenom" }).buid();
					be.addError(ce);
				
				}
				if(null == eleve.getAge()){
					CustomErrorBuilder ceb =  new CustomErrorBuilder("error.eleves.field.null");			
					CustomError  ce = ceb.field("age").errorArgs(new String[] { "Age" }).buid();
					be.addError(ce);
				
				}
				if(eleve.getGroupe() == null || eleve.getGroupe().getId() == null){
					CustomErrorBuilder ceb =  new CustomErrorBuilder("error.eleves.field.null");			
					CustomError  ce = ceb.field("groupe").errorArgs(new String[] { "Groupe" }).buid();
					be.addError(ce);
				
				}
				
				if(CollectionUtils.isEmpty(be.getErrors())){					
					return eleveRepo.save(eleve);
				}else{
					throw be;
				}
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.EleveService#update(com.buseni.ecoledimanche.core.domain.Eleve)
	 */
	@Override
	@Transactional
	public Eleve update(Eleve eleve) throws BusinessException {
		//business control 
		if(eleve == null){
			throw new NullPointerException();
		}
		BusinessException be = new BusinessException();
//		if(StringUtils.isEmpty(eleve.getNom())){
//			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.eleves.field.null");			
//			CustomError  ce = ceb.field("nom").buid();
//			be.addError(ce);
//		
//		}
		if(StringUtils.isEmpty(eleve.getPrenom())){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.eleves.field.null");			
			CustomError  ce = ceb.field("prenom").errorArgs(new String[] { "Prenom" }).buid();
			be.addError(ce);
		
		}
		if(null == eleve.getAge()){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.eleves.field.null");			
			CustomError  ce = ceb.field("age").errorArgs(new String[] { "Age" }).buid();
			be.addError(ce);
		
		}
		if(eleve.getGroupe() == null || eleve.getGroupe().getId() == null){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.eleves.field.null");			
			CustomError  ce = ceb.field("groupe").errorArgs(new String[] { "Groupe" }).buid();
			be.addError(ce);
		
		}
		
		if(CollectionUtils.isEmpty(be.getErrors())){					
			return eleveRepo.save(eleve);
		}else{
			throw be;
		}
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.EleveService#findById(java.lang.Integer)
	 */
	@Override
	public Eleve findById(Integer id) {
		if(id != null){
			Optional<Eleve> o = eleveRepo.findById(id);
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
	public Page<Eleve> findAll(Pageable pageable) {
		if(pageable == null){
			return new PageImpl<>(eleveRepo.findAll());
		}
//		PageRequest pr =  PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize());
		return eleveRepo.findAll(pageable);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.EleveService#search(com.buseni.ecoledimanche.core.web.EleveSearch, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Eleve> search(EleveSearch seach, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.EleveService#findByEnabled(boolean, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Eleve> findByEnabled(boolean enabled, Pageable pageable) {
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
			eleveRepo.deleteById(id);
		}

	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.EleveService#delete(com.buseni.ecoledimanche.core.domain.Eleve)
	 */
	@Override
	@Transactional
	public void delete(Eleve eleve) {
		if( eleve != null){
			eleveRepo.delete(eleve);
		}

	}

}
