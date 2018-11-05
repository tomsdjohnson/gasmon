public class QueueBody {
    //initialising variables//
    private String Type;
    private String MessageId;
    private String TopicArn;
    private String Message;
    private String Timestamp;
    private String SignatureVersion;
    private String Signature;
    private String SigningCertURL;
    private String UnsubscribeURL;

    //constructor//
    public QueueBody(String newType, String newMessageId, String newTopicArn, String newMessage, String newTimestamp,
                     String newSignatureVersion, String newSignature, String newSigningCertURL, String newUnsubscribeURL) {
        Type = newType;
        MessageId = newMessageId;
        TopicArn = newTopicArn;
        Message = newMessage;
        Timestamp = newTimestamp;
        SignatureVersion = newSignatureVersion;
        Signature = newSignature;
        SigningCertURL = newSigningCertURL;
        UnsubscribeURL = newUnsubscribeURL;
    }

    //getters//
    public String getType() {
        return Type;
    }
    public String getMessageId() {
        return MessageId;
    }
    public String getTopicArn() {
        return TopicArn;
    }
    public String getMessage() {
        return Message;
    }
    public String getTimestamp() {
        return Timestamp;
    }
    public String getSignatureVersion() {
        return SignatureVersion;
    }
    public String getSignature() {
        return Signature;
    }
    public String getSigningCertURL() {
        return SigningCertURL;
    }
    public String getUnsubscribeURL() {
        return UnsubscribeURL;
    }
}