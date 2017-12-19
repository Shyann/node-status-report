/**
 * Created by Shyan on 15/12/2017.
 */
public class Node {

    private Status status;
    private Message message;

    public Node(Status status, Message message){
        this.status = status;
        this.message = message;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
