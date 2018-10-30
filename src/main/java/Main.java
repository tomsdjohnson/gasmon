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
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
        try {
            String bucket_name = "apprentices2018gasmon-locationss3bucket-7cafghqlcfch";
            String key_name = "locations.json";
            String myTopicArn = "arn:aws:sns:eu-west-1:552908040772:Apprentices2018GasMon-snsTopicSensorDataPart1-1TGJVE8L26XKE";


            S3Object o = s3.getObject(bucket_name, key_name);
            S3ObjectInputStream s3is = o.getObjectContent();
            String locations = convertStreamToString(s3is);
            s3is.close();

            ArrayList<LocationInfo> locationList = new ArrayList<LocationInfo>(JsonReader.readJson(locations));

            System.out.println(locationList);

            AmazonSNS sns = AmazonSNSClientBuilder.defaultClient();
            AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

            String myQueueUrl = sqs.createQueue(new CreateQueueRequest("TomsQueue")).getQueueUrl();

            Topics.subscribeQueue(sns, sqs, myTopicArn, myQueueUrl);

            TimeUnit.SECONDS.sleep(10);

            List<Message> messages = sqs.receiveMessage(new ReceiveMessageRequest(myQueueUrl)).getMessages();
            if (messages.size() > 0) {
                for( int i ,messages.size() i++){



                }
//                byte[] decodedBytes = Base64.decodeBase64((messages.get(0)).getBody().getBytes());
//                System.out.println("Message: " +  new String(decodedBytes));
            }

            sqs.deleteQueue(myQueueUrl);

        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
