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
            lightColours[0] = REDAMBER;
            lightColours[1] = AMBER;
            lightColours[2] = REDAMBER;
            lightColours[3] = AMBER;
            waitFor(2);
            lightColours[0] = GREEN;
            lightColours[1] = RED;
            lightColours[2] = GREEN;
            lightColours[3] = RED;
        } else {
            //starting on green
            waitFor(2);
            lightColours[0] = AMBER;
            lightColours[1] = REDAMBER;
            lightColours[2] = AMBER;
            lightColours[3] = REDAMBER;
            waitFor(2);
            lightColours[0] = RED;
            lightColours[1] = GREEN;
            lightColours[2] = RED;
            lightColours[3] = GREEN;
            waitFor(2);
        }
    }

    private void processSequence(boolean step) {

        if (step) {
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
        System.out.println("Current vehicle statistics:");
        System.out.println("Traffic light 1: " + tls.Light1.size());
        System.out.println("Traffic light 2: " + tls.Light2.size());
        System.out.println("Traffic light 3: " + tls.Light3.size());
        System.out.println("Traffic light 4: " + tls.Light4.size() + "\n");
    }

    private void PrintMap() {

        /*System.out.println("     |   |   |      ");
         System.out.println("     |       |      ");
         System.out.println("     |   |   |      ");
         System.out.println("     |      " + lightColours[0] + "|      ");
         System.out.println("------   |   -------");
         System.out.println("     " + lightColours[1] + "              ");
         System.out.println("                    ");
         System.out.println("- - -        - - - -");
         System.out.println("                    ");
         System.out.println("             " + lightColours[2] + "      ");
         System.out.println("------       -------");
         System.out.println("     |" + lightColours[3] + "  |   |      ");
         System.out.println("     |       |      ");
         System.out.println("     |   |   |      ");
         System.out.println("     |       |      ");
         System.out.println("     |   |   |      ");
         System.out.println("\n\n --------------------------------------------------- \n\n");*/
    }

    private void waitFor(int seconds) {
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < (1000 * seconds)) {
            //just keep looping...
        }
    }
}
