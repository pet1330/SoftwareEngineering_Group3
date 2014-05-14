package TrafficLightSystem;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Group 3
 */
public class TrafficLightSystem {

    SendThread sendThread;
    ReceivedThread recieveThread;
    ControlSystem controlSystem;
    Thread controlThread;
    Thread thread, thread2;
    public boolean run = true;

    // Traffic light array lists (used for storing the number of vehicles at each light)
    volatile ConcurrentLinkedQueue<TrafficData> Light1 = new ConcurrentLinkedQueue<>();
    volatile ConcurrentLinkedQueue<TrafficData> Light2 = new ConcurrentLinkedQueue<>();
    volatile ConcurrentLinkedQueue<TrafficData> Light3 = new ConcurrentLinkedQueue<>();
    volatile ConcurrentLinkedQueue<TrafficData> Light4 = new ConcurrentLinkedQueue<>();

    public Window Map;
    static SendThread sendData;

    public static void StartSystem() {
        try {
            final TrafficLightSystem tls = new TrafficLightSystem();

            // Create a new socket
            Socket sock = new Socket("localhost", 5000);

            // Start listening for data coming in
            tls.recieveThread = new ReceivedThread(sock, tls);
            tls.thread2 = new Thread(tls.recieveThread);
            tls.thread2.start();

            // Create an object to send data out
            sendData = new SendThread(sock, tls);

            // Send some data to the server
            sendData.sendData("REGI");
            sendData.sendData("BEGI");

            // Start the simulation
            tls.controlSystem = new ControlSystem(tls);
            tls.controlThread = new Thread(tls.controlSystem);
            tls.controlThread.start();

            //Launch GUI
            tls.Map = new Window(tls);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean IsAlive() {
        return run;
    }

    public void Terminate() {
        run = false;
    }

    // Add a vehicle to the correct traffic light list
    public void addToList(String start, String end) {
        int s = Integer.parseInt(start);
        int e = Integer.parseInt(end);
        switch (s) {
            case 0:
                Light1.add(new TrafficData(s, e));
                break;
            case 1:
                Light2.add(new TrafficData(s, e));
                
                break;
            case 2:
                Light3.add(new TrafficData(s, e));
                
                break;
            case 3:
                Light4.add(new TrafficData(s, e));
                
                break;
        }
    }

    // Report on the current traffic light stats (number of vehicles at each light)
    public void reportTrafficLightStatus(Boolean sendToServer) {
        if (sendToServer == true) {
            String report = "";

            report += "Traffic light 1: " + Light1.size() + ":";
            report += "Traffic light 2: " + Light2.size() + ":";
            report += "Traffic light 3: " + Light3.size() + ":";
            report += "Traffic light 4: " + Light4.size() + ";";

            sendData.sendData(report);
        } else {
            System.out.println("Current vehicle statistics:");
            System.out.println("Traffic light 1: " + Light1.size());
            System.out.println("Traffic light 2: " + Light2.size());
            System.out.println("Traffic light 3: " + Light3.size());
            System.out.println("Traffic light 4: " + Light4.size() + "\n");
        }
    }

    // Process the commands sent from clients server (getstate; will reutrn the light stats)
    public void process(String toProcess) {
        if (toProcess.toLowerCase().trim().equals("getstate;")) {
            reportTrafficLightStatus(true);
        } else {
            String[] split = toProcess.split(":");

            int from = Integer.parseInt(split[0]);
            int to = Integer.parseInt(split[1]);
        }
    }

    public static void main(String[] args) {
        // Start the system
        StartSystem();
    }
}
