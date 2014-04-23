package TrafficLightSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Group 3
 */
class SendThread {

    Socket socket;
    PrintWriter print;
    BufferedReader input;
    TrafficLightSystem ControlCentre;

    public SendThread(Socket sock, TrafficLightSystem syst) {
        socket = sock;
        ControlCentre = syst;
    }
    
    // Send some data to the server
    public void sendData(String data)
    {
        try {
            // Check to see if the socket is connected still
            if (socket.isConnected()) 
            {
                // Create a new PrintWriter object
                this.print = new PrintWriter(socket.getOutputStream(), true);
                
                // Read in the data we want to send to the server
                this.print.println(data);
                
                // Flush the pipes (send everything off)
                this.print.flush();
                
                // Output a success message
                System.out.println("The following data was sent successfully: " + data);
            }
        } 
        catch (IOException e) 
        {
            // There was an error so alert the user
            System.out.println("Error sending the following data: " + data);
        }
    }

    /*@Override
    public void run() {
        try {
            if (socket.isConnected()) {
                this.print = new PrintWriter(socket.getOutputStream(), true);
                while (ControlCentre.IsAlive()) {
                    input = new BufferedReader(new InputStreamReader(System.in));
                    String msgtoServerString;
                    msgtoServerString = input.readLine();
                    this.print.println(msgtoServerString);
                    this.print.flush();

                    if (msgtoServerString.equals("EXIT")) {
                        ControlCentre.Terminate();
                    } else {
                        ControlCentre.process(msgtoServerString);
                    }
                }
                socket.close();
            }
        } catch (IOException e) {
        }
    }*/
}
