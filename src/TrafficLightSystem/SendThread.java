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
class SendThread implements Runnable {

    Socket socket;
    PrintWriter print;
    BufferedReader input;
    TrafficLightSystem ControlCentre;

    public SendThread(Socket sock, TrafficLightSystem syst) {
        socket = sock;
        ControlCentre = syst;
    }

    @Override
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
    }
}
