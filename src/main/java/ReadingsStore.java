import java.util.ArrayList;

public class ReadingsStore{
    //initialising variables//
    private ArrayList<Reading> timedReadingList = new ArrayList<Reading>();
    private String timestamp;

    //constructor//
    public ReadingsStore(String newTimestamp) {
        timestamp = newTimestamp;
    }

    //getters//
    public String getTimestamp() {return timestamp;}

    public Boolean checkTimestamp(String readingTimestamp){

        int readingTimestampInt = Integer.parseInt(readingTimestamp);
        int timestampInt = Integer.parseInt(timestamp);

        if(readingTimestampInt - timestampInt <= 60){
            return true;
        } else {
            return false;
        }
    }

    public void add(Reading reading){
        timedReadingList.add(reading);
    }
}
