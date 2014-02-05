
package crossroadlights;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author group 3
 */


public class TrafficLight implements Observer{
    public static final int Red = 0;
    public static final int Orange = 1;
    public static final int Green = 2;
    private int LightColour;
    
    TrafficLight() {
    LightColour = Red;}

    public void setLight(int ChangeTo){

        if(ChangeTo<=2 && ChangeTo>=0)
        {
            LightColour=ChangeTo;
        }
    }
    
    public int getLight()
    {
    return LightColour;
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
