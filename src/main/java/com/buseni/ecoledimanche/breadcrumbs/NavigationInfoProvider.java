package com.buseni.ecoledimanche.breadcrumbs;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * UserAccount: yfliu
 * Date: 12/18/12
 * Time: 8:38 PM
 * To change this template use File | Settings | File Templates.
 */
public interface NavigationInfoProvider {
    String getUrl(HttpSession session);
    String getName(HttpSession session);
}
