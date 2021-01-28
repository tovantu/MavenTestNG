import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

public class TestLog4j {
    private static final Logger logger = LogManager.getLogger(TestLog4j.class);

    @Test
    public void testLog4j(){
        logger.info("This Will Be Printed On Info");
        logger.warn("This Will Be Printed On Warn");
        logger.error("This Will Be Printed On Error");
    }
}
