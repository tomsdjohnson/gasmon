import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonMessage {

    //creates list of object LocationInfo//
    public static QueueMessage readJson(String IOU){

        Gson gson = buildGson();
        QueueMessage location = gson.fromJson(IOU, QueueMessage.class);

        return location;
    }

    private static Gson buildGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

}