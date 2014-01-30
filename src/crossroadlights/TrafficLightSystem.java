package crossroadlights;

import java.util.Scanner;

/**
 *
 * @author group 3
 */

public class TrafficLightSystem extends Thread {

    private boolean buttonPushed = false;
    private int currentSequence = 0;
    private TrafficLight[] lightSystem = {new TrafficLight(), new TrafficLight(), new TrafficLight(), new TrafficLight()};

    public static void Start() {
        TrafficLightSystem syst = new TrafficLightSystem();
        new Thread(syst).start();
        while (true) {
            if (syst.buttonPushed()) {
                syst.setAllLightsRed();
                syst.waitfor(5);
                syst.buttonPushed = false;
                clearConsole();
            } else {
                switch (syst.currentSequence) {
                    case 0:
                        syst.PrintMap();
                        syst.lightSystem[0].setLight(TrafficLight.Green);
                        syst.lightSystem[1].setLight(TrafficLight.Red);
                        syst.lightSystem[2].setLight(TrafficLight.Red);
                        syst.lightSystem[3].setLight(TrafficLight.Green);
                        syst.currentSequence = 1;
                        syst.waitfor(5);
                        break;
                    case 1:
                        syst.PrintMap();
                        syst.lightSystem[0].setLight(TrafficLight.Red);
                        syst.lightSystem[1].setLight(TrafficLight.Green);
                        syst.lightSystem[2].setLight(TrafficLight.Green);
                        syst.lightSystem[3].setLight(TrafficLight.Red);
                        syst.currentSequence = 0;
                        syst.waitfor(5);
                        break;
                    default:
                        System.out.println("Default ERROR");
                        syst.setAllLightsRed();
                        syst.waitfor(5);
                        break;
                }
            }
        }

    }

    private TrafficLightSystem() {
    }

    private void setAllLightsRed() {
        for (TrafficLight L : lightSystem) {
            L.setLight(0);
        }
    }

    private void pushButton() {
        buttonPushed = true;
    }

    private boolean buttonPushed() {
        return buttonPushed;
    }

    @Override
    public void run() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
            pushButton();
            System.out.print("Button Pushed");
        }

    }

    private void PrintMap() {

        // for (int i = 0; i < 321; i++) {
        //    System.out.println("\b");
        // }
        clearConsole();
        System.out.println("     |   |   |      ");
        System.out.println("     |       |      ");
        System.out.println("     |   |   |      ");
        System.out.println("     |      " + lightSystem[0].getLight() + "|      ");
        System.out.println("------   |   -------");
        System.out.println("     " + lightSystem[1].getLight() + "              ");
        System.out.println("                    ");
        System.out.println("- - -        - - - -");
        System.out.println("                    ");
        System.out.println("             " + lightSystem[2].getLight() + "      ");
        System.out.println("------       -------");
        System.out.println("     |" + lightSystem[3].getLight() + "  |   |      ");
        System.out.println("     |       |      ");
        System.out.println("     |   |   |      ");
        System.out.println("     |       |      ");
        System.out.println("     |   |   |      ");
    }

    private static void clearConsole() {
        System.out.print("\u001b[2J" + "\u001b[H");
        System.out.flush();
    }

    private void waitfor(int seconds) {
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < (1000 * seconds)) {
            //just keep looping...
        }
    }
}
