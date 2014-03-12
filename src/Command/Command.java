package Command;

/**
 *
 * @author Group 3
 */

/* 
 * Allows an object to be serialised and sent through an object 
 * socket and reassembles at the other end
 */
import java.io.Serializable;

public class Command implements Serializable {
    private String command = "";
    private boolean quit = false;
    private boolean serverQuit = false;
    private String commandTo = "";
    private String commandFrom = "";
    private String LightID = "";
    private String connected = "";

    public Command(){
    }
    public String getCommand() {
        return command;
    }
    public void setCommand(String myContent) {
        command = myContent;
    }
    public boolean getQuit() {
        return quit;
    }
    public void setQuit(boolean myQuit) {
        quit=myQuit;
    }
    public boolean getServerQuit() {
        return serverQuit;
    }
    public void setServerQuit(boolean myServerQuit) {
        serverQuit=myServerQuit;
    }
    public String getLightID() {
        return LightID;
    }
    public void setLightID(String myNick) {
        LightID=myNick;
    }
    public String getCommandTo() {
        return commandTo;
    }
    public void setCommandTo(String to) {
        commandTo=to;
    }
    public String getCommandFrom() {
        return commandFrom;
    }
    public void setCommandFrom(String from) {
        commandFrom=from;
    }
    public String getConnected() {
        return connected;
    }
    public void setConnected(String areConnected) {
        connected=areConnected;
    }
}
