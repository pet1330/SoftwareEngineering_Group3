package DEBUG;

import java.net.*;
import java.io.*;

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

            String inputLine;
            while (true) {
                while ((inputLine = in.readLine()) != null) {
                    out.println(inputLine);
                    System.out.println(inputLine);
                }
            }
        } catch (IOException e) {
            clientSocket = null;
            serverSocket = null;
            out = null;
            in = null;
        }
    }

    public static void main(String[] args) {
        new DebugNatioalServer(5000);
    }
}
