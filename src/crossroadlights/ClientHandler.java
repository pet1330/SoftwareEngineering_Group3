package crossroadlights;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Group 3
 */
public class ClientHandler extends Thread {

    public static ArrayList<ClientHandler> handlers = new ArrayList<ClientHandler>();
    private static final int MAXCLIENTS = 8;
    private static LocalServer server;
    private Command command;
    private String LightID;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private boolean run = true;
    private String clientIP;
    private static String IDMatch;

    public ClientHandler(Socket clientSocket, LocalServer InputServer) throws IOException {
        socket = clientSocket;
        clientIP = socket.getInetAddress().toString();
        server = InputServer;
        OutputStream os = socket.getOutputStream();
        output = new ObjectOutputStream(os);
        output.flush();
        InputStream is = socket.getInputStream();
        input = new ObjectInputStream(is);
    }

    @Override
    public void run() {
        try {

            command = (Command) input.readObject();
            LightID = command.getID();
            handlers.add(this);
            Command sndCommand = new Command();
            LocalServer.writeToLog("Traffic Light " + LightID + " connected");
            sndCommand.setComandTo(LightID);
            SendCommand(sndCommand);
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        }
        try {
            if (handlers.size() > MAXCLIENTS) {
                Command sndCommand = new Command();
                LocalServer.writeToLog("The Server is Full Connection Refused from Client " + clientIP);
                sndCommand.setComandTo(LightID);
                SendCommand(sndCommand);
                return;
            } else {
                LocalServer.writeToLog("New Client at " + clientIP + " logs in as: " + LightID);
            }
            while (run) {
                try {
                    command = (Command) input.readObject();

//==============================================================================
                    // @TODO LOGIC CODE HERE
                    // e.g. command processing
//==============================================================================
                } catch (ClassNotFoundException e) {
                }
            }
        } catch (IOException ex) {
        } finally {
            handlers.remove(this);
            if (handlers.size() <= MAXCLIENTS) {
                LocalServer.writeToLog("Client " + LightID + " at " + clientIP + " has disconnected from the Server");
            }
            Command updateMsg = new Command();
            SendCommand(updateMsg);
            try {
                socket.close();
            } catch (IOException ex) {
            }
        }
    }

    private static boolean checkID(String ID) {
        synchronized (handlers) {
            for (ClientHandler handler : handlers) {
                if (handler.LightID.toLowerCase().equals(ID.toLowerCase())) {
                    IDMatch = handler.LightID;
                    return false;
                }
            }
        }
        IDMatch = "";
        return true;
    }

    public void terminateHandler() {
        run = false;
        LocalServer.writeToLog("Client " + LightID + " at " + clientIP + " has disconnected from the Server");
    }

    private static void synchroSendCommand(ClientHandler handler, Command command) {
        try {
            synchronized (handler.output) {
                handler.output.writeObject(command);
            }
            handler.output.flush();
        } catch (IOException ex) {
            handler.terminateHandler();
        }
    }

    private static void SendCommand(Command command) {
        synchronized (handlers) {
            for (ClientHandler handler : handlers) {
                command.setID(handler.LightID);
                if (handler.LightID.equals(command.getComandTo())) {
                    synchroSendCommand(handler, command);
                    return;
                }
            }
        }
    }
}