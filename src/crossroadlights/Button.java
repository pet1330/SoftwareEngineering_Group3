package crossroadlights;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 *
 * @author Group 3
 */
public class Button implements Runnable, Observer {

    private boolean buttonPushed = false;

    public static Button start() {
        Button toReturn = new Button();
        Thread buttonRun = new Thread(toReturn);
        buttonRun.start();
        return toReturn;
    }

    private Button() {
    }

    @Override
    public void update(Observable o, Object arg) {
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