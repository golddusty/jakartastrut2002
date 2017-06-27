package com.oreilly.struts.storefront.catalog;

import java.util.List;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import com.oreilly.struts.storefront.framework.exceptions.BaseException;
import com.oreilly.struts.storefront.framework.UserContainer;
import com.oreilly.struts.storefront.framework.StorefrontBaseAction;
import com.oreilly.struts.storefront.catalog.view.ItemDetailView;
import com.oreilly.struts.storefront.framework.util.IConstants;
import com.oreilly.struts.storefront.service.IStorefrontService;

public class FeaturedPageAction extends StorefrontBaseAction {
  public ActionForward execute( ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response )
   throws Exception {
    // Call the storefront service and ask it for an ItemView for the item
    IStorefrontService serviceImpl = getStorefrontService();

    List featuredItems = serviceImpl.getFeaturedItems();

    // Store the featured items into request scope
    request.setAttribute( IConstants.FEATURED_ITEMS_KEY, featuredItems );

    // Return the ActionForward that is defined for the success condition
    return mapping.findForward( IConstants.SUCCESS_KEY );
  }
}