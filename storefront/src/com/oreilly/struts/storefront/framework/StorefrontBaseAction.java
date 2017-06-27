package com.oreilly.struts.storefront.framework;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Iterator;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.oreilly.struts.storefront.framework.util.IConstants;
import com.oreilly.struts.storefront.framework.exceptions.*;
import com.oreilly.struts.storefront.service.IStorefrontService;
import com.oreilly.struts.storefront.service.IStorefrontServiceFactory;
/**
 * An abstract Action class that all Storefront action classes should
 * extend.
 */
abstract public class StorefrontBaseAction extends Action {
  Log log = LogFactory.getLog( this.getClass() );

  protected IStorefrontService getStorefrontService(){
    IStorefrontServiceFactory factory = (IStorefrontServiceFactory)getApplicationObject( IConstants.SERVICE_FACTORY_KEY );
    IStorefrontService service = null;

    try{
      service = factory.createService();
    }catch( Exception ex ){
      log.error( "Problem creating the Storefront Service", ex );
    }
    return service;
  }

  /**
   * Retrieve a session object based on the request and the attribute name.
   */
  protected Object getSessionObject(HttpServletRequest req,
                                    String attrName) {
    Object sessionObj = null;
    HttpSession session = req.getSession(false);
    if ( session != null ){
       sessionObj = session.getAttribute(attrName);
    }
    return sessionObj;
  }

  /**
   * Return the instance of the ApplicationContainer object.
   */
  protected ApplicationContainer getApplicationContainer() {
    return (ApplicationContainer)getApplicationObject(IConstants.APPLICATION_CONTAINER_KEY);
  }

  /**
   * Retrieve the UserContainer for the user tier to the request.
   */
  protected UserContainer getUserContainer(HttpServletRequest request) {

    UserContainer userContainer = (UserContainer)getSessionObject(request, IConstants.USER_CONTAINER_KEY);

    // Create a UserContainer for the user if it doesn't exist already
    if(userContainer == null) {
      userContainer = new UserContainer();
      userContainer.setLocale(request.getLocale());
      HttpSession session = request.getSession(true);
      session.setAttribute(IConstants.USER_CONTAINER_KEY, userContainer);
    }

    return userContainer;
  }

  /**
   * Retrieve an object from the application scope by its name. This is
   * a convience method.
   */
  protected Object getApplicationObject(String attrName) {
    return servlet.getServletContext().getAttribute(attrName);
  }

  public boolean isLoggedIn( HttpServletRequest request ){
    UserContainer container = getUserContainer(request);
    if ( container.getUserView() != null ){
       return true;
    }else{
      return false;
    }
  }
}
