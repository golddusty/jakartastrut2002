package com.oreilly.struts.storefront.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import com.oreilly.struts.storefront.catalog.view.ItemDetailView;
import com.oreilly.struts.storefront.catalog.view.ItemSummaryView;
import com.oreilly.struts.storefront.framework.security.IAuthentication;
import com.oreilly.struts.storefront.customer.view.UserView;
import com.oreilly.struts.storefront.businessobjects.*;
// Import the exceptions used
import com.oreilly.struts.storefront.framework.exceptions.DatastoreException;
import com.oreilly.struts.storefront.framework.exceptions.InvalidLoginException;
import com.oreilly.struts.storefront.framework.exceptions.ExpiredPasswordException;
import com.oreilly.struts.storefront.framework.exceptions.AccountLockedException;
import javax.servlet.http.*;
import javax.servlet.ServletContext;
import org.apache.struts.webapp.example.Constants;
import com.oreilly.struts.storefront.service.memory.StorefrontMemoryDatabase;
/**
 * This class is used as a debug version of the Storefront Service. It's
 * mainly used for testing the frontend without requiring a database or
 * an EJB application to be present. The data is hard-coded.
 */
public class StorefrontDebugServiceImpl  implements IStorefrontService {

  ServletContext servletContext = null;

  public StorefrontDebugServiceImpl(){
    super();
  }

  public void setServletContext( ServletContext ctx ){
    this.servletContext = ctx;
  }

  public ServletContext getServletContext(){
    return servletContext;
  }

  public StorefrontMemoryDatabase getDatabase(){
    return (StorefrontMemoryDatabase)getServletContext().getAttribute(Constants.DATABASE_KEY);
  }

  public List getFeaturedItems() throws DatastoreException{
    List items = new ArrayList();
    Collection coll = getDatabase().getFeaturedItems();
    Iterator iter = coll.iterator();
    while( iter.hasNext() ){
      ItemBO itemBO = (ItemBO)iter.next();
      ItemSummaryView view = new ItemSummaryView();
      view.setName( itemBO.getDisplayLabel() );
      view.setDescription( itemBO.getDescription() );
      view.setUnitPrice( itemBO.getBasePrice() );
      view.setId( itemBO.getId().toString() );
      view.setSmallImageURL( itemBO.getSmallImageURL() );
      items.add( view );
    }
    return items;
  }

  public ItemDetailView getItemDetailView( String itemId )throws DatastoreException{
    ItemBO item = getDatabase().findItem( itemId );

    ItemDetailView view = new ItemDetailView();
    view.setId( item.getId().toString() );
    view.setLargeImageURL( item.getLargeImageURL() );
    view.setSmallImageURL( item.getSmallImageURL() );
    view.setDescription( item.getDescription() );
    view.setName( item.getDisplayLabel() );
    view.setUnitPrice( item.getBasePrice() );
    return view;
  }

  public void logout(String email){
    // Do nothing for this example
  }

  /**
   * Authenticate the user's credentials and either return a UserView for the
   * user or throw one of the security exceptions.
   */
  public UserView authenticate(String email, String password) throws
    InvalidLoginException,ExpiredPasswordException,AccountLockedException,
    DatastoreException{

      CustomerBO customer = getDatabase().authenticateUser( email, password );
      if ( customer != null ){
        UserView view = new UserView();
        view.setFirstName( customer.getFirstName() );
        view.setLastName( customer.getLastName() );
        view.setEmailAddress( customer.getEmail() );
        view.setId( customer.getId().toString() );
        view.setTimeCreated( new Timestamp(System.currentTimeMillis()));
        return view;
      }else{
        throw new InvalidLoginException();
      }
    }

  public void destroy(){
    // Do nothing for this example
  }
}