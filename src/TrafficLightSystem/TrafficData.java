package TrafficLightSystem;

/**
 *
 * @author Group 3
 */
public class TrafficData {

    public int start = 0;
    public int end = 0;

    TrafficData(String s, String e) {
        start = Integer.parseInt(s);
        end = Integer.parseInt(e);
    }

    TrafficData(int s, int e) {
        start = s;
        end = e;
    }

}
