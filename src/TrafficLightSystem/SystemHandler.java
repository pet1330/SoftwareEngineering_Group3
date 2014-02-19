package TrafficLightSystem;

import Command.Command;
import java.net.*;
import java.io.*;
import java.util.*;

public class SystemHandler implements Runnable {

    private static final int MAXCLIENTS = 8;
    public static ArrayList<SystemHandler> handlers = new ArrayList<SystemHandler>();
    private Command command;
    private String lightID;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private boolean run = true;
    private String clientIP;

    public SystemHandler(Socket clientSocket) throws IOException {
        socket = clientSocket;
        clientIP = socket.getInetAddress().toString();
        OutputStream os = socket.getOutputStream();
        output = new ObjectOutputStream(os);
        output.flush();
        InputStream is = socket.getInputStream();
        input = new ObjectInputStream(is);
    }

    public void run() {
        try {
            command = (Command) input.readObject();
            lightID = command.getLightID();
            if (!availableID(lightID)) {
                Command killCommand = new Command();
                killCommand.setCommand("Connection refused: LightID already in use on this server");
                killCommand.setCommandTo(lightID);
                distribute(killCommand);
                LocalServer.writeToLog("New Client conection refused from: " + clientIP);
                return;
            }
            handlers.add(this);
            Command feedback = new Command();
            feedback.setCommand("Sucessfully Connected to the server with ID " + lightID);
            feedback.setCommandTo(lightID);
            distribute(feedback);
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        }
        try {
            if (handlers.size() > MAXCLIENTS) {
                Command killCommand = new Command();
                killCommand.setCommand("Connection refused: The Server is Full");
                killCommand.setCommandTo(lightID);
                distribute(killCommand);
                LocalServer.writeToLog("New Client conection refused from: " + clientIP);
                return;
            }
            LocalServer.writeToLog("New Traffic Light at " + clientIP + " connected as " + lightID);

            while (run) {
                try {
                    command = (Command) input.readObject();

                    // This client's command
                    if (!command.getLightID().equals(lightID)) {
                        if (!availableID(command.getLightID())) {
                            Command killCommand = new Command();
                            killCommand.setCommand("LightID " + " is already in use");
                            killCommand.setCommandTo(lightID);
                            distribute(killCommand);
                            LocalServer.writeToLog("New Client conection refused from: " + clientIP);
                            return;
                        }
                        // If command from someone else and if the Command is a quit command
                    } else if (command.getQuit()) {
                        Command killCommand = new Command();
                        killCommand.setCommand(lightID + " has left the discussion.");
                        distribute(killCommand);
                        
                        //if the Command is a server quit command
                    } else if (command.getServerQuit()) {
                        Command killServerCommand = new Command();
                        killServerCommand.setCommand("The Server has been terminated by " + lightID);
                        distribute(killServerCommand);
                        LocalServer.terminateServer();
                    }
                } catch (ClassNotFoundException e) {
                }
            }
        } catch (IOException ex) {
        } finally {
            handlers.remove(this);
            try {
                socket.close();
            } catch (IOException ex) {
            }
        }
    }

    private static boolean availableID(String ID) {
        synchronized (handlers) {
            for (SystemHandler handler : handlers) {
                if (handler.lightID.toLowerCase().equals(ID.toLowerCase())) {
                    return false;
                }
            }
        }
        return true;
    }

    public void terminateHandler() {
        run = false;
    }

    private static void sendCommand(SystemHandler handler, Command localCommand) {
        try {
            synchronized (handler.output) {
                handler.output.writeObject(localCommand);
            }
            handler.output.flush();
        } catch (IOException ex) {
            handler.terminateHandler();
        }
    }

    private static void distribute(Command localCommand) {
        synchronized (handlers) {
            for (SystemHandler handler : handlers) {
                localCommand.setLightID(handler.lightID);
                if (localCommand.getCommandTo().equals("")) {
                    sendCommand(handler, localCommand);
                } else {
                    if (handler.lightID.equals(localCommand.getCommandTo())) {
                        sendCommand(handler, localCommand);
                        return;
                    }
                }
            }
        }
    }
}