package TrafficLightSystem;

/**
 *
 * @author Group 3
 */
public class ControlSystem extends Thread {

    public static final int RED = 0;
    public static final int REDAMBER = 1;
    public static final int AMBER = 2;
    public static final int GREEN = 3;

    TrafficLightSystem tls;
    boolean step = true;
    int[] lightColours = new int[4];

    public ControlSystem(TrafficLightSystem _tls) {
        tls = _tls;
    }

    @Override
    public void run() {
        while (true) {
            // Process current queues
            waitFor(1);
            processSequence(step);

            // Change the light processSequence
            changeLights(step);
            step = !step;
        }
    }

    public void changeLights(boolean sequence) {
        if (sequence) {
            // Starting on red
            waitFor(2);
            Window.lightColour[0] = REDAMBER;
            Window.lightColour[1] = AMBER;
            Window.lightColour[2] = REDAMBER;
            Window.lightColour[3] = AMBER;
            waitFor(2);
            Window.lightColour[0] = GREEN;
            Window.lightColour[1] = RED;
            Window.lightColour[2] = GREEN;
            Window.lightColour[3] = RED;
        } else {
            //starting on green
            waitFor(2);
            Window.lightColour[0] = AMBER;
            Window.lightColour[1] = REDAMBER;
            Window.lightColour[2] = AMBER;
            Window.lightColour[3] = REDAMBER;
            waitFor(2);
            Window.lightColour[0] = RED;
            Window.lightColour[1] = GREEN;
            Window.lightColour[2] = RED;
            Window.lightColour[3] = GREEN;
        }
    }

    private void processSequence(boolean step) {

        if (!step) {
            //sequence N and S

            if (!tls.Light1.isEmpty()) {
                TrafficLightSystem.Map.animateCars(tls.Light1.get(0).getStart(), tls.Light1.get(0).getEnd());
                tls.Light1.remove(0);
            }

            if (!tls.Light3.isEmpty()) {
                TrafficLightSystem.Map.animateCars(tls.Light3.get(0).getStart(), tls.Light3.get(0).getEnd());
                tls.Light3.remove(0);
            }
        } else {
            if (!tls.Light2.isEmpty()) {
                TrafficLightSystem.Map.animateCars(tls.Light2.get(0).getStart(), tls.Light2.get(0).getEnd());
                tls.Light2.remove(0);
            }

            if (!tls.Light4.isEmpty()) {
                TrafficLightSystem.Map.animateCars(tls.Light4.get(0).getStart(), tls.Light4.get(0).getEnd());
                tls.Light4.remove(0);
            }
        }
    }

    private void waitFor(int seconds) {
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < (1000 * seconds)) {
            //just keep looping...
        }
    }
}
