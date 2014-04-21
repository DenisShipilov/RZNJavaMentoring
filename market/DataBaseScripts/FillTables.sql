--Users
INSERT INTO users(userid,fullname,billingadress,email,login,password) VALUES (USERS_SEQ.nextval,'Dan Loi','illinois','Marck@mail','dan',11111111);
INSERT INTO users(userid,fullname,billingadress,email,login,password) VALUES (USERS_SEQ.nextval,'Alastar Alastar','New York','Marck@mail','All',12121212);
INSERT INTO users(userid,fullname,billingadress,email,login,password) VALUES (USERS_SEQ.nextval,'Joe Peshi','Chicago','Marck@mail','joe',33333333);
INSERT INTO users(userid,fullname,billingadress,email,login,password) VALUES (USERS_SEQ.nextval,'Mark Wed','Mechigan','Marck@mail','mrk',44444444);
COMMIT WORK;

--category

INSERT INTO category(categoryId,category) VALUES (CATEG_SEQ.nextval,'all');
INSERT INTO category(categoryId,category,parentCategory) VALUES (CATEG_SEQ.nextval,'cars',1);
INSERT INTO category(categoryId,category,parentCategory) VALUES (CATEG_SEQ.nextval,'dolls',1);
INSERT INTO category(categoryId,category,parentCategory) VALUES (CATEG_SEQ.nextval,'pats',1);
INSERT INTO category(categoryId,category,parentCategory) VALUES (CATEG_SEQ.nextval,'tools',1);
INSERT INTO category(categoryId,category,parentCategory) VALUES (CATEG_SEQ.nextval,'bicucles',1);



--Items
--1
INSERT INTO items(itemid, sellerid, title, description, category, startprice, timeleft, startbiddingdate, buyitnow, bidincrement)
VALUES (ITEM_SEQ.nextval,1,'Bicycle','DH Nukeproof d900',5,2000,90, TO_DATE('02.11.2012','DD-MM-YY'),'1',40);
--2
INSERT INTO items(itemid, sellerid, title, description,category, startprice, timeleft, startbiddingdate, buyitnow, bidincrement)
VALUES (ITEM_SEQ.nextval,1,'Bicycle','DH Nukeproof d900',5,4900,40000, TO_DATE('16.06.2012','DD-MM-YY'),'0',40);
--3
INSERT INTO items(itemid, sellerid, title, description,category, startprice, timeleft, startbiddingdate, buyitnow, bidincrement)
VALUES (ITEM_SEQ.nextval,1,'Bicycle','DH Nukeproof d400',5,4000,40000, SYSDATE, 0,10);
--4
INSERT INTO items(itemid, sellerid, title, description,category, startprice, timeleft, startbiddingdate, buyitnow, bidincrement)
VALUES (ITEM_SEQ.nextval,2,'cat','boy',3,60,200, TO_DATE('05.06.2012','DD-MM-YY'),1,40);
--5
INSERT INTO items(itemid, sellerid, title, description,category, startprice, timeleft, startbiddingdate, buyitnow, bidincrement)
VALUES (ITEM_SEQ.nextval,2,'cat','boy',3,60,200, TO_DATE('05.06.2012','DD-MM-YY'),1,40);
--6
INSERT INTO items(itemid, sellerid, title, description,category, startprice, timeleft, startbiddingdate, buyitnow, bidincrement)
VALUES (ITEM_SEQ.nextval,2,'cat','girl',3,60,20000, TO_DATE('05.06.2012','DD-MM-YY'),1,40);
--7
INSERT INTO items(itemid, sellerid, title, description,category, startprice, timeleft, startbiddingdate, buyitnow, bidincrement)
VALUES (ITEM_SEQ.nextval,3,'drill','Still Drill',5,200,4000, TO_DATE('05.06.2012','DD-MM-YY'),0,40);
--8
INSERT INTO items(itemid, sellerid, title, description,category, startprice, timeleft, startbiddingdate, buyitnow, bidincrement)
VALUES (ITEM_SEQ.nextval,3,'drill','Still Drill',5,200,4000, TO_DATE('05.06.2012','DD-MM-YY'),0,40);
--9
INSERT INTO items(itemid, sellerid, title, description,category, startprice, timeleft, startbiddingdate, buyitnow, bidincrement)
VALUES (ITEM_SEQ.nextval,3,'Marker','mac Pan',5,2,23, TO_DATE('05.06.2012','DD-MM-YY'),0,40);
--10
INSERT INTO items(itemid, sellerid, title, description,category, startprice, timeleft, startbiddingdate, buyitnow, bidincrement)
VALUES (ITEM_SEQ.nextval,3,'Shovel','Just shovel',5,9880,20, TO_DATE('05.06.2012','DD-MM-YY'),1,40);
--11
INSERT INTO items(itemid, sellerid, title, description,category, startprice, timeleft, startbiddingdate, buyitnow, bidincrement)
VALUES (ITEM_SEQ.nextval,4,'Book','The Cat Who Played Post Office',5,300,200, TO_DATE('29.06.2012','DD-MM-YY'),0,40);
--12
INSERT INTO items(itemid, sellerid, title, description,category, startprice, timeleft, startbiddingdate, buyitnow, bidincrement)
VALUES (ITEM_SEQ.nextval,4,'Book','The Cat Who Knew Shakespeare',5,300,200, TO_DATE('29.06.2012','DD-MM-YY'),0,40);
--13
INSERT INTO items(itemid, sellerid, title, description,category, startprice, timeleft, startbiddingdate, buyitnow, bidincrement)
VALUES (ITEM_SEQ.nextval,4,'book','Girl Doll',2,20,40000, TO_DATE('05.06.2012','DD-MM-YY'),1,40);
--14
INSERT INTO items(itemid, sellerid, title, description,category, startprice, timeleft, startbiddingdate, buyitnow, bidincrement)
VALUES (ITEM_SEQ.nextval,2,'car','Doge',1,3500,40000, TO_DATE('05.06.2012','DD-MM-YY'),0,300);
--15
INSERT INTO items(itemid, sellerid, title, description,category, startprice, timeleft, startbiddingdate, buyitnow, bidincrement)
VALUES (ITEM_SEQ.nextval,2,'car','Ford',1,6000,40000, TO_DATE('05.06.2012','DD-MM-YY'),0,700);
COMMIT WORK;

--Bids
--1
INSERT INTO bids(bidid,bidderid,itemid,bid) VALUES (BID_SEQ.nextval,1,8,40);
--2
INSERT INTO bids(bidid,bidderid,itemid,bid) VALUES (BID_SEQ.nextval,1,7,50);
--3
INSERT INTO bids(bidid,bidderid,itemid,bid) VALUES (BID_SEQ.nextval,2,8,60);
--4
INSERT INTO bids(bidid,bidderid,itemid,bid) VALUES (BID_SEQ.nextval,4,14,600);
COMMIT WORK;





