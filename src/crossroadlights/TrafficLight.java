
package crossroadlights;

/**
 *
 * @author group 3
 */


public class TrafficLight {
    public static final int Red = 0;
    public static final int Orange = 1;
    public static final int Green = 2;
    private int LightColour = Red;
    
    TrafficLight() {   
    }

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
    
}
