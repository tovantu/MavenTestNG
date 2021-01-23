# Required:
* Java, Maven
# Command run test
    mvn clean test -DthreadCount=3

# How to create test

    1. New page object class in pageobjects package and this class must inherit form PageBase class.
    2. Create instance of the page at WebPage under util package.
    3. New test class in test/java package and this class must inherit form TestBase class.
* Add common function at PageBase class under util package.

# Data test and test config
* Data test is under resouces/DataTest package.
* Change config at properties file under resouces package.
    Example: Browser, url, timeout, sql connection
    
# Common functions for UI
* Use classes at uihelper

# How to use apihelper
* Call get/post/put/delete functions at apihelper class under apihelper package.

# How to use sqlhelper
* Download and copy "mssql-jdbc_auth-8.4.1.x64" into java/bin folder.
https://docs.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver15
* Call the executeQuery function at sqlhelper class under sqlhelper package with the query string.

# Report
* View the report at Report folder

