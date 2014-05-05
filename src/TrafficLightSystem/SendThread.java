package TrafficLightSystem;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Group 3
 */
class SendThread {

    Socket socket;
    TrafficLightSystem ControlCentre;

    public SendThread(Socket sock, TrafficLightSystem syst) {
        socket = sock;
        ControlCentre = syst;
    }

    // Send some data to the server
    public void sendData(String data) {
        try {
            // Check to see if the socket is connected still
            if (socket.isConnected()) {
                // Create a new PrintWriter object
                PrintWriter print = new PrintWriter(socket.getOutputStream(), true);

                // Read in the data we want to send to the server
                print.println(data);

                // Flush the pipes (send everything off)
                print.flush();

                // Output a success message
                System.out.println("The following data was sent successfully: " + data);
            }
        } catch (IOException e) {
            // There was an error so alert the user
            System.out.println("Error sending the following data: " + data);
        }
    }
}
