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

import com.buseni.ecoledimanche.core.domain.Lecon;
import com.buseni.ecoledimanche.core.repo.LeconRepo;
import com.buseni.ecoledimanche.core.service.LeconService;
import com.buseni.ecoledimanche.core.web.LeconSearch;
import com.buseni.ecoledimanche.exception.BusinessException;
import com.buseni.ecoledimanche.exception.CustomError;
import com.buseni.ecoledimanche.exception.CustomErrorBuilder;

/**
 * @author habumugisha
 *
 */
@Service
@Transactional(readOnly=true)
public class LeconServiceImpl implements LeconService {

	private LeconRepo leconRepo;

	public LeconServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.LeconService#addOrUpdate(com.buseni.ecoledimanche.core.domain.Lecon)
	 */
	@Override
	@Transactional
	public Lecon addOrUpdate(Lecon lecon) throws BusinessException {
		//business control 
		if(lecon == null){
			throw new NullPointerException();
		}
		if(StringUtils.isEmpty(lecon.getTitre())){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.lecon.titre.null");			
			CustomError  ce = ceb.field("titre").buid();
			throw new BusinessException(ce);
		}
		return leconRepo.save(lecon);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.LeconService#update(com.buseni.ecoledimanche.core.domain.Lecon)
	 */
	@Override
	@Transactional
	public Lecon update(Lecon lecon) throws BusinessException {
		//business control 
				if(lecon == null){
					throw new NullPointerException();
				}
				if(StringUtils.isEmpty(lecon.getTitre())){
					CustomErrorBuilder ceb =  new CustomErrorBuilder("error.lecon.name.null");			
					CustomError  ce = ceb.field("name").buid();
					throw new BusinessException(ce);
				}
				return leconRepo.save(lecon);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.LeconService#findById(java.lang.Integer)
	 */
	@Override
	public Lecon findById(Integer id) {
		if(id != null){
			Optional<Lecon> o = leconRepo.findById(id);
			if(o.isPresent()){
				return o.get();
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.LeconService#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Lecon> findAll(Pageable pageable) {
		if(pageable == null){
			return new PageImpl<>(leconRepo.findAll());
		}
//		PageRequest pr =  PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize());
		return leconRepo.findAll(pageable);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.LeconService#search(com.buseni.ecoledimanche.core.web.LeconSearch, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Lecon> search(LeconSearch seach, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.LeconService#findByEnabled(boolean, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Lecon> findByEnabled(boolean enabled, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.LeconService#delete(java.lang.Integer)
	 */
	@Override
	@Transactional
	public void delete(Integer id) {
		if( id != null){
			leconRepo.deleteById(id);
		}

	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.LeconService#delete(com.buseni.ecoledimanche.core.domain.Lecon)
	 */
	@Override
	@Transactional
	public void delete(Lecon lecon) {
		if(lecon != null){
			leconRepo.delete(lecon);
		}

	}

}
