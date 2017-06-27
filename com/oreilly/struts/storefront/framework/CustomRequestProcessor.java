package com.oreilly.struts.storefront.framework;

import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import java.util.Locale;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.RequestProcessor;
import org.apache.struts.config.ForwardConfig;

/**
 * A customized RequestProcessor that checks the user's preferred Locale
 * from the request each time. If a Locale is not in the session or
 * the one in the session doesn't match the request, the Locale in the
 * request is set to the session.
 */
public class CustomRequestProcessor extends RequestProcessor {

    protected void processLocale(HttpServletRequest request,
                                 HttpServletResponse response) {

      // Are we configured to select the Locale automatically?
      if (!appConfig.getControllerConfig().getLocale()) {
        return;
      }

      // Get the Locale (if any) that is stored in the user's session
      HttpSession session = request.getSession();
      Locale sessionLocale = (Locale)session.getAttribute(Action.LOCALE_KEY);

      // Get the user's preferred Locale from the request
      Locale requestLocale = request.getLocale();

      // If was never a Locale in the session or it has changed, set it
      if (sessionLocale == null ||  (sessionLocale != requestLocale) ){
        if (log.isDebugEnabled()) {
          log.debug(" Setting user locale '" + requestLocale + "'");
        }
        // Set the new Locale into the user's session
        session.setAttribute( Action.LOCALE_KEY, requestLocale );
      }
    }

  protected boolean processPreprocess( HttpServletRequest request,
                                      HttpServletResponse response){

    boolean continueProcessing = true;

    // Get the name of the remote host and log it
    String remoteHost = request.getRemoteHost();
    log.info( "Request from host: " + remoteHost );

    // Make sure the host is from one that you expect
    if ( remoteHost == null || !remoteHost.startsWith( "127.") ){
       // Not the localhost, so don't allow the host to access the site
       continueProcessing = false;

       ForwardConfig config = appConfig.findForwardConfig("Unauthorized");
       try{
         response.sendRedirect( config.getPath() );
       }catch( Exception ex ){
         log.error( "Problem sending redirect from processPreprocess()" );
       }
    }

    return continueProcessing;
  }
}