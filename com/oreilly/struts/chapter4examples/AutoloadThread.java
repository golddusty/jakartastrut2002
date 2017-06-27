package com.oreilly.struts.chapter4examples;

public class AutoloadThread extends Thread {
  private long sleepTimeInSecs;
  private AutoloadServlet servlet = null;

  public AutoloadThread( AutoloadServlet servlet, int sleepTimeInSecs ) {
    this.servlet = servlet;
    this.sleepTimeInSecs = sleepTimeInSecs;
  }

  public void run(){
    while(true){
      try{
        Thread.sleep( sleepTimeInSecs * 1000 );
      }catch( Exception ex){
      }

    }
  }
}