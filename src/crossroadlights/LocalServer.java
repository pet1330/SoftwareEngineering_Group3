package crossroadlights;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author group 3
 */
public class LocalServer extends Thread {

    private int currentSequence = 0;
    private TrafficLight[] lightSystem = {new TrafficLight(), new TrafficLight(), new TrafficLight(), new TrafficLight()};
    private static PrintWriter log;
    private static boolean run = true;
    private static boolean logCreated = false;
    private ServerSocket server;

    private LocalServer() {
    }

    public static void Start(int port) throws IOException {
        LocalServer syst = new LocalServer();
        writeToLog("LocalServer initalising on port " + port);
        syst.server = new ServerSocket(port);
        syst.server.setSoTimeout(1000);

        while (run) {
            Socket client = null;
            try {
                client = syst.server.accept();
            } catch (SocketTimeoutException e) { 
            /* Client not attempting to connect */}
            if (client != null) {
                System.out.println("Client " + client.getInetAddress() + "is connected to the Server");
                ClientHandler handler = new ClientHandler(client, syst);
                writeToLog("New Client connection accepted from: " + client.getInetAddress());
                handler.start();
            }
        }

        if (!run) {
            
            
            synchronized (ClientHandler.handlers) {
                for(ClientHandler handler : ClientHandler.handlers){
                    handler.terminateHandler();
                }
            }
            writeToLog("Server closed");
            log.close();
            System.exit(0);
        }
    }

    public static void writeToLog(String toLog) {
        if (!logCreated) {
            try {
                log = new PrintWriter(new FileWriter("LocalServerLog.txt", true), true);
            } catch (IOException e) {
            }
            logCreated = true;
        }
        log.println(Calendar.getInstance().getTime().toString() + " - " + toLog);
    }

    public static void terminateServer() {
        run = false;
    }

    public static boolean state() {
        return run;
    }

    //Deprecated Function
    private void setAllLightsRed() {
        for (TrafficLight L : lightSystem) {
            L.setLight(0);
        }
    }

    //Deprecated Function
    private void PrintMap() {

        for (int i = 0; i < 321; i++) {
            System.out.println("\b");
        }
        System.out.println("     |   |   |      ");
        System.out.println("     |       |      ");
        System.out.println("     |   |   |      ");
        System.out.println("     |      " + lightSystem[0].getLight() + "|      ");
        System.out.println("------   |   -------");
        System.out.println("     " + lightSystem[1].getLight() + "              ");
        System.out.println("                    ");
        System.out.println("- - -        - - - -");
        System.out.println("                    ");
        System.out.println("             " + lightSystem[2].getLight() + "      ");
        System.out.println("------       -------");
        System.out.println("     |" + lightSystem[3].getLight() + "  |   |      ");
        System.out.println("     |       |      ");
        System.out.println("     |   |   |      ");
        System.out.println("     |       |      ");
        System.out.println("     |   |   |      ");
    }

    //Deprecated Function
    private void waitfor(int seconds) {
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < (1000 * seconds)) {
            //just keep looping...
        }
    }
}
