/**
 * Created by Shyan on 15/12/2017.
 */
public class Message {
    private String message;
    private Long timeMessageGenerated;
    private Long timeMessageReceived;

    public Message(String message, Long timeMessageGenerated, Long timeMessageReceived) {
        this.message = message;
        this.timeMessageGenerated = timeMessageGenerated;
        this.timeMessageReceived = timeMessageReceived;
    }

    public String getSender(){
        return message.split("\\s+")[0];
    }

    public String getReceiver(){
        return !getMessageType().equals("HELLO") ?  message.split("\\s+")[2] : null;
    }

    public String getMessageType(){
        return message.split("\\s+")[1];
    }

    public Long getTimeMessageReceived() {
        return timeMessageReceived;
    }

    @Override
    public String toString() {
        return timeMessageReceived + " " + message;
    }
}
