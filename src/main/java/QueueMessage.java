public class QueueMessage {
    //initialising variables//
    private String locationId;
    private String eventId;
    private String value;
    private String timestamp;


    //constructor//
    public QueueMessage(String newLocationId, String newEventId, String newValue, String newTimestamp) {
        locationId = newLocationId;
        eventId = newEventId;
        value = newValue;
        timestamp = newTimestamp;
    }

    //getters//
    public String getLocationId() {return locationId;}
    public String getEventId() {return eventId;}
    public String getValue() {return value;}
    public String getTimestamp() {return timestamp;}
}