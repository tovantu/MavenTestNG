package helpers.api;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class PhotoAPI extends ApiHelper{
    private static Logger LOGGER = LogManager.getLogger(ProfileAPI.class);

    public ArrayList<ArrayList<String>>likeRandomPhotos(int numberOfPhoto){
        ArrayList<ArrayList<String>> listArray = new ArrayList<>();
        ArrayList<String> listPhotoId = new ArrayList<>();
        ArrayList<String> listTitle = new ArrayList<>();
        for(int i = 0; i < numberOfPhoto; i ++){
            Response response = getRandomPhoto();
            try{
                String jsonString = response.body().asString();
                JsonPath jsonPath = new JsonPath(jsonString);
                String photoId = jsonPath.get("id").toString();
                String title = jsonPath.get("alt_description").toString();
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

    public String downloadPhoto(String photoId){
        try{
            Response response = request.get(String.format(PHOTO_DOWNLOAD, photoId));
            LOGGER.info("Download photo request status is: " + response.getStatusCode());
            String jsonString = response.body().asString();
            JsonPath jsonPath = new JsonPath(jsonString);
            return jsonPath.get("url").toString();
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }


    public Response getRandomPhoto(){
        try{
            Response response = request.get(RANDOM_PHOTO_URI);
            LOGGER.info("Get random photo id request status is: " + response.getStatusCode());
            return response;
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }
}
