package com.oreilly.struts.framework.logging;

/**
 * A very simple version of the log methods. These are not
 * all of the log4j methods, but enough to give you the idea.
 */
public interface ILogger {
  public void debug(Object message);
  public void info(Object message);
  public void warn(Object message);
  public void error(Object message);
  public void fatal(Object message);
}
