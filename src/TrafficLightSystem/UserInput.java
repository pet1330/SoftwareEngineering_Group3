package TrafficLightSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Group 3
 */
public class UserInput implements Runnable {

    BufferedReader input;
    TrafficLightSystem ControlCentre;

    public UserInput(TrafficLightSystem ls) {
        ControlCentre = ls;
    }

    @Override
    public void run() {
        input = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                String userMessage = input.readLine();

                if (!userMessage.equals("")) {
                    userFunctions(userMessage);
                }
            } catch (IOException ex) {
                System.out.println("There was an error");
            }
        }
    }

    public void userFunctions(String message) {
        switch (message.toLowerCase().trim()) {
            case ("exit"):
                ControlCentre.Terminate();
                break;

            case ("report"):
                ControlCentre.reportTrafficLightStatus(false);
                break;
        }
    }

}
