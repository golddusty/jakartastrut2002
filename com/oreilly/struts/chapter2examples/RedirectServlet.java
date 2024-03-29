package com.oreilly.struts.chapter2examples;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

/**
 * Perform a redirect for the page "redirect.jsp"
 */
public class RedirectServlet extends HttpServlet {
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
    throws ServletException,IOException {
    redirect(request, response);
  }
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
    throws ServletException,IOException {
    redirect(request, response);
  }

  /**
   * Set a few URL parameters and objects for the request to see what happens
   * to them during a redirect.
   */
  protected void redirect(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException,IOException {
      log("A request arrived for " + req.getServletPath());
      req.setAttribute("firstName", "John");
      req.setAttribute("lastName", "Doe");
      String contextPath = req.getContextPath();
      String redirectStr = contextPath + "/result.jsp?username=foo&password=bar";
      log("redirecting to " + redirectStr);

      // Always call the encodeRedirectURL method when perfoming a redirect
      resp.sendRedirect(resp.encodeRedirectURL(redirectStr));
  }
}
