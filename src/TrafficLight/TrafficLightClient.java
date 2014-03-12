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

    private TrafficLightClient() {
    }

    public static void StartClient(Socket socket, String ID) throws IOException {
        TrafficLightClient tlc = new TrafficLightClient();
        tlc.LightID = ID;
        tlc.TL = new TrafficLight(tlc);
        OutputStream os = socket.getOutputStream();
        tlc.output = new ObjectOutputStream(os);
        tlc.output.flush();
        InputStream is = socket.getInputStream();
        tlc.input = new ObjectInputStream(is);
        tlc.uiListener = new Thread(tlc);
        tlc.uiListener.start();
        tlc.comment = new Command();
        tlc.comment.setLightID(tlc.LightID);
        tlc.sendCommand(tlc.comment);
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
            TrafficLightClient.StartClient(new Socket("LocalHost", 1560), "0001");
        } catch (ConnectException e) {
            System.out.println("Server not found at this Host:Port");
        }
    }
}