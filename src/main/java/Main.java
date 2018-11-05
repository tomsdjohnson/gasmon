import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.util.Topics;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;
import org.joda.time.LocalTime;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();

        try {
            //Connects to AWS//
            String bucket_name = "apprentices2018gasmon-locationss3bucket-7cafghqlcfch";
            String key_name = "locations.json";
            String myTopicArn = "arn:aws:sns:eu-west-1:552908040772:Apprentices2018GasMon-snsTopicSensorDataPart1-1TGJVE8L26XKE";

            S3Object o = s3.getObject(bucket_name, key_name);
            S3ObjectInputStream s3is = o.getObjectContent();
            String locations = convertStreamToString(s3is);
            s3is.close();

            //Creates a list objects of locations//
            ArrayList<LocationInfo> locationList = new ArrayList<LocationInfo>(JsonQueue.readJson(locations));

            AmazonSNS sns = AmazonSNSClientBuilder.defaultClient();
            AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

            ListQueuesResult allQueues = sqs.listQueues();
            for(String url : allQueues.getQueueUrls()){
                if(url.contains("Tom")){
                    sqs.deleteQueue(url);
                }
            }

            String myQueueUrl = sqs.createQueue(new CreateQueueRequest("gasMonQueue-Tom-" + new Date().getTime())).getQueueUrl();
            Topics.subscribeQueue(sns, sqs, myTopicArn, myQueueUrl);

            while(true){

                ReceiveMessageRequest request = new ReceiveMessageRequest(myQueueUrl).withMessageAttributeNames("ALL");
                request.setMaxNumberOfMessages(10);
                List<Message> messages = sqs.receiveMessage(request).getMessages();

                //List of Message bodies//
                ArrayList<QueueBody> bodyList = new ArrayList<QueueBody>();
                ArrayList<QueueMessage> messageList = new ArrayList<QueueMessage>();
                ArrayList<Reading> readingList = new ArrayList<Reading>();

                if (messages.size() > 0) {
                    for (Message i : messages) {
                        bodyList.add(JsonBody.readJson(i.getBody()));
                    }
                    for (QueueBody i : bodyList) {
                        messageList.add(JsonMessage.readJson(i.getMessage()));
                    }
                    for (QueueMessage i : messageList) {
                        for (LocationInfo y : locationList) {
                            if(i.getLocationId().equals(y.id)){
                                readingList.add(new Reading(i.getLocationId(), i.getValue(), i.getTimestamp(), y.getX(), y.getY()));
                            }
                        }
                    }
                    for (Reading i : readingList) {
                        System.out.println("[TIME: " + i.getTimestamp() + " VALUE: " + i.getValue() +
                                " [LOCATION ID: " + i.getLocationId() + " [X: " + i.getX() + "] Y: " + i.getY() + "]");
                    }
                }
            }

        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}