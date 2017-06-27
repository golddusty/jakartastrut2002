package com.oreilly.struts.framework;

import javax.servlet.http.*;
import java.util.Locale;
import org.apache.struts.action.Action;
import org.apache.struts.action.RequestProcessor;

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
}
