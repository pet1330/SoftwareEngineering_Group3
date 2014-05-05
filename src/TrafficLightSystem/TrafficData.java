package TrafficLightSystem;

/**
 *
 * @author Group 3
 */
public class TrafficData {

    public static final int fromW = 0;
    public static final int fromN = 1;
    public static final int fromE = 2;
    public static final int fromS = 3;

    private int start = 0;
    private int end = 0;
    private double locationX = 0;
    private double locationY = 0;
    private int direction = 0;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    TrafficData(int s, int e) {
        start = s;
        end = e;
        direction = start;

        switch (start) {
            case fromW:
                locationX = 1;
                locationY = 5;
                break;
            case fromN:
                locationX = 9;
                locationY = 0;
                break;
            case fromE:
                locationX = 14;
                locationY = 8;
                break;
            case fromS:
                locationX = 6;
                locationY = 13;
                break;
        }
    }
}
