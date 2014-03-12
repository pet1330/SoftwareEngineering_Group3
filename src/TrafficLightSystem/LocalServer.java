package TrafficLightSystem;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author group 3
 *
 */
public class LocalServer{

    private static PrintWriter log;
    private static boolean logCreated = false;
    private boolean run = true;
    private NationalServerClient nsc;
    

    private LocalServer() {
    }

    public static void StartServer(int port) throws IOException {
        LocalServer ls = new LocalServer();
        ls.nsc = new NationalServerClient(ls);
        ls.nsc.SendNationalFeedback("Hello");
        ls.nsc.start();
        ls.ServerRun(port);
    }

    private void ServerRun(int port) throws IOException {
        ServerSocket server = new ServerSocket(port);
        writeToLog("LocalServer Created on port " + port);
        server.setSoTimeout(50);
        while (run) {
            Socket client = null;
            try {
                client = server.accept();
            } catch (SocketTimeoutException e) {
            }

            if (client != null) {
                SystemHandler handler = new SystemHandler(client, this);
                //SystemHandler.handlers.add(handler);
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
    
    public void processNationalServerCommand(String toProcess) {
        writeToLog("Processed Command: " + toProcess);
        System.out.println("RESEAVED");
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
        System.out.println(dateTime.getTime().toString() + " - " + toLog);
    }

    public void terminateServer() {
        run = false;
    }

    public boolean state() {
        return run;
    }

    public static void main(String[] args) throws IOException {
        StartServer(1560);
    }

}