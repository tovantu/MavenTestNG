import ApiHelper.ApiHelper;
import Util.TestBase;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class ApiTest {



//    @Test
    public void testAPI(){
        ApiHelper apiHelper = new ApiHelper();

        Response response1 = apiHelper.getMethod("1");
        System.out.println(response1.getBody().asString());

        Map<String, Object> map1 = new HashMap<>();
        map1.put("title","foo");
        map1.put("body","bar");
        map1.put("userId","1");

        Response response2 = apiHelper.postMethod("post", map1);
        System.out.println(response2.getBody().asString());

        Map<String, String> map2 = new HashMap<>();
        map2.put("id", "1");
        map2.put("title", "foo");
        map2.put("body", "bar");
        map2.put("userId", "1");
        Response response3 = apiHelper.putMethod("", map2);
        System.out.println(response3.getBody().asString());

        Response response4 = apiHelper.deleteMethod("");
        System.out.println(response4.getStatusCode());
        System.out.println(response4.getStatusLine());
    }
}
