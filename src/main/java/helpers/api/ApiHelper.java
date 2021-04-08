package helpers.api;

import com.aventstack.extentreports.gherkin.model.Given;
import common.ReadProperties;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pageobjects.HomePage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApiHelper {
    private static Logger LOGGER = LogManager.getLogger(ApiHelper.class);

    public static final String ACCESS_TOKEN = "2U0cXXPpoAXsBeaSCK9M7QRpl1fsCHIPAYb1zbZLfWk";
    public static final String RANDOM_PHOTO_URI = "/photos/random";
    public static final String PHOTO_LIKE = "/photos/%s/like";
    public static final String COLLECTION = "/collections";
    public static final String DELETE_COLLECTION = "/collections/%s";
    public static final String ADD_PHOTO_COLLECTION = "/collections/%s/add";
    public static final String PHOTO_DOWNLOAD = "/photos/%s/download";
    public RequestSpecBuilder builder;
    public RequestSpecification request;

    public ApiHelper(){
        createRequest();
    }

    private void createRequest(){
        builder = new RequestSpecBuilder();
        builder.setBaseUri(ReadProperties.getInstanceFromResources("testsetting").getProperty("baseURI"));
        builder.setContentType(ContentType.JSON);
        builder.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request = RestAssured.given().spec(builder.build());
    }
}
