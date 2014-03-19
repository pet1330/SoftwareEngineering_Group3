package TrafficLight;

import Command.Command;
import java.io.*;
import java.util.Scanner;

public class TrafficLight implements Runnable {

    private TrafficLightClient client;
    private boolean run = true;
    private BufferedReader input;
    private Thread uiListener;

    public TrafficLight(TrafficLightClient myClient) {
        client = myClient;
        input = new BufferedReader(new InputStreamReader(System.in));
        uiListener = new Thread(this);
        uiListener.start();
    }

    public void run() {
        while (run) {

            // Process client side commands here

            try {
                String inputedText = input.readLine();
                if (inputedText.equals("--quit")) {
                    Command killCommand = new Command();
                    killCommand.setQuit(true);
                    client.sendCommand(killCommand);
                    client.terminateClient();
                    disconnect();
                }

                if (inputedText.equals("--quitserver")) {
                    Command killCommand = new Command();
                    killCommand.setServerQuit(true);
                    client.sendCommand(killCommand);
                }
                
                if (inputedText.equals("push button")) {
                    System.out.println("Which traffic light was the button pressed at? ");
                    Scanner sc = new Scanner(System.in);
                    sc.nextLine();
                    Command buttonPush = new Command();
                    buttonPush.setCommand("button push");
                    buttonPush.setCommandFrom(sc.toString());
                    client.sendCommand(buttonPush);
                }
                
                if (inputedText.equals("car")) {
                    Command vehicleDetected = new Command();
                    vehicleDetected.setCommand("vehicle detected");
                    client.sendCommand(vehicleDetected);
                }
                
                if(inputedText.startsWith("!"))
                {
                    Command sendCommand = new Command();
                    sendCommand.setCommand(inputedText);
                    client.sendCommand(sendCommand);
                }
            } catch (IOException ex) {
            }
        }
    }

    public void TrafficLightUserLog(Command localCommand) {
        String line = localCommand.getCommand();
        if (!line.equals("")) {
            System.out.println(line);
        }
    }

    public void disconnect() {
        run = false;
        try {
            input.close();
        } catch (IOException ex) {
        }
        System.out.println("Connection to the Server Lost\n");
    }

    private void PrintMap(int colour1, int colour2, int colour3, int colour4) {

        for (int i = 0; i < 321; i++) {
            System.out.println("\b");
        }
        System.out.println("     |   |   |      ");
        System.out.println("     |       |      ");
        System.out.println("     |   |   |      ");
        System.out.println("     |      " + colour1 + "|      ");
        System.out.println("------   |   -------");
        System.out.println("     " + colour2 + "              ");
        System.out.println("                    ");
        System.out.println("- - -        - - - -");
        System.out.println("                    ");
        System.out.println("             " + colour3 + "      ");
        System.out.println("------       -------");
        System.out.println("     |" + colour4 + "  |   |      ");
        System.out.println("     |       |      ");
        System.out.println("     |   |   |      ");
        System.out.println("     |       |      ");
        System.out.println("     |   |   |      ");
    }

    private void waitfor(int seconds) {
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < (1000 * seconds)) {
            //just keep looping...
        }
    }
}
