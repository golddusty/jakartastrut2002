
/**
 * This class encapsulate the data that must be sent in a
 * Email message. This class does not support attachments.
 * This class implements the java.io.Serializable
 * interface so that this object can be marshalled over
 * the network if needed.
 */
package com.oreilly.struts.service.email;
public class EmailMessage implements Emailable,java.io.Serializable {
  public void setFromAddress(String newFromAddress) {
    fromAddress = newFromAddress;
  }
  public String getFromAddress() {
    return fromAddress;
  }
  public String getSubject() {
    return subject;
  }
  
  // Public Accesssors and Mutators
  public String getToAddress() {
    return toAddress;
  }
  public String getBody() {
    return body;
  }
  public void setBody(String newBody) {
    body = newBody;
  }
  public void setSubject(String newSubject) {
    subject = newSubject;
  }
  
  // Default Constructor
  public EmailMessage() {
    super();
  }
  
  // Override the default toString method
  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("To: " + getToAddress());
    buf.append("\n");
    buf.append("From: " + getFromAddress());
    buf.append("\n");
    buf.append("Subject: " + getSubject());
    buf.append("\n");
    buf.append("Body: " + getBody());
    buf.append("\n");
    return buf.toString();
  }
  public void setToAddress(String newToAddress) {
    toAddress = newToAddress;
  }
  protected String subject;
  protected String fromAddress;
  
  // Private instance references
  protected String toAddress;
  protected String body;
}
