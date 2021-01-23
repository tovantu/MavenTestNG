package apihelper;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class ApiHelper {

    private RequestSpecBuilder builder;
    private RequestSpecification request;

    public ApiHelper(){
        builder = new RequestSpecBuilder();
        builder.setBaseUri("https://jsonplaceholder.typicode.com/");
        builder.setContentType(ContentType.JSON);

        request = RestAssured.given().spec(builder.build());
    }

    public Response getMethod(String uri){
        // Header, Params, Authentication
        try{
            return request.get("todos/1");
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    public Response postMethod(String uri, Map<String, Object> map){
        request.body(map);
        try{
            return request.post("posts");
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    public Response putMethod(String uri, Map<String, String> map){
        request.body(map);
        try{
            return request.put("posts/1");
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    public Response deleteMethod(String uri){
        try{
            return request.delete("posts/1");
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return null;
    }
}
