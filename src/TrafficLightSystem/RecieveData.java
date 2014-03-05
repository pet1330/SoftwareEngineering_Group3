/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TrafficLightSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author computing
 */
public class RecieveData implements Runnable {

    public RecieveData() {

    }

    public void run() {
        
            try {
                Socket socket = new Socket("Localhost", 5000);
                while(true)
                {
                    InputStream input = socket.getInputStream();

                    BufferedReader in = new BufferedReader(new InputStreamReader(input));
                    System.out.println(in.readLine());
                }

            } catch (IOException ex) {
                //Logger.getLogger(SendData.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
}
