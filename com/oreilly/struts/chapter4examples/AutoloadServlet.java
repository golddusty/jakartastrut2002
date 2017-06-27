package com.oreilly.struts.chapter4examples;

import java.io.File;
import javax.servlet.ServletException;
import org.apache.struts.action.ActionServlet;

public class AutoloadServlet extends ActionServlet {
  private long lastModifiedTime = 0;
  private AutoloadThread thread = null;

  public AutoloadServlet() {
  }

  public void init() throws ServletException {
    super.init();
  }

  protected void timeToCheck(){
    if ( hasFileBeenUpdated() ){

    }
  }

  protected boolean hasFileBeenUpdated(){
    if ( getLastModifiedTime() > lastModifiedTime ){
      System.out.println( "File has been modified, reload..." );
      return true;
    }else{
      return false;
    }
  }

  protected long getLastModifiedTime(){
    String path = getServletContext().getRealPath( "struts-config.xml" );
    System.out.println(  path );
    File file = new File( path );
    return file.lastModified();
  }
}