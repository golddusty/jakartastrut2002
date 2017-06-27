
/**
 * A Message-Driven bean that listens on a javax.jms.Queue for
 * messages and gets the email message out and sends it off to
 * a smtp host.
 */
package com.oreilly.struts.service.email;
import java.util.Date;
import java.util.Properties;
import javax.ejb.*;
import javax.jms.*;
import javax.naming.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
public class EmailServiceBean implements javax.ejb.MessageDrivenBean,MessageListener {
  public void ejbCreate() {
    
  
  // Not used for this bean
  }
  
  // Default Constructor
  public EmailServiceBean() {
    super();
  }
  
  /** The required onMessage method from the MessageListener interface
   * The onMessage method is not allowed to throw exceptions, so
   * we will catch every checked exception and just print out a
   * stack trace.
   */
  public void onMessage(javax.jms.Message message) {
    
    // Local reference to the javax.mail.Session
    javax.mail.Session mailSession = null;
    try {
      
      //Make sure it's a type ObjectMessage
      if(!(message instanceof ObjectMessage)) {
        return ;
      }
      
      // Make sure it's an EmailMessage
      Object obj = ((ObjectMessage)message).getObject();
      if(!(obj instanceof Emailable)) {
        return ;
      }
      Emailable email = (Emailable)obj;
      Context ctx = new InitialContext();
      
      // Get the properties from this bean from the environment. The
      
      // properties were specified in the env-entry tags in the deployment
      
      // descriptor for this bean
      Context myEnv = (Context)ctx.lookup("java:comp/env");
      String smtpHost = (String)myEnv.lookup("smtpHost");
      Properties props = new Properties();
      props.put("mail.smtp.host", smtpHost);
      
      // Get a mail session. You would normally get this from
      
      // JNDI, but some servers have a problem with this.
      
      // Each Message Driven bean instance is responsible for
      
      //getting its own unshared javax.mail.Session.
      mailSession = javax.mail.Session.getDefaultInstance(props, null);
      javax.mail.Message msg = new MimeMessage(mailSession);
      
      // Set the mail properties
      msg.setFrom(new javax.mail.internet.InternetAddress(email.getFromAddress()));
      InternetAddress[] addresses =  {
        new InternetAddress(email.getToAddress())
      };
      msg.setRecipients(javax.mail.Message.RecipientType.TO, addresses);
      msg.setSubject(email.getSubject());
      msg.setSentDate(new Date());
      
      // Create the body text
      Multipart parts = new MimeMultipart();
      MimeBodyPart mainBody = new MimeBodyPart();
      mainBody.setText(email.getBody());
      parts.addBodyPart(mainBody);
      
      // Could also have supported attachments, but not for this version
      
      // it's commented it out.
      
      /*
      MimeBodyPart attachmentBody = new MimeBodyPart();
      attachmentBody.setText( "This is text in the attachment" );
      attachmentBody.addBodyPart( p2 );
      */
      
      // Set some header fields
      msg.setHeader("X-Priority", "High");
      msg.setHeader("Sensitivity", "Company-Confidential");
      msg.setContent(parts);
      System.out.println("Sending mail to " + email.getToAddress());
      Transport.send(msg);
    }
    catch(Exception ex) {
      
      // The onMessage method should not throw any kind of exceptions
      
      // according to the EJB 2.0 specifiction.
      ex.printStackTrace();
    }
    finally {
      mailSession = null;
    }
  }
  public void setMessageDrivenContext(MessageDrivenContext ctx) {
    context = ctx;
  }
  public void ejbRemove() {
    
  
  // Not used for this bean
  }
  
  // Instance ref for the beans context
  MessageDrivenContext context = null;
}
