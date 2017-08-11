package com.buseni.ecoledimanche.account.web;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.buseni.ecoledimanche.account.domain.UserAccount;

@SuppressWarnings("serial")
public class ResetPasswordEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final UserAccount account;
    private String token;
    public ResetPasswordEvent(UserAccount account, Locale locale, String appUrl, String token) {
        super(account);
        this.account = account;
        this.locale = locale;
        this.appUrl = appUrl;
        this.token =  token;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public UserAccount getUserAccount() {
        return account;
    }
    
    public String getToken(){
    	return token;
    }
}