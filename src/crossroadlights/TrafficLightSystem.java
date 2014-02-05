package crossroadlights;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 *
 * @author group 3
 */

public class TrafficLightSystem extends Thread{

    
    private int currentSequence = 0;
    private TrafficLight[] lightSystem = {new TrafficLight(), new TrafficLight(), new TrafficLight(), new TrafficLight()};

    public static void Start() {
        TrafficLightSystem syst = new TrafficLightSystem();
        Button button = Button.start();
        new Thread(syst).start();
        while (true) {
            if (button.checkButton()) {
                syst.setAllLightsRed();
                syst.waitfor(5);
                button.resetButton();
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

    private void PrintMap() {

         for (int i = 0; i < 321; i++) {
            System.out.println("\b");
         }
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

    private void waitfor(int seconds) {
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < (1000 * seconds)) {
            //just keep looping...
        }
    }
}
