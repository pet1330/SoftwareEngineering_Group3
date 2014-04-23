package TrafficLightSystem;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author Group 3
 */
public class TrafficLightSystem {

    SendThread sendThread;
    ReceivedThread recieveThread;
    ControlSystem controlSystem;
    Thread controlThread;
    Thread thread;
    Thread thread2;
    public boolean run = true;
    int[] trafficNumber = {0, 0, 0, 0};
    ArrayList<TrafficData> Light1 = new ArrayList<>();
    ArrayList<TrafficData> Light2 = new ArrayList<>();
    ArrayList<TrafficData> Light3 = new ArrayList<>();
    ArrayList<TrafficData> Light4 = new ArrayList<>();

    public static void StartSystem() {
        try {
            TrafficLightSystem tls = new TrafficLightSystem();
            //Socket sock = new Socket("192.168.0.103", 5000);

            // Create a new socket
            Socket sock = new Socket("localhost", 5000);

            // Start listening for data coming in
            tls.recieveThread = new ReceivedThread(sock, tls);
            tls.thread2 = new Thread(tls.recieveThread);
            tls.thread2.start();

            // Create an object to send data out
            SendThread sendData = new SendThread(sock, tls);

            // Send some data to the server
            sendData.sendData("REGI");
            sendData.sendData("BEGI");
            
            // Start the simulation
            tls.controlSystem = new ControlSystem(tls);
            tls.controlThread = new Thread(tls.controlSystem);
            tls.controlThread.start();

            /*tls.sendThread = new SendThread(sock, tls);
             tls.thread = new Thread(tls.sendThread);
             tls.thread.start();*/
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

    public void addToList(String start, String end) {

        switch (start) {
            case "1":
                Light1.add(new TrafficData(start, end));
                break;
            case "2":
                Light2.add(new TrafficData(start, end));
                break;
            case "3":
                Light3.add(new TrafficData(start, end));
                break;
            case "4":
                Light4.add(new TrafficData(start, end));
                break;
        }
    }

    public void process(String toProcess) {
        if (toProcess.equals("John's string for requesting the number of cars at each of the directions + ;")) {

        } else {
            String[] split = toProcess.split(":");

            int from = Integer.parseInt(split[0]);
            int to = Integer.parseInt(split[1]);
        }
    }

    public static void main(String[] args) {
        StartSystem();
    }
}
