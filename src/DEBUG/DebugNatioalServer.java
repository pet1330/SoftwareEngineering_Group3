package DEBUG;

import java.net.*;
import java.io.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DebugNatioalServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    DebugNatioalServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Debug Server Starting on " + port + "\nWaiting for client to connect...");
            clientSocket = serverSocket.accept();
            System.out.println("Client " + clientSocket.getInetAddress() + " Accepted");
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            Random r = new Random();

            while (true) {
                int a = r.nextInt(5);
                int b = r.nextInt(5);
                if (a == b) {
                    continue;
                }

                Thread.sleep(600);

                out.println(a + ":" + b + ";");
            }
        } catch (IOException e) {
            clientSocket = null;
            serverSocket = null;
            out = null;
            in = null;
        } catch (InterruptedException ex) {
            Logger.getLogger(DebugNatioalServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        new DebugNatioalServer(5000);
    }
}
