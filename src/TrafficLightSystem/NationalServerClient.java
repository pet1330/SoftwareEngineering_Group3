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
public class NationalServerClient extends Thread {

    private LocalServer ls;
    public PrintWriter printer;
    private BufferedReader reader;
    private Socket socket;
    private boolean run = true;

    public NationalServerClient(LocalServer _ls) throws IOException {
        ls = _ls;
        socket = new Socket("LocalHost", 5000);
        //socket = new Socket("192.168.0.103", 5000);
        
        LocalServer.writeToLog("Starting connection on port 5000");
        try {
            printer = new PrintWriter(this.socket.getOutputStream());
        } catch (IOException ex) {
        }
    }

    public static void SendNationalFeedback(String toSend, PrintWriter printer) {
        printer.print(toSend);
        printer.flush();
        LocalServer.writeToLog("Command " + toSend + " sent");
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //printer.println("HELP");
            //printer.flush();
            while (true) {
                while (run && ls.state()) {
                    String inputLine;
                    while ((inputLine = reader.readLine()) != null) {
                        ls.processNationalServerCommand(inputLine);
                        System.out.println(inputLine);
                        //printer.println("HELP");
                        //printer.flush();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("he");
        } finally {
            try {
                System.out.println("hel");
                socket.close();
            } catch (IOException ex) {
            }
        }
    }

    public void terminateServer() {
        run = false;
    }
}
