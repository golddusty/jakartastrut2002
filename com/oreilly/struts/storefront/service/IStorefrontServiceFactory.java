package com.oreilly.struts.storefront.service;

public interface IStorefrontServiceFactory {
  public IStorefrontService createService() throws ClassNotFoundException,
    IllegalAccessException, InstantiationException;
  public void destroy();
}