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
    public boolean hold = false;

    public ControlSystem(TrafficLightSystem _tls) {

        tls = _tls;
    }

    @Override
    public void run() {
        while (true) {
            int beenProcessed = 0;

            // Process the current traffic light queues (this changes the light sequence to allow vehicles to pass)
            while (((processSequence() > 0) || (beenProcessed < 5)) && (beenProcessed < 10))
            {
                beenProcessed++;
                waitFor(1);
            }

            // Hold the traffic lights in their current sequence
            if (!hold) {
                // Change the light processSequence
                changeLights();
                step = !step;
            }
        }
    }

    // Change the light sequence 
    public void changeLights() {
        if (step) {
            // Starting on red
            waitFor(2);
            tls.Map.lightColour[1] = AMBER;
            tls.Map.lightColour[3] = AMBER;
            waitFor(1);
            tls.Map.lightColour[1] = RED;
            tls.Map.lightColour[3] = RED;

            if (buttonPushed) {
                buttonPushed = false;
                waitFor(10);
            } else {
                waitFor(0.5);
            }

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
            
            if (buttonPushed) {
                buttonPushed = false;
                waitFor(10);
            } else {
                waitFor(0.5);
            }
            
            tls.Map.lightColour[1] = REDAMBER;
            tls.Map.lightColour[3] = REDAMBER;
            waitFor(1);
            tls.Map.lightColour[1] = GREEN;
            tls.Map.lightColour[3] = GREEN;
        }
    }

    // Process the light sequence for the GUI
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

    // Pause the system processing for a specific amount of time
    private void waitFor(double seconds) {
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < (1000 * seconds)) {
            //just keep looping...
        }
    }
}
