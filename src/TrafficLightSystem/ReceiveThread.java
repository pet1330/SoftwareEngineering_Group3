/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TrafficLightSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author computing
 */
public class ReceiveThread extends Thread {

    Socket SendSocket;
    public PrintWriter printer;
    private BufferedReader reader;
    //private Socket socket;
    private boolean run = true;
    LocalServer ls;

    public ReceiveThread(Socket RecieveSocket) {
        SendSocket = RecieveSocket;
    }

    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(SendSocket.getInputStream()));
            
            //printer.println("HELP");
            //printer.flush();
            
            while (true) {
                while (run && ls.state()) {
                    String inputLine;
                    while ((inputLine = reader.readLine()) != null) {
                        System.out.println(inputLine);
                        ls.processNationalServerCommand(inputLine);
                         System.out.println(inputLine);
                        //printer.println("HELP");
                        //printer.flush();
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("he");
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }
}
