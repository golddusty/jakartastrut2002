package com.oreilly.struts.storefront.service.memory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ObjectCreationFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;

import com.oreilly.struts.storefront.businessobjects.CustomerBO;
import com.oreilly.struts.storefront.businessobjects.ItemBO;

public final class StorefrontMemoryDatabase {

    private static StorefrontMemoryDatabase Singleton = null;

    /**
     * Logging output for this user database instance.
     */
    private Log log = LogFactory.getLog(this.getClass());
    private HashMap users = new HashMap();
    private HashMap items = new HashMap();

    /**
     * Absolute pathname to the persistent file we use for loading and storing
     * persistent data.
     */
    private String pathname = null;
    private String pathnameOld = null;
    private String pathnameNew = null;

    public static StorefrontMemoryDatabase getInstance(){
      synchronized(StorefrontMemoryDatabase.class){
        if (Singleton == null ){
          Singleton = new StorefrontMemoryDatabase();
        }
      }
      return Singleton;
    }

    public String getPathname() {
        return (this.pathname);
    }

    public void setPathname(String pathname) {
        this.pathname = pathname;
        pathnameOld = pathname + ".old";
        pathnameNew = pathname + ".new";
    }

    /**
     * <p>Finalize access to the underlying persistence layer.</p>
     *
     * @exception Exception if a database access error occurs
     */
    public void close() throws Exception {
      //save();
    }

    public void save() throws Exception {
    }

    public ItemBO createItem(String oid ) {
        synchronized (items) {
            if (items.get(oid) != null) {
                throw new IllegalArgumentException("Duplicate item '" +
                                                   oid + "'");
            }
            if (log.isTraceEnabled()) {
                log.trace("Creating item '" + oid + "'");
            }
            ItemBO item = new ItemBO();

            item.setId( new Integer(oid) );

            synchronized (items) {
              items.put(oid, item);
            }
            return (item);
        }

    }

    public CustomerBO createCustomer(String oid, String email, String passwd ) {

        synchronized (users) {
            if (users.get(email) != null) {
                throw new IllegalArgumentException("Duplicate user '" +
                                                   email + "'");
            }
            if (log.isTraceEnabled()) {
                log.trace("Creating user '" + email + "'");
            }
            CustomerBO customer = new CustomerBO();
            customer.setId( new Integer(oid) );
            customer.setEmail( email );
            customer.setPassword( passwd );
            synchronized (users) {
              users.put(oid, customer);
            }
            return (customer);
        }

    }


    public CustomerBO findCustomer(String oid) {
      return ((CustomerBO) users.get(oid));
    }

    public ItemBO findItem(String oid) {
      return ((ItemBO) items.get(oid));
    }

    public Collection getFeaturedItems(){
      return items.values();
    }

    public CustomerBO authenticateUser( String email, String passwd ){
      CustomerBO aUser = null;


      if ( email == null || passwd == null ){
        return null;
      }


        Iterator iter = users.values().iterator();
        while ( iter.hasNext() ){
          CustomerBO candidateCustomer = (CustomerBO)iter.next();
          if (email.equals( candidateCustomer.getEmail()) &&
            passwd.equals( candidateCustomer.getPassword()) ) {
              aUser = candidateCustomer;
              break;
          }
        }

      return aUser;
    }


    public void open() throws Exception {

        FileInputStream fis = null;
        BufferedInputStream bis = null;

        try {

            // Acquire an input stream to our database file
            if (log.isDebugEnabled()) {
                log.debug("Loading database from '" + pathname + "'");
            }
            fis = new FileInputStream(pathname);
            bis = new BufferedInputStream(fis);

            // Construct a digester to use for parsing
            Digester digester = new Digester();
            digester.push(this);
            digester.setValidating(false);
            digester.addFactoryCreate
                ("database/user",
                 new MemoryUserCreationFactory(this));
            digester.addFactoryCreate
                ("database/product",
                 new MemoryProductCreationFactory(this));

            // Parse the input stream to initialize our database
            digester.parse(bis);
            bis.close();
            bis = null;
            fis = null;

        } catch (Exception e) {

            log.error("Loading database from '" + pathname + "':", e);
            throw e;

        } finally {

            if (bis != null) {
                try {
                    bis.close();
                } catch (Throwable t) {
                    ;
                }
                bis = null;
                fis = null;
            }

        }

    }




/**
 * Digester object creation factory for subscription instances.
 */
class MemoryProductCreationFactory implements ObjectCreationFactory {

    public MemoryProductCreationFactory(StorefrontMemoryDatabase database) {
        this.database = database;
    }

    private StorefrontMemoryDatabase database = null;

    private Digester digester = null;

    public Digester getDigester() {
        return (this.digester);
    }

    public void setDigester(Digester digester) {
        this.digester = digester;
    }

    public Object createObject(Attributes attributes) {
        String oid = attributes.getValue("oid");
        String name = attributes.getValue("name");
        String image = attributes.getValue("image");
        String priceStr = attributes.getValue("price");
        String desc = attributes.getValue("description");

        ItemBO item = database.createItem( oid );
        item.setDescription( desc );
        item.setSmallImageURL( image );
        item.setLargeImageURL( image );
        item.setDisplayLabel( name );

        Double price = null;
        if ( priceStr != null ){
          try{
            item.setBasePrice( Double.valueOf(priceStr) );
          }catch( Exception ex ){
            ex.printStackTrace();
          }
        }
        System.out.println( "Putting " + oid + " " + item );
        items.put( oid, item );
        return (item);
    }

}


/**
 * Digester object creation factory for user instances.
 */
class MemoryUserCreationFactory implements ObjectCreationFactory {

    public MemoryUserCreationFactory(StorefrontMemoryDatabase database) {
        this.database = database;
    }

    private StorefrontMemoryDatabase database = null;

    private Digester digester = null;

    public Digester getDigester() {
        return (this.digester);
    }

    public void setDigester(Digester digester) {
        this.digester = digester;
    }

    public Object createObject(Attributes attributes) {
        String oid = attributes.getValue("oid");
        String email = attributes.getValue("email");
        String password = attributes.getValue("password");

        CustomerBO cust = database.createCustomer(oid,email, password);
        cust.setFirstName(attributes.getValue("firstname"));
        cust.setLastName(attributes.getValue("lastname"));

        return (cust);
    }
}

}
