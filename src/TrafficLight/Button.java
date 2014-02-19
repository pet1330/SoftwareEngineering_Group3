package TrafficLight;

import java.util.Scanner;

/**
 *
 * @author Group 3
 */
public class Button implements Runnable {

    private boolean buttonPushed = false;

    public static void start() {
        Button toReturn = new Button();
        toReturn.start();
    }

    public Button() {
    }

    public void pushButton() {
        buttonPushed = true;
    }

    public void resetButton() {
        buttonPushed = false;
    }

    public boolean checkButton() {
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
}