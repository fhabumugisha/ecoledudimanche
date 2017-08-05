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

import com.buseni.ecoledimanche.core.domain.ThemeLecon;
import com.buseni.ecoledimanche.core.repo.ThemeLeconRepo;
import com.buseni.ecoledimanche.core.service.ThemeLeconService;
import com.buseni.ecoledimanche.exception.BusinessException;
import com.buseni.ecoledimanche.exception.CustomError;
import com.buseni.ecoledimanche.exception.CustomErrorBuilder;

/**
 * @author habumugisha
 *
 */
@Service
@Transactional(readOnly=true)
public class ThemeLeconServiceImpl implements ThemeLeconService {

	private ThemeLeconRepo themeLeconRepo;

	public ThemeLeconServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.ThemeLeconService#addOrUpdate(com.buseni.ecoledimanche.core.domain.ThemeLecon)
	 */
	@Override
	@Transactional
	public ThemeLecon addOrUpdate(ThemeLecon themeLecon) throws BusinessException {
		//business control 
		if(themeLecon == null){
			throw new NullPointerException();
		}
		if(StringUtils.isEmpty(themeLecon.getName())){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.themelecon.name.null");			
			CustomError  ce = ceb.field("name").buid();
			throw new BusinessException(ce);
		}
		return themeLeconRepo.save(themeLecon);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.ThemeLeconService#update(com.buseni.ecoledimanche.core.domain.ThemeLecon)
	 */
	@Override
	@Transactional
	public ThemeLecon update(ThemeLecon themeLecon) throws BusinessException {
		//business control 
		if(themeLecon == null){
			throw new NullPointerException();
		}
		if(StringUtils.isEmpty(themeLecon.getName())){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.themelecon.name.null");			
			CustomError  ce = ceb.field("name").buid();
			throw new BusinessException(ce);
		}
		return themeLeconRepo.save(themeLecon);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.ThemeLeconService#findById(java.lang.Integer)
	 */
	@Override
	public ThemeLecon findById(Integer id) {
		if(id != null){
			Optional<ThemeLecon> o = themeLeconRepo.findById(id);
			if(o.isPresent()){
				return o.get();
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.ThemeLeconService#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<ThemeLecon> findAll(Pageable pageable) {
		if(pageable == null){
			return new PageImpl<>(themeLeconRepo.findAll());
		}
		PageRequest pr =  PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize());
		return themeLeconRepo.findAll(pr);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.ThemeLeconService#findByEnabled(boolean, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<ThemeLecon> findByEnabled(boolean enabled, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.ThemeLeconService#delete(java.lang.Integer)
	 */
	@Override
	@Transactional
	public void delete(Integer id) {
		if(id != null){
			themeLeconRepo.deleteById(id);
		}

	}

	/* (non-Javadoc)
	 * @see com.buseni.ecoledimanche.core.service.ThemeLeconService#delete(com.buseni.ecoledimanche.core.domain.ThemeLecon)
	 */
	@Override
	@Transactional
	public void delete(ThemeLecon themeLecon) {
		if(themeLecon != null){
			themeLeconRepo.delete(themeLecon);
		}

	}

}
