package TrafficLight;

import Command.Command;
import java.net.*;
import java.io.*;

public class TrafficLightClient implements Runnable {

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Command comment;
    private TrafficLight TL;
    private String LightID;
    private Thread uiListener;
    private boolean run = true;

    private TrafficLightClient(Socket socket, String ID) throws IOException {
        LightID = ID;
        TL = new TrafficLight(this);
        OutputStream os = socket.getOutputStream();
        output = new ObjectOutputStream(os);
        output.flush();
        InputStream is = socket.getInputStream();
        input = new ObjectInputStream(is);
        uiListener = new Thread(this);
        uiListener.start();
        comment = new Command();
        comment.setLightID(LightID);
        sendCommand(comment);
    }

    public void writeOutput(Object command) {
        try {
            output.writeObject(command);
        } catch (IOException ex) {
        }
    }

    public String getLightID() {
        return LightID;
    }

    public void setLightID(String ID) {
        this.LightID = ID;
    }

    public void run() {
        try {
            while (run) {
                try {
                    comment = (Command) input.readObject();
                    TL.TrafficLightUserLog(comment);
                } catch (ClassNotFoundException e) {
                }
            }
        } catch (IOException ex) {
        } finally {
            uiListener = null;
            TL.disconnect();

            try {
                output.close();
            } catch (IOException ex) {
            }
        }
    }

    public void sendCommand(Command command) {
        try {
            command.setLightID(LightID);
            output.writeObject(command);
            output.flush();
        } catch (IOException ex) {
            terminateClient();
        }
    }

    public void terminateClient() {
        run = false;
    }

    public boolean stillRunning() {
        return (uiListener != null);
    }

    public static void main(String[] args) throws IOException, UnknownHostException {
        try {
            Socket socket = new Socket("LocalHost", 1560);
            new TrafficLightClient(socket, "0001");
        } catch (ConnectException e) {
            System.out.println("Server not found at this Host:Port");
        }
    }
}