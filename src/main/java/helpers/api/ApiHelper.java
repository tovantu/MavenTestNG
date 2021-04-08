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
    private RequestSpecBuilder builder;
    private RequestSpecification request;

    public ApiHelper(){
        createRequest();
    }

    private void createRequest(){
        builder = new RequestSpecBuilder();
        builder.setBaseUri(ReadProperties.getInstance("testsetting").getProperty("baseURI"));
        builder.setContentType(ContentType.JSON);
        builder.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        request = RestAssured.given().spec(builder.build());
    }

    public Response getMethod(String uri){
        try{
            return request.get(uri);
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    public Response postMethod(String uri, Map<String, Object> map){
        request.body(map);
        try{
            return request.post(uri);
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    public Response deleteMethod(String uri){
        try{
            return request.delete(uri);
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return null;
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

    public Response getRandomPhoto(){
        try{
            return request.get(RANDOM_PHOTO_URI);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    public ArrayList<ArrayList<String>>likeRandomPhotos(int numberOfPhoto){
        ArrayList<ArrayList<String>> listArray = new ArrayList<>();
        ArrayList<String> listPhotoId = new ArrayList<>();
        ArrayList<String> listTitle = new ArrayList<>();
        for(int i = 0; i < numberOfPhoto; i ++){
            Response response = getRandomPhoto();
            String jsonString = response.body().asString();
            JsonPath jsonPath = new JsonPath(jsonString);
            String photoId = jsonPath.get("id").toString();
            String title = jsonPath.get("alt_description").toString();
            try{
                String uri = String.format(PHOTO_LIKE, photoId);
                Response postResponse = request.post(uri);
                LOGGER.info("Like request status is: " + postResponse.getStatusCode());
                listPhotoId.add(photoId);
                listTitle.add(title);
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
        listArray.add(listPhotoId);
        listArray.add(listTitle);
        return listArray;
    }

    public void unlikePhoto(String photoId){
        try{
            String uri = String.format(PHOTO_LIKE, photoId);
            Response response = request.delete(uri);
            LOGGER.info("Unlike request status is: " + response.getStatusCode());
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public Response createPrivateCollection(String title){
        try{
            Map<String, Object> map = new HashMap<>();
            map.put("title", title);
            map.put("private", true);
            request.body(map);
            Response response = request.post(COLLECTION);
            LOGGER.info("Create collection request status is: " + response.getStatusCode());
            return response;
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    public void removeCollection(String id){
        try{
            String uri = String.format(DELETE_COLLECTION, id);
            Response response = request.delete(uri);
            LOGGER.info("Delete collection request status is: " + response.getStatusCode());
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public ArrayList<String> addRandomPhotosIntoCollection(String id , int numberOfPhoto){
        ArrayList<String> listTitle = new ArrayList<>();
        for(int i = 0; i < numberOfPhoto; i ++){
            Response response = getRandomPhoto();
            String jsonString = response.body().asString();
            JsonPath jsonPath = new JsonPath(jsonString);
            String photoId = jsonPath.get("id").toString();
            String title = jsonPath.get("alt_description").toString();
            try{
                String uri = String.format(ADD_PHOTO_COLLECTION, id);
                Map<String, Object> map = new HashMap<>();
                map.put("photo_id", photoId);
                request.body(map);
                Response postResponse = request.post(uri);
                LOGGER.info("Add photo into collection request status is: " + postResponse.getStatusCode());
                listTitle.add(title);
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
        return listTitle;
    }
}
