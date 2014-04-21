1)To create DateBase and war file
put all .bat files in folder with build.xml
-Run create-db.bat to create datebase
-Run buildProject.bat to create .war and .class files
2)To deploy on web sphere 
1.run server
1.2 run administrative console

<!-- TO CREATE JDBC PROVIDER -->

2.choise Recources -> JDBC ->  JDBC provider -> NEW
2.1 
DataBase type = oracle
provider type = oracle JDBC Driver
implementation type = connection pool
-> next 
2.2 Set path to ojdbc14.jar in your file system -> next -> finish -> Save

<!-- TO CREATE JAAS - J2C  ALIAS -->

3.1 Security -> Secure administration, applications, and infrastructure ->  Java Authentication and Authorization Service -> J2C authentication data -> New
3.2 
Alias: market
User ID: market
Password: market
-> Apply -> Save

<--! TO CREATE DATA Source -->
4.1 Resources -> JDBC -> Data sources -> new
4.2 
data source name = OracleDataSource
jndi Name = jdbc/marketPlace
4.3 Select jdbc provider  = Oracle jdbc provider -> next
4.4 
url = jdbc:oracle:thin:@localhost:1521:xe
Data store helper = Oracle10g data store helper
-> next -> finish ->Save

<!-- Deploy application ->
5.1 Applications -> Install New Application
5.2 
Browse = created war file (OnlineMarketplaceWithAdvancedSearchItems.war)
context path = OnlineMarketplace
5.3 map modules to server: check server, choise war file, push apply -> next
5.4 check "Use default method (many-to-one mapping)" , choise "nodename"/market
5.5 select application -> apply
5.4 browse -> check oracleJdbcDriver -> apply -> next*2 -> finish -> save

<! -- RUN -->
6.1 Applications -> Enterprise Applications -> check OnlineMarketplaceWithAdvancedSearchItems_war  -> start
6.2 http://localhost:9080/OnlineMarketplace/ in browser 






