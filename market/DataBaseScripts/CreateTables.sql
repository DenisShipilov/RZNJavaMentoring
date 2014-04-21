--creating users table
CREATE TABLE users (
userId number,	
fullName varchar2(30),
billingAdress varchar2(20),
email varchar2(30),
login varchar2(10),
password varchar2(20),
Constraint PK_user PRIMARY KEY (userId),
CONSTRAINT UK_login  UNIQUE (login)
);

--createing category table
CREATE TABLE category (
categoryId number,
category varchar2(20),
parentCategory number,
CONSTRAINT PK_category PRIMARY KEY (categoryId),
CONSTRAINT FK_parent FOREIGN KEY (parentCategory)
	  	     REFERENCES category(categoryId)
);

--creating items table
CREATE TABLE items (
itemId number,
sellerId number,
title varchar2(10),
description varchar2(30),
category number,
startPrice float,
timeLeft number,
startBiddingDate date,
buyItNow number(7),
bidIncrement float,
CONSTRAINT PK_item PRIMARY KEY (itemId), 
CONSTRAINT FK_seller
	  FOREIGN KEY (sellerId)
	  REFERENCES users(userId) ON DELETE CASCADE,
CONSTRAINT FK_category
	  FOREIGN KEY (category)
	  REFERENCES category(categoryId)
);

--create bids
CREATE TABLE bids (
bidId number,
bidderId number,
itemId number,
bid float,
CONSTRAINT PK_bid PRIMARY KEY (bidId),
CONSTRAINT FK_bidder FOREIGN KEY (bidderId)
	  	     REFERENCES users(userId),
CONSTRAINT FK_item FOREIGN KEY (itemId)
	  	     REFERENCES items(itemId) ON DELETE CASCADE
);

--create deals table
CREATE TABLE deals (
dealId number,
sellerId number,
bidderId number,
itemId number,
CONSTRAINT PK_deal PRIMARY KEY (dealId),
CONSTRAINT FK_deal_seller FOREIGN KEY (sellerId)
	  	     REFERENCES users(userId) ON DELETE CASCADE,
CONSTRAINT FK_deal_buyer FOREIGN KEY (bidderId)
	  	     REFERENCES users(userId)
);

--create black list
CREATE TABLE blackList (
blockId number,
userId number,
blockedUserId number,
Constraint PK_block PRIMARY KEY (blockId),
CONSTRAINT FK_block  FOREIGN KEY (userId)
                    REFERENCES USERS(userId),
CONSTRAINT FK_blocked  FOREIGN KEY (blockedUserId)
                    REFERENCES USERS(userId)
);


--create sequence for user id
CREATE SEQUENCE users_seq
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE;

--create sequence for item id
CREATE SEQUENCE item_seq
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE;

--create sequence for bid id
CREATE SEQUENCE bid_seq
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE;

--creating category seq
CREATE SEQUENCE categ_seq
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE;


-- create sequence for blackList
CREATE SEQUENCE block_seq
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE;


-- create sequence for deals
CREATE SEQUENCE deal_seq
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE;
