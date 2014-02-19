package TrafficLightSystem;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author group 3
 */
public class LocalServer extends Thread {

    private static PrintWriter log;
    private static boolean run = true;
    private static boolean logCreated = false;
    
    public LocalServer(int port) throws IOException {
        ServerSocket server = new ServerSocket(port);
        writeToLog("LocalServer Created on port " + port);
        System.out.println("LocalServer Created on port " + port);
        server.setSoTimeout(500);

        while (run) {
            Socket client = null;
            try {
                client = server.accept();
            } catch (SocketTimeoutException e) {
            }
            if (client != null) {
                System.out.println("Connection to Server accepted from: " + client.getInetAddress());
                SystemHandler handler = new SystemHandler(client);
                writeToLog("New Client connection accepted from: " + client.getInetAddress());
                Thread handleThread = new Thread(handler);
                handleThread.start();
            }
        }

        if (!run) {
            synchronized (SystemHandler.handlers) {
                for (SystemHandler handler : SystemHandler.handlers) {
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
        Calendar dateTime = Calendar.getInstance();
        log.println(dateTime.getTime().toString() + " - " + toLog);
    }

    public static void terminateServer() {
        run = false;
    }

    public static boolean state() {
        return run;
    }

    public static void main(String[] args) throws IOException {
        new LocalServer(1560);
    }
}