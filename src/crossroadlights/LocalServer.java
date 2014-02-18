package crossroadlights;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author group 3
 */
public class LocalServer extends Thread {

    private static int currentSequence = 0;
    private static int nextLightSequence;
    private TrafficLight[] lightSystem = {new TrafficLight(0), new TrafficLight(1), new TrafficLight(2), new TrafficLight(3)};
    private static PrintWriter log;
    private static boolean run = true;
    private static boolean logCreated = false;
    private static int sequenceMax = 2; // Maximum number of sequences for the traffic light system

    public LocalServer(int port) throws IOException {
        writeToLog("LocalServer initalising on port " + port);
        ServerSocket server = new ServerSocket(port);
        server.setSoTimeout(1000);
        while (run) {
            Socket client = null;
            try {
                client = server.accept();
            } catch (SocketTimeoutException e) {
            }
            if (client != null) {
                System.out.println("Client " + client.getInetAddress() + "is connected to the Server");
                ClientHandler handler = new ClientHandler(client, this);
                writeToLog("New Client connection accepted from: " + client.getInetAddress());
                handler.start();
            }
        }
        if (!run) {
            synchronized (ClientHandler.handlers) {

                Iterator myHandlers = ClientHandler.handlers.iterator();

                while (myHandlers.hasNext()) {
                    ClientHandler handler = (ClientHandler) myHandlers.next();
                    handler.terminateHandler();
                }
            }
            writeToLog("Server closed");
            log.close();
            System.exit(0);
        }
    }

    //Deprecated Function
    public static void Start() {
        LocalServer syst = new LocalServer();
        Button button = Button.start();
        new Thread(syst).start();
        while (true) {
            if (button.checkButton()) 
            {
                syst.setAllLightsRed();
                syst.waitfor(5);
                button.resetButton();
            } else {    
                
                nextLightSequence = getNextLightSequence();
                
                switch (syst.nextLightSequence) {
                    case 0:
                        syst.lightSystem[0].setLight(TrafficLight.Orange);
                        syst.lightSystem[1].setLight(TrafficLight.Red);
                        syst.lightSystem[2].setLight(TrafficLight.Red);
                        syst.lightSystem[3].setLight(TrafficLight.Orange);
                        syst.PrintMap();
                        syst.waitfor(2);
                        syst.lightSystem[0].setLight(TrafficLight.Green);
                        syst.lightSystem[1].setLight(TrafficLight.Red);
                        syst.lightSystem[2].setLight(TrafficLight.Red);
                        syst.lightSystem[3].setLight(TrafficLight.Green);
                        syst.PrintMap();
                        syst.waitfor(5);
                        syst.lightSystem[0].setLight(TrafficLight.Orange);
                        syst.lightSystem[1].setLight(TrafficLight.Red);
                        syst.lightSystem[2].setLight(TrafficLight.Red);
                        syst.lightSystem[3].setLight(TrafficLight.Orange);
                        syst.PrintMap();
                        syst.waitfor(2);
                        syst.setAllLightsRed();
                        syst.PrintMap();
                        syst.waitfor(2);
                        break;
                    case 1:
                        syst.lightSystem[0].setLight(TrafficLight.Red);
                        syst.lightSystem[1].setLight(TrafficLight.Orange);
                        syst.lightSystem[2].setLight(TrafficLight.Orange);
                        syst.lightSystem[3].setLight(TrafficLight.Red);
                        syst.PrintMap();
                        syst.waitfor(2);
                        syst.lightSystem[0].setLight(TrafficLight.Red);
                        syst.lightSystem[1].setLight(TrafficLight.Green);
                        syst.lightSystem[2].setLight(TrafficLight.Green);
                        syst.lightSystem[3].setLight(TrafficLight.Red);
                        syst.PrintMap();
                        syst.waitfor(5);
                        syst.lightSystem[0].setLight(TrafficLight.Red);
                        syst.lightSystem[1].setLight(TrafficLight.Orange);
                        syst.lightSystem[2].setLight(TrafficLight.Orange);
                        syst.lightSystem[3].setLight(TrafficLight.Red);
                        syst.PrintMap();
                        syst.waitfor(2);
                        syst.setAllLightsRed();
                        syst.PrintMap();
                        syst.waitfor(2);
                        break;
                    default:
                        System.out.println("Default ERROR");
                        syst.setAllLightsRed();
                        syst.waitfor(5);
                        break;
                }
            }
        }

    }
    
    private static int getNextLightSequence()
    {
        // Maybe get new sequence from server???
        if(currentSequence == 0)
        {
            currentSequence = 1;
            return 1;
        }
        else
        {
            currentSequence = 0;
            return 0;
        }
    }
    
    private void defaultSequence()
    {
        LocalServer syst = new LocalServer();
        Button button = Button.start();
        new Thread(syst).start();
        
        currentSequence = 0;
        
        while (true) {
            if (button.checkButton()) {
                syst.setAllLightsRed();
                syst.waitfor(5);
                button.resetButton();
            } else {                
                switch (syst.currentSequence) {
                    case 0:
                        syst.PrintMap();
                        syst.lightSystem[0].setLight(TrafficLight.Green);
                        syst.lightSystem[1].setLight(TrafficLight.Red);
                        syst.lightSystem[2].setLight(TrafficLight.Red);
                        syst.lightSystem[3].setLight(TrafficLight.Green);
                        syst.waitfor(5);
                        break;
                    case 1:
                        syst.PrintMap();
                        syst.lightSystem[0].setLight(TrafficLight.Red);
                        syst.lightSystem[1].setLight(TrafficLight.Green);
                        syst.lightSystem[2].setLight(TrafficLight.Green);
                        syst.lightSystem[3].setLight(TrafficLight.Red);
                        syst.waitfor(5);
                        break;
                    default:
                        System.out.println("Default ERROR");
                        syst.setAllLightsRed();
                        syst.waitfor(5);
                        break;
                }
                
                // Calculate the next sequence
                if (currentSequence == (sequenceMax - 1))
                {
                    currentSequence = 0;
                }
                else
                {
                    currentSequence++;
                }
            }
        }

    }

    //Deprecated Function
    private LocalServer() {
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
}
