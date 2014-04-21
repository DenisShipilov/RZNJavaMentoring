CREATE USER marketplace 
IDENTIFIED BY marketplace 
DEFAULT TABLESPACE USERS
TEMPORARY TABLESPACE TEMP 
PROFILE DEFAULT 
ACCOUNT UNLOCK ;
  -- 2 Roles for marketplace 
  GRANT RESOURCE TO marketplace;
  GRANT CONNECT TO marketplace;
  ALTER USER marketplace DEFAULT ROLE ALL;
  -- 6 System Privileges for marketplace
  GRANT CREATE PROCEDURE TO marketplace;
  GRANT CREATE TABLE TO marketplace;
  GRANT CREATE TRIGGER TO marketplace;
  GRANT CREATE SEQUENCE TO marketplace;
  GRANT CREATE VIEW TO marketplace;
  GRANT UNLIMITED TABLESPACE TO marketplace;
EXIT;