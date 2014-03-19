package TrafficLightSystem;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author computing
 */
public class SendThread extends Thread {
    
    private Socket SendSocket;
    
    
    public SendThread(Socket RecieveSocket)
    {
        SendSocket = RecieveSocket;
    }
    
    public void run(){
        try {       
            OutputStream SendStream = SendSocket.getOutputStream();
            PrintWriter SendPrint = new PrintWriter(SendStream);
            SendPrint.print("HELP");
            SendPrint.flush();
            SendSocket.close(); 
        } catch (IOException ex) {
            Logger.getLogger(SendThread.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
            
    
}
