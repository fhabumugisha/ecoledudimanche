package com.buseni.ecoledimanche.core.web;

import java.beans.PropertyEditorSupport;

import com.buseni.ecoledimanche.account.domain.UserAccount;
import com.buseni.ecoledimanche.account.repo.UserAccountRepository;

public class MoniteurEditor extends PropertyEditorSupport {
	
	private	UserAccountRepository moniteurRepo;
	
	public MoniteurEditor(UserAccountRepository moniteurRepo) {
		this.moniteurRepo = moniteurRepo;
	}
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Integer id = Integer.parseInt(text);
        setValue(moniteurRepo.findById(id).get());
	}
	
	@Override
	public String getAsText() {
		UserAccount moniteur = (UserAccount)getValue();
		return Integer.toString(moniteur.getId());

	}

}
