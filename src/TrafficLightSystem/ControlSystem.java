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
    public boolean buttonPushed = false;
    public boolean holdEW = false;
    public boolean holdNS = false;

    public ControlSystem(TrafficLightSystem _tls) {

        tls = _tls;
    }

    @Override
    public void run() {
        while (true) {
            int beenProcessed = 0;

            while (((processSequence() > 0) || (beenProcessed < 5)) && (beenProcessed < 10)) // Process current queues
            {

                beenProcessed++;
                waitFor(1);
            }

            if (buttonPushed) {
                buttonPushed();
                buttonPushed = false;
            }

            if (holdEW && step) {
                holdEW = false;
            } else if (holdNS && step) {
                holdNS = false;
            } else {
                // Change the light processSequence
                changeLights();
                step = !step;
            }
        }
    }

    public void changeLights() {
        if (step) {
            // Starting on red
            waitFor(2);
            tls.Map.lightColour[1] = AMBER;
            tls.Map.lightColour[3] = AMBER;
            waitFor(1);
            tls.Map.lightColour[1] = RED;
            tls.Map.lightColour[3] = RED;
            waitFor(0.5);
            tls.Map.lightColour[0] = REDAMBER;
            tls.Map.lightColour[2] = REDAMBER;
            waitFor(1);
            tls.Map.lightColour[0] = GREEN;
            tls.Map.lightColour[2] = GREEN;
        } else {
            //starting on green
            waitFor(2);
            tls.Map.lightColour[0] = AMBER;
            tls.Map.lightColour[2] = AMBER;
            waitFor(1);
            tls.Map.lightColour[0] = RED;
            tls.Map.lightColour[2] = RED;
            waitFor(0.5);
            tls.Map.lightColour[1] = REDAMBER;
            tls.Map.lightColour[3] = REDAMBER;
            waitFor(1);
            tls.Map.lightColour[1] = GREEN;
            tls.Map.lightColour[3] = GREEN;
        }
    }

    private void buttonPushed() {
        if (step) {
            // Starting on red
            waitFor(2);
            tls.Map.lightColour[1] = AMBER;
            tls.Map.lightColour[3] = AMBER;
            waitFor(1);
            tls.Map.lightColour[1] = RED;
            tls.Map.lightColour[3] = RED;
        } else {
            //starting on green
            waitFor(2);
            tls.Map.lightColour[0] = AMBER;
            tls.Map.lightColour[2] = AMBER;

            waitFor(1);
            tls.Map.lightColour[0] = RED;
            tls.Map.lightColour[2] = RED;

        }
        waitFor(5);
        if (step) {
            tls.Map.lightColour[0] = REDAMBER;
            tls.Map.lightColour[2] = REDAMBER;
            waitFor(1);
            tls.Map.lightColour[0] = GREEN;
            tls.Map.lightColour[2] = GREEN;
        } else {
            tls.Map.lightColour[1] = REDAMBER;
            tls.Map.lightColour[3] = REDAMBER;
            waitFor(1);
            tls.Map.lightColour[1] = GREEN;
            tls.Map.lightColour[3] = GREEN;
        }

    }

    private int processSequence() {
        int processed = 0;
        TrafficData remove;
        if (!step) {
            //sequence N and S

            remove = tls.Light1.poll();
            if (remove != null) {
                tls.Map.animateCars(remove.getStart(), remove.getEnd());
                processed++;
            }

            remove = tls.Light3.poll();
            if (remove != null) {
                tls.Map.animateCars(remove.getStart(), remove.getEnd());
                processed++;
            }

        } else {
            remove = tls.Light2.poll();
            if (remove != null) {

                tls.Map.animateCars(remove.getStart(), remove.getEnd());
                processed++;
            }

            remove = tls.Light4.poll();
            if (remove != null) {
                tls.Map.animateCars(remove.getStart(), remove.getEnd());
                processed++;
            }
        }
        return processed;
    }

    private void waitFor(double seconds) {
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < (1000 * seconds)) {
            //just keep looping...
        }
    }
}
