import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonBody {

    //creates list of object LocationInfo//
    public static QueueBody readJson(String IOU){

        Gson gson = buildGson();
        QueueBody location = gson.fromJson(IOU, QueueBody.class);

        return location;
    }

    private static Gson buildGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

}