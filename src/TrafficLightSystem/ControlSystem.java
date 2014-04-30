package TrafficLightSystem;

/**
 *
 * @author computing
 */
public class ControlSystem extends Thread {

    public static final int RED = 0;
    public static final int REDAMBER = 1;
    public static final int ORANGE = 2;
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
            for (int i = 0; i < 11; i++) {
                waitFor(1);
                sequence((step = !step));
            }

            // Change the light sequence
            changeLights();
        }
    }

    public void changeLights() {
        if (lightColours[0] == GREEN) {
            lightColours[0] = REDAMBER;
            lightColours[1] = RED;
            lightColours[2] = RED;
            lightColours[3] = REDAMBER;
            PrintMap();
            waitFor(2);
            lightColours[0] = GREEN;
            lightColours[1] = RED;
            lightColours[2] = RED;
            lightColours[3] = GREEN;
            PrintMap();
            waitFor(5);
            lightColours[0] = ORANGE;
            lightColours[1] = RED;
            lightColours[2] = RED;
            lightColours[3] = ORANGE;
            PrintMap();
            waitFor(2);
            lightColours[0] = RED;
            lightColours[1] = RED;
            lightColours[2] = RED;
            lightColours[3] = RED;
            PrintMap();
            waitFor(2);
        } else {
            lightColours[0] = RED;
            lightColours[1] = REDAMBER;
            lightColours[2] = REDAMBER;
            lightColours[3] = RED;
            PrintMap();
            waitFor(2);
            lightColours[0] = RED;
            lightColours[1] = GREEN;
            lightColours[2] = GREEN;
            lightColours[3] = RED;
            PrintMap();
            waitFor(5);
            lightColours[0] = RED;
            lightColours[1] = ORANGE;
            lightColours[2] = ORANGE;
            lightColours[3] = RED;
            PrintMap();
            waitFor(2);
            lightColours[0] = RED;
            lightColours[1] = RED;
            lightColours[2] = RED;
            lightColours[3] = RED;
            PrintMap();
            waitFor(2);
        }

    }

    private void sequence(boolean step) {

        if (step) {
            //sequence N and S

            // changeLight();
            if (!tls.Light1.isEmpty()) {
                tls.Light1.remove(0);
            }

            if (!tls.Light3.isEmpty()) {
                tls.Light3.remove(0);

            }
        }
        else
        {
            if (!tls.Light2.isEmpty()) {
                tls.Light2.remove(0);
            }

            if (!tls.Light4.isEmpty()) {
                tls.Light4.remove(0);

            }
        }
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
