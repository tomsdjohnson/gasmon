
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Arrays;

public class JsonReader {

    //creates list of object LocationInfo//
    public static ArrayList<LocationInfo> readJson(String IOU){

        Gson gson = buildGson();
        LocationInfo[] location = gson.fromJson(IOU, LocationInfo[].class);
        ArrayList<LocationInfo> locationList = new ArrayList<LocationInfo>(Arrays.asList(location));

        return locationList;
    }

    private static Gson buildGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

}
