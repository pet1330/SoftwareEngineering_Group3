package TrafficLightSystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Group 3
 */
public class TrafficLightSystem {

    SendThread sendThread;
    ReceivedThread recieveThread;
    ControlSystem controlSystem;
    UserInput uInput;
    Thread controlThread;
    Thread thread, thread2;
    public boolean run = true;
    ArrayList<TrafficData> Light1 = new ArrayList<>();
    ArrayList<TrafficData> Light2 = new ArrayList<>();
    ArrayList<TrafficData> Light3 = new ArrayList<>();
    ArrayList<TrafficData> Light4 = new ArrayList<>();
    public static Window Map;

    public static void StartSystem() {
        try {
            TrafficLightSystem tls = new TrafficLightSystem();

            // Create a new socket
            Socket sock = new Socket("localhost", 5000);

            // Start listening for data coming in
            tls.recieveThread = new ReceivedThread(sock, tls);
            tls.thread2 = new Thread(tls.recieveThread);
            tls.thread2.start();

            // Start listening for user input
            tls.uInput = new UserInput(tls);
            tls.thread = new Thread(tls.uInput);
            tls.thread.start();

            // Create an object to send data out
            SendThread sendData = new SendThread(sock, tls);

            // Send some data to the server
            sendData.sendData("REGI");
            sendData.sendData("BEGI");

            // Start the simulation
            tls.controlSystem = new ControlSystem(tls);
            tls.controlThread = new Thread(tls.controlSystem);
            tls.controlThread.start();

            // Launch GUI
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JPanel ControlPanel = new JPanel();

                    JButton holdWE = new JButton("Hold Lights 1 & 3");
                    holdWE.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("Hold Lights 1 & 3");
                        }
                    });

                    JButton holdNS = new JButton("Hold Lights 2 & 4");
                    holdNS.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("Hold Lights 2 & 4");
                        }
                    });

                    JButton stats = new JButton("Stats");
                    stats.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("Reporting Stats");
                        }
                    });

                    ControlPanel.add(holdWE);
                    ControlPanel.add(holdNS);
                    ControlPanel.add(stats);

                    Map = new Window();
                    JFrame aFrame = new JFrame();
                    //Create a split pane with the two scroll panes in it.
                    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, Map, ControlPanel);
                    splitPane.setOneTouchExpandable(false);
                    splitPane.setDividerLocation(322);
                    aFrame.add(splitPane);
                    aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    aFrame.pack();
                    aFrame.setSize(655, 450);
                    aFrame.setResizable(false);
                    aFrame.setVisible(true);
                }
            });

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean IsAlive() {
        return run;
    }

    public void Terminate() {
        run = false;
    }

    public void addToList(String start, String end) {

        int e = Integer.parseInt(end);

        switch (start) {
            case "1":
                Light1.add(new TrafficData(0, e));
                break;
            case "2":
                Light2.add(new TrafficData(1, e));
                break;
            case "3":
                Light3.add(new TrafficData(2, e));
                break;
            case "4":
                Light4.add(new TrafficData(3, e));
                break;
        }
    }

    public void reportTrafficLightStatus() {
        System.out.println("Current vehicle statistics:");
        System.out.println("Traffic light 1: " + Light1.size());
        System.out.println("Traffic light 2: " + Light2.size());
        System.out.println("Traffic light 3: " + Light3.size());
        System.out.println("Traffic light 4: " + Light4.size() + "\n");
    }

    public void process(String toProcess) {
        if (toProcess.equals("John's string for requesting the number of cars at each of the directions + ;")) {
            // TODO LOGIC CODE HERE
        } else {
            String[] split = toProcess.split(":");

            int from = Integer.parseInt(split[0]);
            int to = Integer.parseInt(split[1]);
        }
    }

    public static void main(String[] args) {
        StartSystem();
    }
}
