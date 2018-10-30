public class LocationInfo {
    //initialising variables//
    public String x;
    public String y;
    public String id;

    //constructor//
    public LocationInfo(String newX, String newY, String newId) {
        x = newX;
        y = newY;
        id = newId;
    }

    public String getX() {
        return x;
    }
    public String getY() {
        return y;
    }
    public String getId() {
        return id;
    }
}