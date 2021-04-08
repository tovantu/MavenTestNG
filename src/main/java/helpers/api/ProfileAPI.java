package helpers.api;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class ProfileAPI extends  ApiHelper{
    private static Logger LOGGER = LogManager.getLogger(ProfileAPI.class);

    public void updateUsername(String username){
        LOGGER.info("Update username");
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        Response response = updateUserProfile("/me", params);
        Assert.assertEquals(response.getStatusCode(),200, "Update username failed");
    }

    public Response updateUserProfile(String uri, Map<String, String> map){
        request.body(map);
        try{
            return request.put(uri);
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return null;
    }
}
