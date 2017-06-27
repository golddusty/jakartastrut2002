package com.oreilly.struts.framework.logging;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.helpers.LogLog;
import java.util.Hashtable;
import java.util.Properties;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import com.oreilly.struts.service.email.Emailable;
import com.oreilly.struts.service.email.EmailMessage;

/**
 * A log4j appender that sends messages to a JMS Queue that will
 * eventually send them to a SMTP server.
 *
 */
public class JMSQueueAppender extends AppenderSkeleton {
  
  /**
   * Set the name of the JMS Queue Connection
   */
  public void setQueueConnectionFactoryBindingName(String queueConnectionName) {
    this.queueConnectionFactoryBindingName = queueConnectionFactoryBindingName;
  }
  public boolean requiresLayout() {
    return false;
  }
  
  /**
   * This method is called by AppenderSkeleton to do most of the real
   * appending work.  The LoggingEvent will be be wrapped in an ObjectMessage
   * to be put on the JMS queue.
   */
  public void append(LoggingEvent event) {
    if(!checkEntryConditions()) {
      return ;
    }
    try {
      ObjectMessage msg = queueSession.createObjectMessage();
      Emailable emailMsg = new EmailMessage();
      emailMsg.setToAddress("administrator@somehost.com");
      emailMsg.setFromAddress("logMessageService@somehost.com");
      emailMsg.setSubject("Log Message for application received");
      emailMsg.setBody(event.toString());
      msg.setObject(emailMsg);
      queueSender.send(msg);
    }
    catch(Exception ex) {
      errorHandler.error("Error sendubg message", ex, ErrorCode.GENERIC_FAILURE);
    }
  }
  
  /**
   * Close all the resources used by the appender.
   */
  public synchronized void close() {
    if(this.closed) {
      return ;
    }
    LogLog.debug("Closing appender [" + name + "].");
    this.closed = true;
    try {
      if(queueSession != null) {
        queueSession.close();
      }
      if(queueConnection != null) {
        queueConnection.close();
      }
    }
    catch(Exception e) {
      LogLog.error("Error closing JMSQueueAppender [" + name + "].", e);
    }
    
    // Help garbage collection
    queueSender = null;
    queueSession = null;
    queueConnection = null;
  }
  
  /**
   * Set the name of the JMS Queue
   */
  public void setQueueBindingName(String queueBindingName) {
    this.queueBindingName = queueBindingName;
  }
  public JMSQueueAppender() {
    super();
  }
  
  /**
   * Retrieve the name of the JMS Queue
   */
  public String getQueueBindingName() {
    return queueBindingName;
  }
  
  /**
   * Retrieve the provider Url
   */
  public String getProviderUrl() {
    return providerUrl;
  }
  
  /**
   * Set the provider Url
   */
  public void setProviderUrl(String providerUrl) {
    this.providerUrl = providerUrl;
  }
  
  /**
   * Set the InitialContextFactory
   */
  public void setInitialContextFactory(String initialContextFactory) {
    icFactory = initialContextFactory;
  }
  
  /**
   * Retrieve the name of the JMS Queue connection
   */
  public String getQueueConnectionFactoryBindingName() {
    return queueConnectionFactoryBindingName;
  }
  
  /**
   * Retrieve the InitialContextFactory
   */
  public String getInitialContextFactory() {
    return icFactory;
  }
  public void activateOptions() {
    QueueConnectionFactory queueConnectionFactory;
    try {
      Context ctx = getInitialContext();
      queueConnectionFactory = (QueueConnectionFactory)ctx.lookup(queueConnectionFactoryBindingName);
      queueConnection = queueConnectionFactory.createQueueConnection();
      queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
      Queue queue = (Queue)ctx.lookup(queueBindingName);
      queueSender = queueSession.createSender(queue);
      queueConnection.start();
      ctx.close();
    }
    catch(Exception ex) {
      errorHandler.error("Error in activateOptions", ex, ErrorCode.GENERIC_FAILURE);
    }
  }
  
  /**
   * Return true if all of the neccessary resources are initialzed.
   * Otherwise return false.
   */
  protected boolean checkEntryConditions() {
    String fail = null;
    if(this.queueConnection == null) {
      fail = "No QueueConnection";
    }
    else {
      if(this.queueSession == null) {
        fail = "No QueueSession";
      }
      else {
        if(this.queueSender == null) {
          fail = "No QueueSender";
        }
      }
    }
    if(fail != null) {
      errorHandler.error(fail + " for JMSQueueAppender named [" + name + "].");
      return false;
    }
    else {
      return true;
    }
  }
  
  /**
   * Return a new InitialContext
   */
  protected InitialContext getInitialContext()
    throws NamingException {
    try {
      Hashtable ht = new Hashtable();
      
      //Populate property hashtable with data to retrieve the context.
      ht.put(Context.INITIAL_CONTEXT_FACTORY, icFactory);
      ht.put(Context.PROVIDER_URL, providerUrl);
      return (new InitialContext(ht));
    }
    catch(NamingException ne) {
      LogLog.error("InitialContext failed");
      throw ne;
    }
  }
  protected Queue queue;
  protected QueueConnection queueConnection;
  protected QueueSender queueSender;
  protected QueueSession queueSession;
  String queueConnectionFactoryBindingName;
  String icFactory;
  String providerUrl;
  String queueBindingName;
}
