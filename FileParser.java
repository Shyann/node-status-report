import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Created by Shyan on 15/12/2017.
 */
public class FileParser {

    private final long SYNCHRONISED_TIME = 50;
    private final String HELLO = "HELLO", FOUND = "FOUND", LOST = "LOST";

    private Map<String, Node> nodes = new LinkedHashMap<>();
    private List<Message> messages = new ArrayList<>();

    public FileParser() {
    }

    public static void main(String[] args) {
        FileParser f = new FileParser();
        f.readFile(args[0]);
        f.generateReport();
    }

    public void readFile(String name) {
        try {
            URL path = ClassLoader.getSystemResource(name);
            File f = new File(path.getFile());
            BufferedReader br = new BufferedReader(new FileReader(f));

            String line;
            while ((line = br.readLine()) != null) {
                parseLine(line);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e);
        }
    }

    public void parseLine(String line) {
        String[] parts = line.split("\\s+");
        if (parts.length > 5 || parts.length < 4) throw new IllegalArgumentException("Invalid data type");
        switch (parts[3]) {
            case HELLO:
                updateNodeStatus(parts[2], Status.ALIVE,
                        new Message(parts[2] + " " + parts[3], Long.parseLong(parts[1]), Long.parseLong(parts[0])), true);
                break;
            case FOUND:
                updateNodeStatus(parts[2], Status.ALIVE, generateMessage(parts), false);
                updateNodeStatus(parts[4], Status.ALIVE, generateMessage(parts), true);
                break;
            case LOST:
                updateNodeStatus(parts[2], Status.ALIVE, generateMessage(parts), false);
                updateNodeStatus(parts[4], Status.DEAD, generateMessage(parts), true);
                break;
            default:
                throw new IllegalArgumentException("Error parsing file");
        }
    }

    public void updateNodeStatus(String name, Status status, Message message, Boolean add) {
        if (nodes.containsKey(name)) {
            Node n = nodes.get(name);
            if (!(status.equals(Status.DEAD) && n.getStatus().equals(Status.ALIVE))) {
                n.setStatus(status);
                n.setMessage(message);
            }
        } else {
            Node n = new Node(status, message);
            nodes.put(name, n);
        }
        if(add) {
            messages.add(message);
            if (checkNodeSynchronisation()) {
                Node n = nodes.get(name);
                n.setStatus(Status.UNKNOWN);
                n.setMessage(message);
            }
        }

    }

    public boolean checkNodeSynchronisation() {
        if(messages.size() < 2) return false;
        Message last = messages.get(messages.size() - 1);
        for (int i = messages.size() - 2; i >= 0; i--) {
            if(Math.abs(messages.get(i).getTimeMessageReceived() - last.getTimeMessageReceived()) < SYNCHRONISED_TIME){
                return checkAmbiguity(last, messages.get(i));
            } else {
                break;
            }
        }
        return false;
    }

    public boolean checkAmbiguity(Message currentMessage, Message comparisonMessage) {
        // check if first message is hello and second message is lost(where node is subject)
        if((currentMessage.getMessageType().equals(HELLO))){
            return (currentMessage.getSender().equals(comparisonMessage.getReceiver()) &&
                    comparisonMessage.getMessageType().equals(LOST));
        }

        // check if first message is lost(where node is subject) and second message is hello
        if((comparisonMessage.getMessageType().equals(HELLO))){
            return (currentMessage.getReceiver().equals(comparisonMessage.getSender()) &&
                    currentMessage.getMessageType().equals(LOST));
        }
        return false;
    }

    public Message generateMessage(String[] split) {
        return new Message(split[2] + " " + split[3] + " " + split[4], Long.parseLong(split[1]), Long.parseLong(split[0]));
    }

    public void generateReport() {
        for (Map.Entry<String, Node> entry : nodes.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue().getStatus().toString() + " "
                    + entry.getValue().getMessage().toString());
        }
    }
}
