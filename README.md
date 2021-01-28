# Required:
* Java, Maven
# Command run test
    mvn clean test -DdefaultSuiteFiles="./src/test/resources/SmokeTest.xml" -Dtestng.dtd.http=true -DthreadCount=2

# How to create test

    1. New page object class in pageobjects package and this class must inherit form PageBase class.
    2. Create instance of the page at WebPage.
    3. New test class in test/java package and this class must inherit form TestBase class.
* Add common function at PageBase class under pageobjects package.

# Log4j example
    private static final Logger logger = LogManager.getLogger(TestLog4j.class);
        logger.info("This Will Be Printed On Info");
        logger.warn("This Will Be Printed On Warn");
        logger.error("This Will Be Printed On Error");

# Data test and test config
* Data test is under resouces/DataTest package.
* Change config at properties file under resouces package.
    Example: Browser, url, timeout, sql connection
    
# Common functions
* Use classes under common package.

# How to use ApiHelper
* Call get/post/put/delete functions at ApiHelper class under common package.

# How to use Sqlhelper
* Download and copy "mssql-jdbc_auth-8.4.1.x64" into java/bin folder.
https://docs.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-manage-for-sql-server?view=sql-server-ver15
* Call the executeQuery function at sqlhelper class under sqlhelper package with the query string.

# Report
* View the report at Report folder

