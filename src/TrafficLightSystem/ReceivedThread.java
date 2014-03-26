package TrafficLightSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Group 3
 */
class ReceivedThread implements Runnable {

    Socket socket;
    BufferedReader recieve;
    TrafficLightSystem ControlCentre;

    public ReceivedThread(Socket sock, TrafficLightSystem syst) {
        socket = sock;
        ControlCentre = syst;
    }

    @Override
    public void run() {
        try {
            recieve = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msgRecieved;
            while ((msgRecieved = recieve.readLine()) != null) {
                System.out.println("Received : " + msgRecieved);

                if (!ControlCentre.IsAlive()) {
                    break;
                }
            }
        } catch (IOException e) {
        }
    }
}
