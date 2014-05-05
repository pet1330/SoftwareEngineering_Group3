package JohnServer;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JFrame;

class TCPMultiServer {

    public static void main(String argv[]) throws Exception {
      //  String fromclient;
        //String toclient;

        BufferedReader inFromConsole = new BufferedReader(new InputStreamReader(System.in));

       // int maxConnections = 50;
       // int numOfConnections = 0;

        TrafficControlSystem.init();

        ServerButtons myButtons = new ServerButtons();
        myButtons.createAndShowGUI();

        HandleConnections mainServer = new HandleConnections();
        Thread t = new Thread(mainServer);
        t.start();
        while (true) {
            System.out.println("DEBUG: " + inFromConsole.readLine());
        }
    }
}

class HandleConnections implements Runnable {

    Socket clientConnection;
    ServerSocket server;
    HandleClient newClient;

    public void run() {
        try {
            server = new ServerSocket(5000);
            System.out.println("TCPServer Waiting for client(s) on port 5000");
        } catch (IOException e) {
            System.out.println("DEBUG: " + e);
        }

        while (true) {
            try {
                clientConnection = server.accept();
                newClient = new HandleClient(clientConnection);
                Thread tt = new Thread(newClient);
                tt.start();
            } catch (SocketException e) {
                System.out.println("Some exception: " + e);
            } catch (IOException e) {
                System.out.println("DEBUG: " + e);
            }
        }

    }

}

class TrafficData implements Runnable {

    Random rnd = new Random();
    int inRoad;
    int outRoad;
    String path;

    PrintWriter outToClient;

    public TrafficData(Socket clientConnection) {
        try {
            outToClient = new PrintWriter(clientConnection.getOutputStream(), true);
        } catch (Exception e) {
            System.out.println("DEBUG: " + e);
        }
    }

    public String generateTraffic() {
        inRoad = rnd.nextInt(4) + 1;
        outRoad = rnd.nextInt(4) + 1;
        while (outRoad == inRoad) {
            outRoad = rnd.nextInt(4) + 1;
        }

        path = inRoad + ":" + outRoad + ";";
        return path;
    }

    public void run() {
        while (true) {
            outToClient.println(generateTraffic());
            try {
                Thread.sleep(rnd.nextInt(4000) + 1000);
            } catch (Exception e) {
                System.out.println("Error in sleep: " + e);
            }
        }
    }
}

class HandleClient implements Runnable {

    private Socket clientConnection;
    BufferedReader inFromClient;
    PrintWriter outToClient;
    String clientMessages;
    String clientID;
    StringTokenizer st;
    boolean registered = false;

    public HandleClient(Socket clientConnection) {
        this.clientConnection = clientConnection;
        clientID = clientConnection.getInetAddress().getHostAddress();
    }

    public void closeClientConnection() {
        try {
            clientConnection.close();
        } catch (IOException e) {
            System.out.println("Cannot close socket: " + e);
        }
    }

    public void run() {
        System.out.println("Connected to client: " + clientConnection.getInetAddress());

        try {
            inFromClient = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
            outToClient = new PrintWriter(clientConnection.getOutputStream(), true);
        } catch (Exception e) {
            System.out.println("DEBUG: " + e);
        }

        while (!clientConnection.isClosed()) {
            try {
                clientMessages = inFromClient.readLine();
                System.out.println(clientConnection.getInetAddress() + ": " + clientMessages);

                if (clientMessages.equals("HELO")) {
                    outToClient.println("Ack! : " + clientConnection.getInetAddress());
                } else if (clientMessages.equals("HELP")) {
                    outToClient.println("HELP: List of commands are:");
                    outToClient.println("HELO: Send a ping! to the server, and receive a pong!");
                    outToClient.println("REGI: Register with the TCS Server system");
//                    outToClient.println("VALU: Receive current traffic details from the server");
                    outToClient.println("BEGI: Begin traffic simulation. Periodically you will receive traffic details.");
                    outToClient.println("EXIT: De-Register with the STS Server system");

                } else if (clientMessages.equals("REGI")) {
                    registered = TrafficControlSystem.registerClient("" + clientConnection.getInetAddress());
                    outToClient.println("REGI:SUCCESSFUL");
                } else if (clientMessages.equals("EXIT")) {
                    System.out.println("Client: " + clientConnection.getInetAddress() + " has exited.");
                } else if (clientMessages.equals("VALU")) {
                    outToClient.println("MSG: " + clientMessages);
                } else if (clientMessages.equals("BEGI")) {
                    TrafficControlSystem.sendTraffic(clientConnection);
                    outToClient.println("BEGIN: Traffic is starting...\n");
                }
            } catch (SocketException e) {
                System.out.println(clientID);
                System.out.println("Socket unexpectedly terminated!\n" + e);
                closeClientConnection();
            } catch (Exception e) {
                System.out.println("Exception:" + e);
                closeClientConnection();
            }
            if (clientMessages == null) {
                closeClientConnection();
            }
        }
        System.out.println("Closed Socket...");
    }
}

/**
 * The Class for the Server side logic
 *
 */
class TrafficControlSystem {

    static String clientList[][] = new String[20][201];
    static int clientCount = 0;
    static boolean registered = false;
    static boolean isExist = false;
    static String message = "";

    static StringTokenizer st;
    static BufferedReader input;
    static BufferedWriter output;

    static TrafficData trafficClient;

    static Timer shareTimer = new Timer();

    static long shareDelay = 90 * 1000;

    public static void init() throws Exception {
        String line = null;
        int count = 0;
    }

    public static boolean registerClient(String aName) {
        for (int i = 0; i < clientCount; i++) {
            if (clientList[i][0].equals(aName)) {
                System.out.println("DEBUG: " + aName + "- has previously registered.");
                registered = true;
                isExist = true;
            }
        }
        if (!isExist) {
            clientList[clientCount][0] = aName;
            registered = true;
            System.out.println("Registered: " + aName + " @ " + clientCount);
            clientCount++;
            isExist = false;
        }
        return registered;
    }

    public static void sendTraffic(Socket clientConnection) {
        try {
            trafficClient = new TrafficData(clientConnection);
            Thread tt = new Thread(trafficClient);
            tt.start();
        } catch (Exception e) {
            System.out.println("DEBUG: " + e);
        }
    }

    public static int findLoc(int i, String aComp) {
        int j = 0;
        do {
            if (clientList[i][j].contains(aComp)) {
                return j;
            } else {
                j++;
            }
        } while (clientList[i][j] != null);
        return j;
    }

    public static String[] clientShares(String aName) {
        for (int i = 0; i < clientCount; i++) {
            if (clientList[i][0].equals(aName)) {
                return clientList[i];
            }
        }
        return null;
    }
}

class ServerButtons extends JPanel implements ActionListener {

    JButton b1, b2, b3, b4;
    JTextArea outputArea;

    String myData[][];

    public ServerButtons() {
        b1 = new JButton("Client Values");
        b1.setActionCommand("CVAL");
        b1.addActionListener(this);
        add(b1);

        b2 = new JButton("Save Session");
        b2.setActionCommand("SS");
        b2.addActionListener(this);
        add(b2);

        b4 = new JButton("Clear Text Area");
        b4.setActionCommand("CLS");
        b4.addActionListener(this);
        add(b4);

        b3 = new JButton("Exit TMS");
        b3.setActionCommand("EXIT");
        b3.addActionListener(this);
        add(b3);

        outputArea = new JTextArea(40, 60);
        outputArea.setEditable(false);
        add(outputArea);

    }

    public void actionPerformed(ActionEvent e) {
        if ("EXIT".equals(e.getActionCommand())) {
            System.out.println("DEBUG: Exit Button pressed!");
            System.exit(0);
        } else if ("SS".equals(e.getActionCommand())) {
            System.out.println("DEBUG: Save session....");
            try {

            } catch (Exception ee) {
                System.out.println("ERROR: Can not save session!");
            }
        } else if ("CVAL".equals(e.getActionCommand())) {
            System.out.println("DEBUG: Current client values:");
            String data = "";
            for (int i = 0; i < myData.length; i++) {
                data = ("Client: " + myData[i][0] + "\t" + "Value: " + myData[i][1] + "\n");
                outputArea.append(data);
            }
        } else if ("CLS".equals(e.getActionCommand())) {
            System.out.println("DEBUG: Clear output screen:");
            outputArea.setText("");
        } else {
        }
    }

    public void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Server Buttons");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        //ButtonOptions newContentPane = new ButtonOptions();
        this.setOpaque(true);

        //content panes must be opaque
        frame.setContentPane(this);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

}
