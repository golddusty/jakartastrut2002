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

    // Attempt to authenticate the user
    UserView userView = serviceImpl.authenticate(email, password);

    UserContainer existingContainer = getUserContainer(request);
    existingContainer.setUserView(userView);
    System.out.println( existingContainer.getUserView() );
    return mapping.findForward(IConstants.SUCCESS_KEY);
  }
}