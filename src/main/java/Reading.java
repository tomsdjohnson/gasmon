public class Reading {
    //initialising variables//
    private String locationId;
    private String value;
    private String timestamp;
    private String x;
    private String y;

    //constructor//
    public Reading(String newLocationId, String newValue, String newTimestamp, String newX, String newY) {
        locationId = newLocationId;
        value = newValue;
        timestamp = newTimestamp;
        x = newX;
        y = newY;
    }

    //getters//
    public String getLocationId() {return locationId;}
    public String getValue() {return value;}
    public String getTimestamp() {return timestamp;}
    public String getX() {return x;}
    public String getY() {return y;}
}