
/**
 * This interface defines methods that an object that wishses to
 * represent an email message, must implement.<p>
 */
package com.oreilly.struts.service.email;
public interface Emailable extends java.io.Serializable {
  public String getSubject();
  public void setBody(String newBody);
  public String getBody();
  public String getToAddress();
  public void setToAddress(String newToAddress);
  public void setFromAddress(String newFromAddress);
  public String getFromAddress();
  public void setSubject(String newSubject);
}
