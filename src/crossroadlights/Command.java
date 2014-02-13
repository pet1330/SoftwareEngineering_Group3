package crossroadlights;

/**
 *
 * @author Group 3
 */
class Command {

    private String comandTo = "";
    private String comandFrom = "";
    private String LightID = "";
    private String commandA = "";
    private boolean connectionSucessful = false;
    private boolean connectionRefusedByServer = false;

    public Command() {
    }

    public void setConnectionSucessful(boolean connection) {
        connectionSucessful = connection;
    }

    public boolean getConnectionSucessful() {
        return connectionSucessful;
    }

    public void setRefusedConnection(boolean refused) {
        connectionRefusedByServer = refused;
    }

    public boolean getRefusedConnection() {
        return connectionRefusedByServer;
    }

    public String getID() {
        return LightID;
    }

    public void setID(String newID) {
        LightID = newID;
    }

    public String getComandTo() {
        return comandTo;
    }

    public void setComandTo(String to) {
        comandTo = to;
    }

    public String getComandFrom() {
        return comandFrom;
    }

    public void setComandFrom(String from) {
        comandFrom = from;
    }

    public String getCommandA() {
        return commandA;
    }

    public void setCommandA(String message) {
        commandA = message;
    }
}