package TrafficLightSystem;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendData implements Runnable {
    
    public SendData()
    {
        
    }
    
    public void run()
    {
        /*try {
            Socket socket = new Socket("Localhost", 5000);
            OutputStream output = socket.getOutputStream();
            PrintWriter printer = new PrintWriter(output);
            printer.print("HELO");
            printer.flush();
            
            //BufferedReader in = new BufferedReader(new InputStreamReader(input));
            socket.close();
        } catch (IOException ex) {
            //Logger.getLogger(SendData.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    
    public static void main(String[] args)
    {
         try {
            Socket socket = new Socket("Localhost", 5000);
            OutputStream output = socket.getOutputStream();
            PrintWriter printer = new PrintWriter(output);
            printer.print("HELO");
            printer.flush();
            
            //BufferedReader in = new BufferedReader(new InputStreamReader(input));
            socket.close();
        } catch (IOException ex) {
            //Logger.getLogger(SendData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
