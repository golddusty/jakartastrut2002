package com.oreilly.struts.chapter10examples;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.util.AppException;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ExceptionConfig;
import com.oreilly.struts.storefront.framework.exceptions.BaseException;
public class SpecialExceptionHandler extends ExceptionHandler {

  public ActionForward execute(Exception ex,
                                  ExceptionConfig config,
                                  ActionMapping mapping,
                                  ActionForm formInstance,
                                  HttpServletRequest request,
                                  HttpServletResponse response)
    throws ServletException {
    ActionForward forward = null;
    ActionError error = null;
    String property = null;

    /* Get the path for the forward either from the exception element
    * or from the input attribute.
    */
    String path = null;
    if(config.getPath() != null) {
      path = config.getPath();
    }
    else {
      path = mapping.getInput();
    }

    // Construct the forward object
    forward = new ActionForward(path);

    /* Figure out what type of exception has been thrown. The Struts
    * AppException is not being used in this example.
    */
    if(ex instanceof BaseException) {

      // This is the specialized behavior
      BaseException baseException = (BaseException)ex;
      String messageKey = baseException.getMessageKey();
      Object[] exArgs = baseException.getMessageArgs();
      if(exArgs != null && exArgs.length > 0) {

        // If there were args provided, use them in the ActionError
        error = new ActionError(messageKey, exArgs);
      }
      else {

        // Create an ActionError without any arguments
        error = new ActionError(messageKey);
      }
    }
    else {
      error = new ActionError(config.getKey());
      property = error.getKey();
    }

    // Store the ActionError into the proper scope

    // The storeException method is defined in the parent class
    storeException(request, property, error, forward, config.getScope());
    return forward;
  }
}
