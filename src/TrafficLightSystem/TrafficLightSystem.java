package TrafficLightSystem;

import java.io.IOException;
import java.net.*;

/**
 *
 * @author Group 3
 */
public class TrafficLightSystem {

    SendThread sendThread;
    ReceivedThread recieveThread;
    Thread thread;
    Thread thread2;
    public boolean run = true;
    int [] trafficNumber = {0,0,0,0};
    

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
            sendData.sendData("Hello there");
            
            sendData.sendData("Hello there again");
            
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

    public void process(String toProcess) {
        if (toProcess.equals("John's string for requesting the number of cars at each of the directions + ;")) {

        } else {
            String [] split = toProcess.split(":");
            
            int from = Integer.parseInt(split[0]);
            int to = Integer.parseInt(split[1]);
        }
    }

    public static void main(String[] args) {
        StartSystem();
    }
}
