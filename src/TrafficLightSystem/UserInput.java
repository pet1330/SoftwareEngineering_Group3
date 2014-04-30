/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TrafficLightSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author computing
 */
public class UserInput implements Runnable {
    
    BufferedReader input;
    TrafficLightSystem ControlCentre;
    
    public UserInput(TrafficLightSystem ls)
    {
        ControlCentre = ls;
    }

    @Override
    public void run() {
        input = new BufferedReader(new InputStreamReader(System.in));
        String userMessage = "";
        
        while(true)
        {
            try {
                userMessage = input.readLine();
                
                if(!userMessage.equals(""))
                {
                    userFunctions(userMessage);
                }
            } catch (IOException ex) {
                System.out.println("There was an error");
            }
        }
    }
    
    public void userFunctions(String message) {
        switch (message) {
            case ("exit"):
                ControlCentre.Terminate();
                break;

            case ("report"):
                ControlCentre.reportTrafficLightStatus();
                break;
        }
    }

}
