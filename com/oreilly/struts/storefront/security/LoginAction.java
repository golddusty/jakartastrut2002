package com.oreilly.struts.storefront.security;

import java.util.Locale;
import javax.servlet.http.*;
import javax.servlet.ServletContext;
import org.apache.struts.action.*;
import org.apache.struts.util.MessageResources;
import com.oreilly.struts.storefront.customer.view.UserView;
import com.oreilly.struts.storefront.framework.exceptions.*;
import com.oreilly.struts.storefront.framework.UserContainer;
import com.oreilly.struts.storefront.framework.StorefrontBaseAction;
import com.oreilly.struts.storefront.framework.util.IConstants;
import com.oreilly.struts.storefront.service.IStorefrontService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * Implements the logic to authenticate a user for the storefront application.
 */
public class LoginAction extends StorefrontBaseAction {
  protected static Log log = LogFactory.getLog( StorefrontBaseAction.class );
  /**
   * Called by the controller when the a user attempts to login to the
   * storefront application.
   */
  public ActionForward execute( ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response )
  throws Exception{

    // Get the user's login name and password. They should have already
    // validated by the ActionForm.
    String email = ((LoginForm)form).getEmail();
    String password = ((LoginForm)form).getPassword();

    // Obtain the ServletContext
    ServletContext context = getServlet().getServletContext();

    // Login through the security service
    IStorefrontService serviceImpl = getStorefrontService();

    String errorMsg = null;
    UserView userView = null;
    try{
      // Attempt to authenticate the user
      userView = serviceImpl.authenticate(email, password);
    }catch( InvalidLoginException ex ){
      ActionErrors errors = new ActionErrors();
      ActionError newError = new ActionError("error.security.invalidlogin");
      errors.add( ActionErrors.GLOBAL_ERROR, newError );
      saveErrors( request, errors );
      return mapping.findForward( IConstants.FAILURE_KEY );
    }catch( ExpiredPasswordException ex ){
      ActionErrors errors = new ActionErrors();
      ActionError newError = new ActionError("error.security.passwordexpired");
      errors.add( ActionErrors.GLOBAL_ERROR, newError );
      saveErrors( request, errors );
      return mapping.findForward( IConstants.FAILURE_KEY );
    }catch (AccountLockedException ex){
      ActionErrors errors = new ActionErrors();
      ActionError newError = new ActionError("error.security.accountlocked" );
      errors.add( ActionErrors.GLOBAL_ERROR, newError );
      saveErrors( request, errors );
      return mapping.findForward( IConstants.FAILURE_KEY );
    }



    UserContainer existingContainer = null;
    HttpSession session = request.getSession(false);
    if ( session != null ){
      existingContainer = getUserContainer(request);
      session.invalidate();
    }else{
      existingContainer = new UserContainer();
    }

    // Create a new session for the user
    session = request.getSession(true);
    existingContainer.setUserView(userView);
    session.setAttribute(IConstants.USER_CONTAINER_KEY, existingContainer);

    return mapping.findForward(IConstants.SUCCESS_KEY);
  }
}