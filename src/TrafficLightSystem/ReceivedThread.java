package TrafficLightSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 *
 * @author Group 3
 */
class ReceivedThread implements Runnable {

    Socket socket;
    BufferedReader recieve;
    TrafficLightSystem ControlCentre;

    public ReceivedThread(Socket sock, TrafficLightSystem syst) {
        // Assign the socket to a global variable for use within this class
        socket = sock;
        ControlCentre = syst;
    }

    @Override
    public void run() {
        try {
            recieve = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msgRecieved;
            
            // Check to see if a message has been recieved
            while ((msgRecieved = recieve.readLine()) != null) {

                // Remove the ; from the end of the string
                if (msgRecieved.endsWith(";")) {
                    msgRecieved = msgRecieved.substring(0, msgRecieved.length() - 1);
                }

                // Split the string by :
                StringTokenizer st = new StringTokenizer(msgRecieved, ":");

                // Check for elements and assign to correct lists
                if (st.hasMoreTokens()) {
                    String start;
                    String end;

                    start = st.nextToken();
                    end = st.nextToken();
                    if (start.length() == 1 && end.length() == 1) {
                        ControlCentre.addToList(start, end);
                    }
                }

                if (!ControlCentre.IsAlive()) {
                    break;
                }
            }
        } catch (IOException e) {
        }
    }
}
