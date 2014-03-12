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
    private PrintWriter printer;
    private BufferedReader reader;
    private Socket socket;
    private boolean run = true;

    public NationalServerClient(LocalServer _ls) throws IOException {
        ls = _ls;
        socket = new Socket("LocalHost", 5000);
        try {
            printer = new PrintWriter(this.socket.getOutputStream());
        } catch (IOException ex) {
        }
    }

    public void SendNationalFeedback(String toSend) {
        printer.print(toSend);
        printer.flush();
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printer.println("Start Sending");
            printer.flush();
            while (true) {
                while (run && ls.state()) {
                    String inputLine;
                    while ((inputLine = reader.readLine()) != null) {
                        System.out.println("Got to this line");
                        ls.processNationalServerCommand(inputLine);
                        System.out.println(inputLine);
                        printer.println("Start Sending");
                        printer.flush();
                    }
                    System.out.println("OUTBREAK");
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
