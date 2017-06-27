DROP DATABASE storefront;

CREATE DATABASE storefront;

use storefront;

CREATE TABLE CATALOG(
  id int NOT NULL,
  displaylabel varchar(50) NOT NULL,
  featuredcatalog char(1) NULL,
  description varchar(255) NULL 
);

ALTER TABLE CATALOG ADD 
  CONSTRAINT PK_CATALOG PRIMARY KEY(id);

CREATE TABLE CUSTOMER (
  id int NOT NULL ,
  firstname varchar(50) NOT NULL,
  lastname varchar(50) NOT NULL,
  email varchar(50) NOT NULL ,
  password varchar(15) NOT NULL,  
  description varchar(255) NULL,  
  displaylabel varchar(255) NULL,  
  creditStatus char(1) NULL,
  accountstatus char(1) NULL,
  accountnumber varchar(15) NOT NULL
);

ALTER TABLE CUSTOMER ADD 
  CONSTRAINT PK_CUSTOMER PRIMARY KEY(id);

CREATE TABLE ITEM (
  id int NOT NULL ,  
  itemnumber varchar (255) NOT NULL,
  displaylabel varchar(50) NOT NULL,
  description varchar (255) NULL,
  baseprice decimal(9,2) NOT NULL,
  manufacturer varchar (255) NULL,
  sku varchar (255)  NULL,
  upc varchar (255)  NULL,
  minsellingunits int NULL,
  sellinguom varchar (255) NULL,
  leadtimemin varchar (255) NULL,
  leadtimemax varchar (255) NULL,
  onhandquantity int NOT NULL,
  featuredesc1 varchar (255) NULL,
  featuredesc2 varchar (255) NULL,
  featuredesc3 varchar (255) NULL 
)

ALTER TABLE ITEM ADD 
  CONSTRAINT PK_ITEM PRIMARY KEY(id);


CREATE TABLE CATALOGITEM_LNK(
  catalogid int NOT NULL ,
  itemid int NOT NULL 
)

ALTER TABLE CATALOGITEM_LNK ADD 
  CONSTRAINT PK_CATALOGITEM_LNK PRIMARY KEY(catalogid, itemid);

ALTER TABLE CATALOGITEM_LNK ADD 
  CONSTRAINT FK_CATALOGITEM_LNK_CATALOG FOREIGN KEY 
  (catalogid) REFERENCES CATALOG(id);
  
ALTER TABLE CATALOGITEM_LNK ADD 
  CONSTRAINT FK_CATALOGITEM_LNK_ITEM FOREIGN KEY 
  (itemid) REFERENCES ITEM(id);

CREATE TABLE PURCHASEORDER (
  id int NOT NULL,
  customerid int NOT NULL,
  submitteddatetime datetime NOT NULL ,
  status varchar (15) NULL,
  totalprice decimal(9,2) NOT NULL
)

ALTER TABLE PURCHASEORDER ADD 
  CONSTRAINT PK_PURCHASEORDER PRIMARY KEY(id);
  
ALTER TABLE PURCHASEORDER ADD 
  CONSTRAINT FK_PURCHASEORDER_CUSTOMER FOREIGN KEY 
  (customerid) REFERENCES CUSTOMER(id);

CREATE TABLE LINEITEM (
  id int NOT NULL,
  orderid int NOT NULL,
  itemid int NOT NULL,  
  lineitemnumber int NULL,
  unitprice decimal(9, 2) NOT NULL,
  baseprice decimal(9, 2) NOT NULL,
  quantity int NOT NULL 
)

ALTER TABLE LINEITEM ADD 
  CONSTRAINT PK_LINEITEM PRIMARY KEY(id);
    
ALTER TABLE LINEITEM ADD 
  CONSTRAINT FK_LINEITEM_ORDER FOREIGN KEY 
  (orderid) REFERENCES PURCHASEORDER(id);
  
ALTER TABLE LINEITEM ADD 
  CONSTRAINT FK_LINEITEM_ITEM FOREIGN KEY 
  (itemid) REFERENCES ITEM(id);  

