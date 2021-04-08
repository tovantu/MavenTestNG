package helpers.api;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CollectionAPI extends ApiHelper {
    private static Logger LOGGER = LogManager.getLogger(CollectionAPI.class);

    public String createPrivateCollection(String title){
        try{
            Map<String, Object> map = new HashMap<>();
            map.put("title", title);
            map.put("private", true);
            request.body(map);
            Response response = request.post(COLLECTION);
            LOGGER.info("Create collection request status is: " + response.getStatusCode());
            String jsonString = response.body().asString();
            JsonPath jsonPath = new JsonPath(jsonString);
            String collectionId = jsonPath.get("id").toString();
            return collectionId;
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
            PhotoAPI photoAPI = new PhotoAPI();
            Response response = photoAPI.getRandomPhoto();
            try{
                String jsonString = response.body().asString();
                JsonPath jsonPath = new JsonPath(jsonString);
                String photoId = jsonPath.get("id").toString();
                String title = jsonPath.get("alt_description").toString();
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
