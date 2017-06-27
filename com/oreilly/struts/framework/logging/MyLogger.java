package com.oreilly.struts.framework.logging;
import org.apache.log4j.Logger;
public class MyLogger implements ILogger {
  public void fatal(Object message) {
    loggerDelegate.fatal(message);
  }
  public void error(Object message) {
    loggerDelegate.error(message);
  }
  public void warn(Object message) {
    loggerDelegate.warn(message);
  }
  public void debug(Object message) {
    loggerDelegate.debug(message);
  }
  public void info(Object message) {
    loggerDelegate.info(message);
  }
  public synchronized static ILogger getLogger(String className) {
    return new MyLogger(Logger.getLogger(className));
  }
  private MyLogger(Logger delegate) {
    loggerDelegate = delegate;
  }
  protected static Logger loggerDelegate = null;
}
